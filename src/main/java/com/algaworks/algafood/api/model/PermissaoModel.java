package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CONSULTAR_RESTAURANTES")
    private String nome;

    @ApiModelProperty(example = "Permite consultar restaurantes")
    private String descricao;
}
