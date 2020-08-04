package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadosModel")
@Getter
@Setter
public class EstadosModelOpenApi {

    private EstadoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("EstadosEmbeddedModelOpenApi")
    @Data
    public class EstadoEmbeddedModelOpenApi {

        private List<EstadoModel> estados;
    }
}
