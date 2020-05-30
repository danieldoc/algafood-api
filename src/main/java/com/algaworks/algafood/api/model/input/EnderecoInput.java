package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EnderecoInput {

    @ApiModelProperty(example = "36050-390", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Travessa Maria Alves Stopa", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "682", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Casa 1")
    private String complemento;

    @ApiModelProperty(example = "Progresso", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
