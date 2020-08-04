package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApiV2 {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApiV2 page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaModelV2> cozinhas;
    }
}
