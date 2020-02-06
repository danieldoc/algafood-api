package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe um cadastro de forma de pagamento com código %d";

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, formaPagamentoId));
    }
}
