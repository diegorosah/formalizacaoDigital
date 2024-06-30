package com.example.formalizacaodigital.cartao_credito.Integration;

import com.example.formalizacaodigital.cartao_credito.controller.FormalizacaoController;
import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.service.ClienteService;
import com.example.formalizacaodigital.cartao_credito.service.SimulacaoService;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormalizacaoIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SimulacaoService simulacaoService;

    @BeforeMethod
    public void setUp() {
        RestAssured.port = 8080; // Configura a porta do servidor
    }

    @Test
    public void testFormalizarContratacaoRendaAlta() {
        // Criar cliente com renda alta
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Renda Alta");
        cliente.setEmail("rendaalta@teste.com");
        cliente.setRenda(15000.00);
        cliente = clienteService.createCliente(cliente);

        // Criar simulação para o cliente
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(cliente.getId());
        Simulacao simulacaoEscolhida = simulacoes.get(0); // Escolhe a primeira simulação disponível

        // Formalizar contratação usando a simulação escolhida
        FormalizacaoController.ContratacaoRequest request = new FormalizacaoController.ContratacaoRequest();
        request.setClienteId(cliente.getId());
        request.setSimulacaoId(simulacaoEscolhida.getId());

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/formalizacao/contratar")
                .then()
                .statusCode(200)
                .body("cliente.id", equalTo(cliente.getId()))
                .body("simulacao.id", equalTo(simulacaoEscolhida.getId()));
    }

    @Test
    public void testFormalizarContratacaoRendaMedia() {
        // Criar cliente com renda alta
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Renda Media");
        cliente.setEmail("rendaalta@teste.com");
        cliente.setRenda(6000.00);
        cliente = clienteService.createCliente(cliente);

        // Criar simulação para o cliente
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(cliente.getId());
        Simulacao simulacaoEscolhida = simulacoes.get(0); // Escolhe a primeira simulação disponível

        // Formalizar contratação usando a simulação escolhida
        FormalizacaoController.ContratacaoRequest request = new FormalizacaoController.ContratacaoRequest();
        request.setClienteId(cliente.getId());
        request.setSimulacaoId(simulacaoEscolhida.getId());

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/formalizacao/contratar")
                .then()
                .statusCode(200)
                .body("cliente.id", equalTo(cliente.getId()))
                .body("simulacao.id", equalTo(simulacaoEscolhida.getId()));
    }

    @Test
    public void testFormalizarContratacaoRendaBaixa() {
        // Criar cliente com renda alta
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Renda Baixa");
        cliente.setEmail("rendaalta@teste.com");
        cliente.setRenda(2500.00);
        cliente = clienteService.createCliente(cliente);

        // Criar simulação para o cliente
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(cliente.getId());
        Simulacao simulacaoEscolhida = simulacoes.get(0); // Escolhe a primeira simulação disponível

        // Formalizar contratação usando a simulação escolhida
        FormalizacaoController.ContratacaoRequest request = new FormalizacaoController.ContratacaoRequest();
        request.setClienteId(cliente.getId());
        request.setSimulacaoId(simulacaoEscolhida.getId());

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/formalizacao/contratar")
                .then()
                .statusCode(200)
                .body("cliente.id", equalTo(cliente.getId()))
                .body("simulacao.id", equalTo(simulacaoEscolhida.getId()));
    }
}
