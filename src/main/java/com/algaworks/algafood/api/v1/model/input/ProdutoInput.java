package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

    @ApiModelProperty(example = "Isca de Picanha", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Acompanha tomate e batata frita", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "39.50")
    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean ativo;
}
