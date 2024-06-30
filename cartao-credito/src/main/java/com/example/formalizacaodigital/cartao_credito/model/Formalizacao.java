package com.example.formalizacaodigital.cartao_credito.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "formalizacao")
public class Formalizacao {
    @Id
    private String id;
    private Cliente cliente;
    private Simulacao simulacao;
    private String status;
}
