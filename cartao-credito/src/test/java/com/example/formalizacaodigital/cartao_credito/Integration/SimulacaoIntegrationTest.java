package com.example.formalizacaodigital.cartao_credito.Integration;

import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimulacaoIntegrationTest {

    @BeforeClass
    public void setUpClass() {
        RestAssured.port = 8080;
        // Criar produtos necessários para os testes
        createProduto("Cartão Bronze", "Descrição do Cartão Bronze", "Bronze", 10.0);
        createProduto("Cartão Prata", "Descrição do Cartão Prata", "Prata", 15.0);
        createProduto("Cartão Ouro", "Descrição do Cartão Ouro", "Ouro", 20.0);
    }

    private void createProduto(String nome, String descricao, String tipo, double taxaJuros) {
        given()
                .contentType("application/json")
                .body("{\"nome\": \"" + nome + "\", \"descricao\": \"" + descricao + "\", \"tipo\": \"" + tipo
                        + "\", \"taxaJuros\": " + taxaJuros + "}")
                .when()
                .post("/api/produtos")
                .then()
                .statusCode(201);
    }

    @Test
    public void testSimulacaoRendaBaixa() {
        // Criar cliente com renda baixa
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Renda Baixa\", \"email\": \"rendabaixa@teste.com\", \"renda\": 3000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        // Executar teste de simulação para renda baixa
        given()
                .when()
                .get("/api/simulacoes/{clienteId}", clienteId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testSimulacaoRendaMedia() {
        // Criar cliente com renda média
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Renda Média\", \"email\": \"rendamedia@teste.com\", \"renda\": 8000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        // Executar teste de simulação para renda média
        given()
                .when()
                .get("/api/simulacoes/{clienteId}", clienteId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testSimulacaoRendaAlta() {
        // Criar cliente com renda alta
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Renda Alta\", \"email\": \"rendaalta@teste.com\", \"renda\": 15000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        // Executar teste de simulação para renda alta
        given()
                .when()
                .get("/api/simulacoes/{clienteId}", clienteId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
