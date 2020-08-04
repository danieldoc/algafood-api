package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Numero da pagina (começa por 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por pagina")
    private int size;

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenacao")
    private List<String> sort;
}
