package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Nao existe um cadastro de permissao com o codigo %s";

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        super(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, permissaoId));
    }
}
