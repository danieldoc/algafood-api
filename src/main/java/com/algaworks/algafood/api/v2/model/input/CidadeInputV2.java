package com.algaworks.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("CidadeInput")
@Setter
@Getter
public class CidadeInputV2 {

    @ApiModelProperty(example = "Uberlandia")
    @NotBlank
    private String nomeCidade;

    @ApiModelProperty(example = "1")
    @NotNull
    private Long idEstado;
}
