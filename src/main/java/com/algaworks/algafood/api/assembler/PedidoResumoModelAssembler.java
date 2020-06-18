package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {

        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoResumoModel);

        Link linkPedido = linkTo(PedidoController.class)
                .withRel("pedidos");
        pedidoResumoModel.add(linkPedido);

        Link linkRestauranteId = linkTo(methodOn(RestauranteController.class)
                .buscar(pedidoResumoModel.getRestaurante().getId()))
                .withSelfRel();
        pedidoResumoModel.getRestaurante().add(linkRestauranteId);

        Link linkClienteId = linkTo(methodOn(UsuarioController.class)
                .buscar(pedidoResumoModel.getCliente().getId()))
                .withSelfRel();
        pedidoResumoModel.getCliente().add(linkClienteId);

        return pedidoResumoModel;
    }
}
