package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi.CozinhaEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModelOpenApi")
    @Data
    public class CozinhaEmbeddedModelOpenApi {

        private List<CozinhaModel> cozinhas;
    }
}
