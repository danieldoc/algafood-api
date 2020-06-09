package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void confirmar(
            @ApiParam(value = "Código do pedido", example = "90c52e48-05ff-49e5-b0d9-4b0b4105253f", required = true)
            String codigoPedido
    );

    @ApiOperation("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void cancelar(
            @ApiParam(value = "Código do pedido", example = "90c52e48-05ff-49e5-b0d9-4b0b4105253f", required = true)
            String codigoPedido
    );

    @ApiOperation("Registrar entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void entregar(
            @ApiParam(value = "Código do pedido", example = "90c52e48-05ff-49e5-b0d9-4b0b4105253f", required = true)
            String codigoPedido
    );
}
