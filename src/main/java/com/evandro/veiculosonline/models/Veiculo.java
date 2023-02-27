package com.evandro.veiculosonline.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "veiculos", schema = "veiculosonline")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String modelo;
    private int ano;
    private String preco;
    private String imagem;
    private String tipo;
    private String cor;
    private String marca;

    @ManyToOne
    private Usuario proprietario;
}
