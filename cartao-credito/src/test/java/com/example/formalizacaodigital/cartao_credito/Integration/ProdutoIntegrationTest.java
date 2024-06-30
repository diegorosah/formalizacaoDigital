package com.example.formalizacaodigital.cartao_credito.Integration;

import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoIntegrationTest {

    @BeforeMethod
    public void setUp() {
        RestAssured.port = 8080; 
    }

    @Test
    public void testCriarProduto() {
        given()
                .contentType("application/json")
                .body("{\"nome\": \"Novo Produto\", \"descricao\": \"Descrição do produto\", \"tipo\": \"Tipo A\", \"taxaJuros\": 0.05}")
                .when()
                .post("/api/produtos")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Novo Produto"))
                .body("descricao", equalTo("Descrição do produto"))
                .body("tipo", equalTo("Tipo A"))
                .body("taxaJuros", equalTo(0.05f));
    }

    @Test
    public void testBuscarProdutoPorId() {
        String produtoId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Existente\", \"descricao\": \"Descrição do produto\", \"tipo\": \"Tipo B\", \"taxaJuros\": 0.08}")
                .when()
                .post("/api/produtos")
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .get("/api/produtos/{id}", produtoId)
                .then()
                .statusCode(200)
                .body("id", equalTo(produtoId))
                .body("nome", equalTo("Produto Existente"))
                .body("descricao", equalTo("Descrição do produto"))
                .body("tipo", equalTo("Tipo B"))
                .body("taxaJuros", equalTo(0.08f));
    }

    @Test
    public void testDeletarProduto() {
        String produtoId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Para Deletar\", \"descricao\": \"Descrição do produto\", \"tipo\": \"Tipo C\", \"taxaJuros\": 0.07}")
                .when()
                .post("/api/produtos")
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .delete("/api/produtos/{id}", produtoId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testCriarDoisProdutosEBuscarTodos() {
        given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Busca 1\", \"descricao\": \"Descrição do produto 1\", \"tipo\": \"Tipo D\", \"taxaJuros\": 0.06}")
                .when()
                .post("/api/produtos")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Produto Busca 1"))
                .body("descricao", equalTo("Descrição do produto 1"))
                .body("tipo", equalTo("Tipo D"))
                .body("taxaJuros", equalTo(0.06f));

        given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Busca 2\", \"descricao\": \"Descrição do produto 2\", \"tipo\": \"Tipo E\", \"taxaJuros\": 0.09}")
                .when()
                .post("/api/produtos")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Produto Busca 2"))
                .body("descricao", equalTo("Descrição do produto 2"))
                .body("tipo", equalTo("Tipo E"))
                .body("taxaJuros", equalTo(0.09f));

        given()
                .when()
                .get("/api/produtos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2))
                .body("nome", hasItems("Produto Busca 1", "Produto Busca 2"))
                .body("descricao", hasItems("Descrição do produto 1", "Descrição do produto 2"))
                .body("tipo", hasItems("Tipo D", "Tipo E"))
                .body("taxaJuros", hasItems(0.06f, 0.09f));
    }

    @Test
    public void testAtualizarProduto() {
        String produtoId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Atualizar\", \"descricao\": \"Descrição do produto\", \"tipo\": \"Tipo F\", \"taxaJuros\": 0.04}")
                .when()
                .post("/api/produtos")
                .then()
                .extract()
                .path("id");

        given()
                .contentType("application/json")
                .body("{\"nome\": \"Produto Atualizado\", \"descricao\": \"Nova descrição\", \"tipo\": \"Tipo G\", \"taxaJuros\": 0.03}")
                .when()
                .put("/api/produtos/{id}", produtoId)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Produto Atualizado"))
                .body("descricao", equalTo("Nova descrição"))
                .body("tipo", equalTo("Tipo G"))
                .body("taxaJuros", equalTo(0.03f));

        given()
                .when()
                .get("/api/produtos/{id}", produtoId)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Produto Atualizado"))
                .body("descricao", equalTo("Nova descrição"))
                .body("tipo", equalTo("Tipo G"))
                .body("taxaJuros", equalTo(0.03f));
    }

    @Test
    public void testRemoverTodosProdutos() {
        // Remover todos os produtos criados nos testes anteriores
        given()
                .when()
                .delete("/api/produtos")
                .then()
                .statusCode(204);
    }
}
