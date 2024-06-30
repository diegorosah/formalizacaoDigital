package com.example.formalizacaodigital.cartao_credito.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "produtos")
public class Produto {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private String tipo;
    private double taxaJuros;
}
