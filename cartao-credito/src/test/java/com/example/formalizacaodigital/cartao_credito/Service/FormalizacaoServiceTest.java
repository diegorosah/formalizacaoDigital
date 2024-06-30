package com.example.formalizacaodigital.cartao_credito.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.model.Formalizacao;
import com.example.formalizacaodigital.cartao_credito.model.Simulacao;
import com.example.formalizacaodigital.cartao_credito.repository.FormalizacaoRepository;
import com.example.formalizacaodigital.cartao_credito.repository.SimulacaoRepository;
import com.example.formalizacaodigital.cartao_credito.service.ClienteService;
import com.example.formalizacaodigital.cartao_credito.service.FormalizacaoService;

public class FormalizacaoServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @Mock
    private FormalizacaoRepository formalizacaoRepository;

    @InjectMocks
    private FormalizacaoService formalizacaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFormalizarContratacao_SimulacaoExistente() {
        // Cliente e Simulação de exemplo
        String clienteId = "1";
        String simulacaoId = "10";

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Cliente Teste");

        Simulacao simulacao = new Simulacao();
        simulacao.setId(simulacaoId);

        // Configuração dos mocks
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);
        when(simulacaoRepository.findById(simulacaoId)).thenReturn(Optional.of(simulacao));

        // Executa o método formalizarContratacao
        Formalizacao formalizacao = formalizacaoService.formalizarContratacao(clienteId, simulacaoId);

        // Verificações
        assertNotNull(formalizacao);
        assertEquals(cliente, formalizacao.getCliente());
        assertEquals(simulacao, formalizacao.getSimulacao());
        assertEquals("Sucesso", formalizacao.getStatus());

        // Verifica se a formalização foi salva
        verify(formalizacaoRepository, times(1)).save(any(Formalizacao.class));
    }

    @Test
    public void testFormalizarContratacao_SimulacaoNaoEncontrada() {
        // Cliente de exemplo
        String clienteId = "1";
        String simulacaoId = "10";

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Cliente Teste");

        // Configuração dos mocks
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);
        when(simulacaoRepository.findById(simulacaoId)).thenReturn(Optional.empty());

        // Executa o método formalizarContratacao e verifica a exceção
        // IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            formalizacaoService.formalizarContratacao(clienteId, simulacaoId);
        });

        // Verifica a mensagem da exceção
        assertEquals("Simulação não encontrada com ID: " + simulacaoId, exception.getMessage());

        // Verifica se a formalização não foi salva
        verify(formalizacaoRepository, never()).save(any(Formalizacao.class));
    }
}
