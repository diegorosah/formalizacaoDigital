package com.example.formalizacaodigital.cartao_credito.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.formalizacaodigital.cartao_credito.model.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    Optional<Produto> findByNome(String nome);
}
