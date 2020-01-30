package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void testarCadastroCozinhaComSucesso() {
        // Cenario
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // Acao
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        // Validacao
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testarCadastroCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        cadastroCozinha.salvar(novaCozinha);
    }
}
