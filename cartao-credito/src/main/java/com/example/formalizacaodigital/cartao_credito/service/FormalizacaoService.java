package com.example.formalizacaodigital.cartao_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.model.Formalizacao;
import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.repository.FormalizacaoRepository;
import com.example.formalizacaodigital.cartao_credito.repository.SimulacaoRepository;

@Service
public class FormalizacaoService {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    @Autowired
    private FormalizacaoRepository formalizacaoRepository;

    public Formalizacao formalizarContratacao(String clienteId, String simulacaoId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        Simulacao simulacao = simulacaoRepository.findById(simulacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Simulação não encontrada com ID: " + simulacaoId));

        Formalizacao formalizacao = new Formalizacao();
        salvarFormalizacao(formalizacao);
        formalizacao.setCliente(cliente);
        formalizacao.setSimulacao(simulacao);
        formalizacao.setStatus("Sucesso");

        return formalizacao;
    }

    private void salvarFormalizacao(Formalizacao formalizacao) {
        formalizacaoRepository.save(formalizacao); 
    }

    public List<Formalizacao> getAllFormalizacoes() {
        return formalizacaoRepository.findAll();
    }
}
