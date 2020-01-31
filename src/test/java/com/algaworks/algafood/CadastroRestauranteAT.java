package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteAT {

    public static final int RESTAURANTE_ID_EXISTENTE = 1;
    public static final int RESTAURANTE_ID_INEXISTENTE = 999;
    public static final String COZINHA_BRASILEIRA = "Brasileira";
    public static final String RESTAURANTE_NOME = "Carne na Brasa";

    private int quantidadeDeRestaurantes;

    @LocalServerPort
    private int port;

    private String restauranteJson;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;


    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();

        this.restauranteJson = ResourceUtils.getContentFromResource("/json/restaurante.json");
    }

    @Test
    public void testarRetorno200QuandoConsultarRestaurantes() {

        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testarContemRestaurantes() {
        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(this.quantidadeDeRestaurantes))
                .body("nome", hasItems(RESTAURANTE_NOME));
    }

    @Test
    public void testarRetorna201QuandoCadastrarRestaurante() {
        given()
                .body(this.restauranteJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testarRetornaRespostaEStatusCorretosQuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_EXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .get("/{restauranteId}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(RESTAURANTE_NOME));
    }

    @Test
    public void testarRetornaRespostaEStatus404QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .get("/{restauranteId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(COZINHA_BRASILEIRA);
        cozinhaRepository.save(cozinha);

        List<Restaurante> restaurantes = new ArrayList<>();
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(RESTAURANTE_NOME);
        restaurante.setTaxaFrete(BigDecimal.TEN);
        restaurante.setCozinha(cozinha);
        restaurantes.add(restaurante);

        restauranteRepository.saveAll(restaurantes);

        this.quantidadeDeRestaurantes = restaurantes.size();
    }
}
