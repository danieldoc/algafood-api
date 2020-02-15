package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_PRODUTO_NAO_ENCONTRADO = "Nao existe um cadastro de produto com codigo %s para o restaurante de codigo %s!";

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        super(String.format(MSG_PRODUTO_NAO_ENCONTRADO, restauranteId, produtoId));
    }
}
