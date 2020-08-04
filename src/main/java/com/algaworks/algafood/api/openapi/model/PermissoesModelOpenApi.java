package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissoesModel")
@Getter
@Setter
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public class PermissoesEmbeddedModelOpenApi {

        private List<PermissaoModel> permissoes;
    }
}
