package com.example.formalizacaodigital.cartao_credito.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.model.Produto;
import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.repository.SimulacaoRepository;
import com.example.formalizacaodigital.cartao_credito.service.ClienteService;
import com.example.formalizacaodigital.cartao_credito.service.ProdutoService;
import com.example.formalizacaodigital.cartao_credito.service.SimulacaoService;

public class SimulacaoServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @InjectMocks
    private SimulacaoService simulacaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSimulacoes_RendaBaixa() {
        // Cliente com renda baixa
        String clienteId = "1";
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setRenda(2000.00);

        // Produtos simulados
        Produto cartaoBronze = createProduto("1", "Cartão Bronze", "Descrição Cartão Bronze", "Cartão", 1.5);
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);
        when(produtoService.getProdutoByNome("Cartão Bronze")).thenReturn(cartaoBronze);

        // Executa o método getSimulacoes
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(clienteId);

        // Verificações
        assertNotNull(simulacoes);
        assertEquals(1, simulacoes.size());
        assertEquals(cliente, simulacoes.get(0).getCliente());
        assertEquals(cartaoBronze, simulacoes.get(0).getProduto());

        // Verifica se a simulação foi salva
        verify(simulacaoRepository, times(1)).save(any(Simulacao.class));
    }

    @Test
    public void testGetSimulacoes_RendaMedia() {
        // Cliente com renda média
        String clienteId = "1";
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setRenda(8000.00);

        // Produtos simulados
        Produto cartaoBronze = createProduto("1", "Cartão Bronze", "Descrição Cartão Bronze", "Cartão", 1.5);
        Produto cartaoPrata = createProduto("2", "Cartão Prata", "Descrição Cartão Prata", "Cartão", 2.0);
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);
        when(produtoService.getProdutoByNome("Cartão Bronze")).thenReturn(cartaoBronze);
        when(produtoService.getProdutoByNome("Cartão Prata")).thenReturn(cartaoPrata);

        // Executa o método getSimulacoes
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(clienteId);

        // Verificações
        assertNotNull(simulacoes);
        assertEquals(2, simulacoes.size());
        assertEquals(cliente, simulacoes.get(0).getCliente());
        assertEquals(cartaoBronze, simulacoes.get(0).getProduto());
        assertEquals(cliente, simulacoes.get(1).getCliente());
        assertEquals(cartaoPrata, simulacoes.get(1).getProduto());

        // Verifica se as simulações foram salvas
        verify(simulacaoRepository, times(2)).save(any(Simulacao.class));
    }

    @Test
    public void testGetSimulacoes_RendaAlta() {
        // Cliente com renda alta
        String clienteId = "1";
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setRenda(15000.00);

        // Produtos simulados
        Produto cartaoBronze = createProduto("1", "Cartão Bronze", "Descrição Cartão Bronze", "Cartão", 1.5);
        Produto cartaoPrata = createProduto("2", "Cartão Prata", "Descrição Cartão Prata", "Cartão", 2.0);
        Produto cartaoOuro = createProduto("3", "Cartão Ouro", "Descrição Cartão Ouro", "Cartão", 2.5);
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);
        when(produtoService.getProdutoByNome("Cartão Bronze")).thenReturn(cartaoBronze);
        when(produtoService.getProdutoByNome("Cartão Prata")).thenReturn(cartaoPrata);
        when(produtoService.getProdutoByNome("Cartão Ouro")).thenReturn(cartaoOuro);

        // Executa o método getSimulacoes
        List<Simulacao> simulacoes = simulacaoService.getSimulacoes(clienteId);

        // Verificações
        assertNotNull(simulacoes);
        assertEquals(3, simulacoes.size());
        assertEquals(cliente, simulacoes.get(0).getCliente());
        assertEquals(cartaoBronze, simulacoes.get(0).getProduto());
        assertEquals(cliente, simulacoes.get(1).getCliente());
        assertEquals(cartaoPrata, simulacoes.get(1).getProduto());
        assertEquals(cliente, simulacoes.get(2).getCliente());
        assertEquals(cartaoOuro, simulacoes.get(2).getProduto());

        // Verifica se as simulações foram salvas
        verify(simulacaoRepository, times(3)).save(any(Simulacao.class));
    }

    // Método auxiliar para criar produtos simulados
    private Produto createProduto(String id, String nome, String descricao, String tipo, double taxaJuros) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setTipo(tipo);
        produto.setTaxaJuros(taxaJuros);
        return produto;
    }
}
