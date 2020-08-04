package com.algaworks.algafood.api.v2.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApiV2 {

    @ApiModelProperty(example = "10", value = "Quantidade de registros por pagina")
    private Long size;

    @ApiModelProperty(example = "20", value = "Total de elementos")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de paginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Numero da pagina (começa em 0)")
    private Long number;
}
