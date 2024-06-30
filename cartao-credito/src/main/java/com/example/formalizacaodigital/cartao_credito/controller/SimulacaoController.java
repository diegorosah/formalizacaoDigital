package com.example.formalizacaodigital.cartao_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.service.SimulacaoService;

@RestController
@RequestMapping("/api/simulacoes")
public class SimulacaoController {
    @Autowired
    private SimulacaoService simulacaoService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Simulacao>> getSimulacoes(@PathVariable String clienteId) {
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(clienteId);
        return new ResponseEntity<>(simulacoes, HttpStatus.OK);
    }
}
