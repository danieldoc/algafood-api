package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @NotNull
    @TaxaFrete
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;
}
