package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.RestauranteResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantesResumoModel")
@Getter
@Setter
public class RestaurantesResumoModelOpenApi {

    private RestaurantesResumoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantesResumoEmbeddedModel")
    @Data
    public class RestaurantesResumoEmbeddedModelOpenApi {

        private List<RestauranteResumoModel> restaurantes;
    }
}
