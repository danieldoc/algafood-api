package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModel {

    @ApiModelProperty(example = "36050-390")
    private String cep;

    @ApiModelProperty(example = "Travessa Maria Alves Stopa")
    private String logradouro;

    @ApiModelProperty(example = "682")
    private String numero;

    @ApiModelProperty(example = "Casa 1")
    private String complemento;

    @ApiModelProperty(example = "Progresso")
    private String bairro;

    private CidadeResumoModel cidade;
}
