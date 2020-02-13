package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        Usuario cliente = cadastroUsuario.buscarOuFalhar(CLIENTE_ID);
        pedido.setCliente(cliente);

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
        pedido.setRestaurante(restaurante);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());

//        TODO: Implementar regras de negocio e preencher propriedades
//        pedido.setSubtotal();
//        pedido.setValorTotal();
//        pedido.setFormaPagamento();
//        for (ItemPedido item : pedido.getItens()) {
//            item.setPrecoUnitario();
//            item.setPrecoTotal();
//        }

        return pedidoRepository.save(pedido);
    }
}
