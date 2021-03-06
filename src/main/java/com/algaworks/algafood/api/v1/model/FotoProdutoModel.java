package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    private Long id;

    @ApiModelProperty(example = "d62e8eac-b611-49cc-9d78-a2ae8b696993_prime_rib_na_frigideira_7764_600.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Prime Rib ao ponto")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "202912")
    private Long tamanho;
}
