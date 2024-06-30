package com.example.formalizacaodigital.cartao_credito.repository;

import com.example.formalizacaodigital.cartao_credito.model.Simulacao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoRepository extends MongoRepository<Simulacao, String> {

}
