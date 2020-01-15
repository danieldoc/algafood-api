package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;

    private LocalDateTime dataCancelamento;

    private LocalDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false)
    private StatusPedido status;

    @JsonIgnore
    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
