package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Flávia Cunha")
    private String nome;

    @ApiModelProperty(example = "flaviacunha@algafood.com")
    private String email;
}
