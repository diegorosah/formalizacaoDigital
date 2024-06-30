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
import com.example.formalizacaodigital.cartao_credito.repository.ClienteRepository;
import com.example.formalizacaodigital.cartao_credito.service.ClienteService;
import com.example.formalizacaodigital.cartao_credito.service.ResourceNotFoundException;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarCliente() {
        // Configuração do mock para o método save do repository
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("teste@teste.com");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Executa o método createCliente
        Cliente clienteSalvo = clienteService.createCliente(cliente);

        // Verificações
        assertNotNull(clienteSalvo);
        assertEquals("Cliente Teste", clienteSalvo.getNome());
        assertEquals("teste@teste.com", clienteSalvo.getEmail());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de criar cliente passou com sucesso!");
    }

    @Test
    public void testGetClienteById() {
        // Cliente de exemplo
        Cliente cliente = new Cliente();
        cliente.setId("1");
        cliente.setNome("Cliente Teste");

        // Configuração do mock para o método findById do repository
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));

        // Executa o método getClienteById
        Cliente clienteEncontrado = clienteService.getClienteById("1");

        // Verificações
        assertNotNull(clienteEncontrado);
        assertEquals("1", clienteEncontrado.getId());
        assertEquals("Cliente Teste", clienteEncontrado.getNome());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de buscar cliente por ID passou com sucesso!");
    }

    @Test
    public void testUpdateCliente() {
        // Cliente de exemplo
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId("1");
        clienteExistente.setNome("Cliente Existente");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId("1");
        clienteAtualizado.setNome("Cliente Atualizado");

        // Configuração do mock para o método existsById e save do repository
        when(clienteRepository.existsById("1")).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        // Executa o método updateCliente
        Cliente clienteEditado = clienteService.updateCliente("1", clienteAtualizado);

        // Verificações
        assertNotNull(clienteEditado);
        assertEquals("1", clienteEditado.getId());
        assertEquals("Cliente Atualizado", clienteEditado.getNome());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de atualizar cliente passou com sucesso!");
    }

    @Test
    public void testDeleteCliente() {
        // Configuração do mock para o método existsById e deleteById do repository
        when(clienteRepository.existsById("1")).thenReturn(true);

        // Executa o método deleteCliente
        assertDoesNotThrow(() -> clienteService.deleteCliente("1"));

        // Verifica se o método deleteById foi chamado
        verify(clienteRepository, times(1)).deleteById("1");

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de deletar cliente passou com sucesso!");
    }

    @Test
    public void testDeleteClienteNotFound() {
        // Configuração do mock para o método existsById do repository
        when(clienteRepository.existsById("2")).thenReturn(false);

        // Executa o método deleteCliente e verifica a exceção ResourceNotFoundException
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.deleteCliente("2");
        });

        // Verifica a mensagem da exceção
        assertEquals("Cliente not found with id: 2", exception.getMessage());

        // Mensagem de log em caso de exceção esperada
        System.out.println("Teste de deletar cliente com ID não encontrado passou com sucesso!");
    }
}
