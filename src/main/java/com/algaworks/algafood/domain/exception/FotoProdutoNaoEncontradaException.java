package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final String MSG_FOTO_PRODUTO_NAO_ENCONTRADA = "Não existe uma foto cadastrada para o produto %s do restaurante %s";

    public FotoProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format(MSG_FOTO_PRODUTO_NAO_ENCONTRADA, restauranteId, produtoId));
    }
}
