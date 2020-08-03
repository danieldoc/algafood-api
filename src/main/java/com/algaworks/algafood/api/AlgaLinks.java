package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToPedidos(String rel) {

        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return new Link(UriTemplate.of(pedidosUrl, PAGE_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).listar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId))
                .withSelfRel();
    }

    public Link linkToRestaurantes(String rel) {
        String link = linkTo(RestauranteController.class)
                .withRel(rel)
                .toUri()
                .toString();

        return new Link(UriTemplate.of(link, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPagamentoId))
                .withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToAtivacaoRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToInativacaoRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToAberturaRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId))
                .withRel(rel);
    }

    public Link linkToFechamentoRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId))
                .withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class)
                .withRel(rel);
    }

    public Link linkToResponsavelRestauranteDesassociacao(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).desassociar(restauranteId, usuarioId))
                .withRel(rel);
    }

    public Link linkToResponsavelRestauranteAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).desassociar(restauranteId, null))
                .withRel(rel);
    }
}
