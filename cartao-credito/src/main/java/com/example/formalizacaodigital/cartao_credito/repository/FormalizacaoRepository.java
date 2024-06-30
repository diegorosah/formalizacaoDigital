package com.example.formalizacaodigital.cartao_credito.repository;

import com.example.formalizacaodigital.cartao_credito.model.Formalizacao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormalizacaoRepository extends MongoRepository<Formalizacao, String> {

}
