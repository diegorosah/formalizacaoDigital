package com.example.formalizacaodigital.cartao_credito.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "simulacoes")
public class Simulacao {
    @Id
    private String id;
    private Cliente cliente;
    private Produto produto;
    private double taxaJuros;

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
}
