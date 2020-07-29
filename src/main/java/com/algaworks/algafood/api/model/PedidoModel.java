package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @ApiModelProperty(example = "90c52e48-05ff-49e5-b0d9-4b0b4105253f")
    private String codigo;

    @ApiModelProperty(example = "42.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "52.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private StatusPedido status;

    @ApiModelProperty(example = "2020-05-30T20:29:21Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2020-05-30T20:30:21Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2020-05-30T21:09:21Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2020-05-30T20:29:21Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

    private FormaPagamentoModel formaPagamento;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;
}
