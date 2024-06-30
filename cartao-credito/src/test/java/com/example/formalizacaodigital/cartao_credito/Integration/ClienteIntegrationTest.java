package com.example.formalizacaodigital.cartao_credito.Integration;

import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIntegrationTest {

    @BeforeMethod
    public void setUp() {
        RestAssured.port = 8080;
    }

    @Test
    public void testCriarCliente() {
        given()
                .contentType("application/json")
                .body("{\"nome\": \"Novo Cliente\", \"email\": \"novo@teste.com\", \"renda\": 5000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Novo Cliente"))
                .body("email", equalTo("novo@teste.com"));
    }

    @Test
    public void testBuscarClientePorId() {
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Existente\", \"email\": \"existente@teste.com\", \"renda\": 8000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .get("/api/clientes/{id}", clienteId)
                .then()
                .statusCode(200)
                .body("id", equalTo(clienteId));
    }

    @Test
    public void testDeletarCliente() {
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Para Deletar\", \"email\": \"deletar@teste.com\", \"renda\": 3000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .delete("/api/clientes/{id}", clienteId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testCriarDoisClientesEBuscarTodos() {
        given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Busca 1\", \"email\": \"cliente1@teste.com\", \"renda\": 7000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Cliente Busca 1"))
                .body("email", equalTo("cliente1@teste.com"));

        given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Busca 2\", \"email\": \"cliente2@teste.com\", \"renda\": 9000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Cliente Busca 2"))
                .body("email", equalTo("cliente2@teste.com"));

        given()
                .when()
                .get("/api/clientes")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2))
                .body("nome", hasItems("Cliente Busca 1", "Cliente Busca 2"))
                .body("email", hasItems("cliente1@teste.com", "cliente2@teste.com"));
    }

    @Test
    public void testAtualizarCliente() {
        String clienteId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Atualizar\", \"email\": \"atualizar@teste.com\", \"renda\": 4000.00}")
                .when()
                .post("/api/clientes")
                .then()
                .extract()
                .path("id");

        given()
                .contentType("application/json")
                .body("{\"nome\": \"Cliente Atualizado\", \"email\": \"atualizado@teste.com\", \"renda\": 6000.00}")
                .when()
                .put("/api/clientes/{id}", clienteId)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Cliente Atualizado"))
                .body("email", equalTo("atualizado@teste.com"))
                .body("renda", equalTo(6000.00f)); // Verifica o valor como float

        given()
                .when()
                .get("/api/clientes/{id}", clienteId)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Cliente Atualizado"))
                .body("email", equalTo("atualizado@teste.com"))
                .body("renda", equalTo(6000.00f)); // Verifica o valor como float
    }
}
