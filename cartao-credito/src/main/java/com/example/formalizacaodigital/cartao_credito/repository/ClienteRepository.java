package com.example.formalizacaodigital.cartao_credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
}
