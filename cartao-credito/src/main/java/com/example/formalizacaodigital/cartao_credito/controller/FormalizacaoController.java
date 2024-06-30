package com.example.formalizacaodigital.cartao_credito.controller;

import com.example.formalizacaodigital.cartao_credito.model.Formalizacao;
import com.example.formalizacaodigital.cartao_credito.service.FormalizacaoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formalizacao")
public class FormalizacaoController {

    @Autowired
    private FormalizacaoService formalizacaoService;

    @PostMapping("/contratar")
    public ResponseEntity<Formalizacao> formalizarContratacao(@RequestBody ContratacaoRequest request) {
        Formalizacao formalizacao = formalizacaoService.formalizarContratacao(request.getClienteId(),
                request.getSimulacaoId());
        return new ResponseEntity<>(formalizacao, HttpStatus.OK);
    }

    // Retornar todos os clientes
    @GetMapping
    public ResponseEntity<List<Formalizacao>> getAllFormalizacoes() {
        List<Formalizacao> formalizacoes = formalizacaoService.getAllFormalizacoes();
        return new ResponseEntity<>(formalizacoes, HttpStatus.OK);
    }

    public static class ContratacaoRequest {
        private String clienteId;
        private String simulacaoId;

        public String getClienteId() {
            return clienteId;
        }

        public void setClienteId(String clienteId) {
            this.clienteId = clienteId;
        }

        public String getSimulacaoId() {
            return simulacaoId;
        }

        public void setSimulacaoId(String simulacaoId) {
            this.simulacaoId = simulacaoId;
        }
    }
}
