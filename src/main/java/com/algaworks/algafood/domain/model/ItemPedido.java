package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;
}
