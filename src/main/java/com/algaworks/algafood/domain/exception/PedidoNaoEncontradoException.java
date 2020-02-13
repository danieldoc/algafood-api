package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Nao existe um cadastro de pedido com o codigo %s";

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, pedidoId));
    }
}
