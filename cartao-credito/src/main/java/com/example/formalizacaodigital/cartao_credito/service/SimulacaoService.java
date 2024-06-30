package com.example.formalizacaodigital.cartao_credito.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.model.Produto;
import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.repository.SimulacaoRepository;

@Service
public class SimulacaoService {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private SimulacaoRepository simulacaoRepository; 

    public List<Simulacao> getSimulacoes(String clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        double rendaCliente = cliente.getRenda();

        List<Simulacao> simulacoes = new ArrayList<>();

        // Produtos de cartão fixos
        Produto cartaoBronze = produtoService.getProdutoByNome("Cartão Bronze");
        Produto cartaoPrata = produtoService.getProdutoByNome("Cartão Prata");
        Produto cartaoOuro = produtoService.getProdutoByNome("Cartão Ouro");

        // Lógica para determinar os cartões com base na renda do cliente
        if (rendaCliente <= 3000.00) {
            Simulacao simulacaoBronze = criarSimulacao(cliente, cartaoBronze);
            salvarSimulacao(simulacaoBronze);
            simulacoes.add(simulacaoBronze);
        } else if (rendaCliente <= 10000.00) {
            Simulacao simulacaoBronze = criarSimulacao(cliente, cartaoBronze);
            Simulacao simulacaoPrata = criarSimulacao(cliente, cartaoPrata);

            salvarSimulacao(simulacaoBronze);
            salvarSimulacao(simulacaoPrata);

            simulacoes.add(simulacaoBronze);
            simulacoes.add(simulacaoPrata);
        } else {
            Simulacao simulacaoBronze = criarSimulacao(cliente, cartaoBronze);
            Simulacao simulacaoPrata = criarSimulacao(cliente, cartaoPrata);
            Simulacao simulacaoOuro = criarSimulacao(cliente, cartaoOuro);

            salvarSimulacao(simulacaoBronze);
            salvarSimulacao(simulacaoPrata);
            salvarSimulacao(simulacaoOuro);

            simulacoes.add(simulacaoBronze);
            simulacoes.add(simulacaoPrata);
            simulacoes.add(simulacaoOuro);
        }

        return simulacoes;
    }

    private Simulacao criarSimulacao(Cliente cliente, Produto produto) {
        Simulacao simulacao = new Simulacao();
        simulacao.setCliente(cliente);
        simulacao.setProduto(produto);
        simulacao.setTaxaJuros(produto.getTaxaJuros());
        return simulacao;
    }

    private void salvarSimulacao(Simulacao simulacao) {
        simulacaoRepository.save(simulacao); // Salva a simulação no banco de dados
    }
}
