package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Nao existe um cadastro de pedido com o codigo %s";

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigoPedido));
    }
}
