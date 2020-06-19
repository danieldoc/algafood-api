package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {

        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        TemplateVariables pageVariables = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        Link linkPedido = new Link(UriTemplate.of(pedidosUrl, pageVariables), "pedidos");
        pedidoModel.add(linkPedido);

        Link linkRestauranteId = linkTo(methodOn(RestauranteController.class)
                .buscar(pedidoModel.getRestaurante().getId()))
                .withSelfRel();
        pedidoModel.getRestaurante().add(linkRestauranteId);

        Link linkClienteId = linkTo(methodOn(UsuarioController.class)
                .buscar(pedidoModel.getCliente().getId()))
                .withSelfRel();
        pedidoModel.getCliente().add(linkClienteId);

        Link linkFormaPagamentoId = linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedidoModel.getFormaPagamento().getId(), null))
                .withSelfRel();
        pedidoModel.getFormaPagamento().add(linkFormaPagamentoId);

        Link linkEnderecoEntregaId = linkTo(methodOn(CidadeController.class)
                .buscar(pedidoModel.getEnderecoEntrega().getCidade().getId()))
                .withSelfRel();
        pedidoModel.getEnderecoEntrega().getCidade().add(linkEnderecoEntregaId);

        pedidoModel.getItens().forEach(item -> {

            Link linkProduto = linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto");
            item.add(linkProduto);
        });

        return pedidoModel;
    }
}
