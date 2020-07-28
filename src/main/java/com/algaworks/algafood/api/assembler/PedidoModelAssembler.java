package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {

        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos());

        Long restauranteId = pedidoModel.getRestaurante().getId();
        pedidoModel.getRestaurante()
                .add(algaLinks.linkToRestaurante(restauranteId));

        Long usuarioId = pedidoModel.getCliente().getId();
        pedidoModel.getCliente()
                .add(algaLinks.linkToUsuario(usuarioId));

        Long formaPagamentoId = pedidoModel.getFormaPagamento().getId();
        pedidoModel.getFormaPagamento()
                .add(algaLinks.linkToFormaPagamento(formaPagamentoId));

        Long cidadeId = pedidoModel.getEnderecoEntrega().getCidade().getId();
        pedidoModel.getEnderecoEntrega().getCidade()
                .add(algaLinks.linkToCidade(cidadeId));

        pedidoModel.getItens().forEach(item ->
                item.add(algaLinks.linkToProduto(restauranteId, item.getProdutoId(), "produto")));

        return pedidoModel;
    }
}
