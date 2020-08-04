package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.GrupoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
@Getter
@Setter
public class GruposModelOpenApi {

    private GrupoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedModelOpenApi")
    @Data
    public class GrupoEmbeddedModelOpenApi {

        private List<GrupoModel> grupos;
    }
}
