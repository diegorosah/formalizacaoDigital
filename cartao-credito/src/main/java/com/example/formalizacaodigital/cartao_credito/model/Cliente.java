package com.example.formalizacaodigital.cartao_credito.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientes")
public class Cliente {
    @Id
    private String id;
    private String nome;
    private String email;
    private String cpf;
    private double renda;
}
