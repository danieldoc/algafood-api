package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaAT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void testarRetorno200QuandoConsultarCozinhas() {

        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testarContemDuasCozinhas() {
        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void testarRetorna201QuandoCadastrarCozinha() {
        given()
                .body("{\"nome\": \"Chinesa\"}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testarRetornaRespostaEStatusCorretosQuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", 2)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Tailandesa"));
    }

    @Test
    public void testarRetornaRespostaEStatus404QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", 100)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Indiana");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Tailandesa");
        cozinhaRepository.save(cozinha2);
    }
}
