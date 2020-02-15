package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CadastroPedidoService {

    private static final long CLIENTE_ID = 1L;

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

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
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
}
