package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de cidade com código %d";

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long estadoId) {
        this(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
    }
}
