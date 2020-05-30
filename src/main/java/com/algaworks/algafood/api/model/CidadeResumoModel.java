package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Juiz de Fora")
    private String nome;

    @ApiModelProperty(example = "Minas Gerais")
    private String estado;
}
