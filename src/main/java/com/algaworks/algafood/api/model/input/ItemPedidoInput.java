package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @NotNull
    @Positive
    private Integer quantidade;

    private String observacao;
}
