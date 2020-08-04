package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @ApiModel("Link")
    @Getter
    @Setter
    private static class LinkModel {

        private String href;
        private boolean templated;
    }
}
