package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Getter
@Setter
public class FormasPagamentoModelOpenApi {

    private FormaPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedModelOpenApi")
    @Data
    public class FormaPagamentoEmbeddedModelOpenApi {

        private List<FormaPagamentoModel> formasPagamento;
    }
}
