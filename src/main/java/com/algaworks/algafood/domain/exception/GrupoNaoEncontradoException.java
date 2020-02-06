package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
    }
}
