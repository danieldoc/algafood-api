package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long estadoId) {
        this(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
    }
}
