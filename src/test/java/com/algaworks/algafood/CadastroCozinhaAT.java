package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaAT {

    public static final int COZINHA_ID_EXISTENTE = 2;
    public static final int COZINHA_ID_INEXISTENTE = 999;
    public static final String COZINHA_INDIANA = "Indiana";
    public static final String COZINHA_TAILANDESA = "Tailandesa";

    private int quantidadeDeCozinhas;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    private String cozinhaChinesaJson;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();

        this.cozinhaChinesaJson = ResourceUtils.getContentFromResource("/json/cozinha.json");
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
    public void testarContemCozinhas() {
        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(this.quantidadeDeCozinhas))
                .body("nome", hasItems(COZINHA_INDIANA, COZINHA_TAILANDESA));
    }

    @Test
    public void testarRetorna201QuandoCadastrarCozinha() {
        given()
                .body(this.cozinhaChinesaJson)
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
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(COZINHA_TAILANDESA));
    }

    @Test
    public void testarRetornaRespostaEStatus404QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        List<Cozinha> cozinhas = new ArrayList<>();

        Cozinha cozinhaIndiana = construirCozinha(COZINHA_INDIANA);
        cozinhas.add(cozinhaIndiana);

        Cozinha cozinhaTailandesa = construirCozinha(COZINHA_TAILANDESA);
        cozinhas.add(cozinhaTailandesa);

        cozinhaRepository.saveAll(cozinhas);

        this.quantidadeDeCozinhas = cozinhas.size();
    }

    private Cozinha construirCozinha(String nome) {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(nome);
        return cozinha;
    }
}
