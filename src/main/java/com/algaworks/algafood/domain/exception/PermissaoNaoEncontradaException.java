package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Permissao com o codigo %s nao encontrada!";

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        super(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, permissaoId));
    }
}
