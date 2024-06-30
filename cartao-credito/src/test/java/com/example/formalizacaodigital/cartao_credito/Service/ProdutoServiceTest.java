package com.example.formalizacaodigital.cartao_credito.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.formalizacaodigital.cartao_credito.model.Produto;
import com.example.formalizacaodigital.cartao_credito.repository.ProdutoRepository;
import com.example.formalizacaodigital.cartao_credito.service.ProdutoService;
import com.example.formalizacaodigital.cartao_credito.service.ResourceNotFoundException;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Lista de produtos de exemplo
        List<Produto> produtos = new ArrayList<>();
        produtos.add(createProduto("1", "Produto 1", "Descrição do Produto 1", "Tipo 1", 1.5));
        produtos.add(createProduto("2", "Produto 2", "Descrição do Produto 2", "Tipo 2", 2.0));

        // Configuração do mock para o método findAll do repository
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Executa o método findAll
        List<Produto> produtosEncontrados = produtoService.findAll();

        // Verificações
        assertNotNull(produtosEncontrados);
        assertEquals(2, produtosEncontrados.size());
        assertEquals("Produto 1", produtosEncontrados.get(0).getNome());
        assertEquals("Produto 2", produtosEncontrados.get(1).getNome());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de buscar todos os produtos passou com sucesso!");
    }

    @Test
    public void testGetProdutoById() {
        // Produto de exemplo
        Produto produto = createProduto("1", "Produto 1", "Descrição do Produto 1", "Tipo 1", 1.5);

        // Configuração do mock para o método findById do repository
        when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        // Executa o método getProdutoById
        Produto produtoEncontrado = produtoService.getProdutoById("1");

        // Verificações
        assertNotNull(produtoEncontrado);
        assertEquals("1", produtoEncontrado.getId());
        assertEquals("Produto 1", produtoEncontrado.getNome());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de buscar produto por ID passou com sucesso!");
    }

    @Test
    public void testUpdateProduto() {
        // Produto de exemplo
        //Produto produtoExistente = createProduto("1", "Produto Existente", "Descrição do Produto", "Tipo 1", 1.5);
        Produto produtoAtualizado = createProduto("1", "Produto Atualizado", "Nova Descrição", "Tipo 2", 2.0);

        // Configuração do mock para o método existsById e save do repository
        when(produtoRepository.existsById("1")).thenReturn(true);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        // Executa o método updateProduto
        Produto produtoEditado = produtoService.updateProduto("1", produtoAtualizado);

        // Verificações
        assertNotNull(produtoEditado);
        assertEquals("1", produtoEditado.getId());
        assertEquals("Produto Atualizado", produtoEditado.getNome());

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de atualizar produto passou com sucesso!");
    }

    @Test
    public void testDeleteProduto() {
        // Configuração do mock para o método existsById do repository
        when(produtoRepository.existsById("1")).thenReturn(true);

        // Executa o método deleteProduto
        produtoService.deleteProduto("1");

        // Verifica se o método deleteById foi chamado com o ID correto
        verify(produtoRepository, times(1)).deleteById("1");

        // Mensagem de log em caso de sucesso
        System.out.println("Teste de deletar produto passou com sucesso!");
    }

    @Test
    public void testDeleteProdutoNotFound() {
        // Configuração do mock para o método existsById do repository
        when(produtoRepository.existsById("2")).thenReturn(false);

        // Executa o método deleteProduto e verifica a exceção ResourceNotFoundException
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.deleteProduto("2");
        });

        // Verifica a mensagem da exceção
        assertEquals("Produto not found", exception.getMessage());

        // Verifica se o método deleteById não foi chamado
        verify(produtoRepository, never()).deleteById(anyString());

        // Mensagem de log em caso de exceção esperada
        System.out.println("Teste de deletar produto com ID não encontrado passou com sucesso!");
    }

    // Método auxiliar para criar produtos com base nos parâmetros
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
