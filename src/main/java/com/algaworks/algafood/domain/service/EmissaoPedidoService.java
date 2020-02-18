package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        Long clienteId = pedido.getCliente().getId();
        Usuario cliente = cadastroUsuario.buscarOuFalhar(clienteId);

        Long restauranteId = pedido.getRestaurante().getId();
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
        if (!restaurante.getFormasPagamento().contains(formaPagamento))
            throw new NegocioException(String.format("Forma de pagamento '%s' nao e aceita por esse Restaurante!", formaPagamento.getDescricao()));

        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            Long produtoId = item.getProduto().getId();
            Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

            Integer quantidade = item.getQuantidade();
            BigDecimal precoUnitario = produto.getPreco();
            BigDecimal precoTotal = BigDecimal.valueOf(precoUnitario.doubleValue() * quantidade);

            item.setProduto(produto);
            item.setPrecoUnitario(precoUnitario);
            item.setPrecoTotal(precoTotal);

            subtotal = subtotal.add(precoTotal);
        }

        BigDecimal taxaFrete = restaurante.getTaxaFrete();
        BigDecimal total = subtotal.add(taxaFrete);

        pedido.setRestaurante(restaurante);
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setTaxaFrete(taxaFrete);
        pedido.setSubtotal(subtotal);
        pedido.setValorTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listar(Specification<Pedido> specification) {
        return pedidoRepository.findAll(specification);
    }
}
