package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_USUARIO_NAO_ENCONTRADO = "Não existe um cadastro de usuario com código %d";

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long grupoId) {
        this(String.format(MSG_USUARIO_NAO_ENCONTRADO, grupoId));
    }
}
