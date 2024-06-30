package com.example.formalizacaodigital.cartao_credito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.formalizacaodigital.cartao_credito.model.Produto;
import com.example.formalizacaodigital.cartao_credito.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto newProduto = produtoService.createProduto(produto);
        return new ResponseEntity<>(newProduto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable String id) {
        Produto produto = produtoService.getProdutoById(id);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable String id, @RequestBody Produto produto) {
        Produto updatedProduto = produtoService.updateProduto(id, produto);
        return new ResponseEntity<>(updatedProduto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable String id) {
        produtoService.deleteProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Retornar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.findAll();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTodosProdutos() {
        produtoService.deleteTodosProdutos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
