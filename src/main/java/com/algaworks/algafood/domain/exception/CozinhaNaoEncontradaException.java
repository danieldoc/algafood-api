package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de cozinha com código %d";

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long estadoId) {
        this(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
    }
}
