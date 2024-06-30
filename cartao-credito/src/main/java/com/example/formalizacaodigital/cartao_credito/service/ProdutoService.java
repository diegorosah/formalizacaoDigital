package com.example.formalizacaodigital.cartao_credito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.formalizacaodigital.cartao_credito.model.Produto;
import com.example.formalizacaodigital.cartao_credito.repository.ProdutoRepository;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto getProdutoById(String id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto not found"));
    }

    public Produto updateProduto(String id, Produto produto) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto not found");
        }
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public void deleteProduto(String id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto not found");
        }
        produtoRepository.deleteById(id);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto getProdutoByNome(String nome) {
        return produtoRepository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado pelo nome: " + nome));
    }

    public void deleteTodosProdutos() {
        produtoRepository.deleteAll();
    }
}
