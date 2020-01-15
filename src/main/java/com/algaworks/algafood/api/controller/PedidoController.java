package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final CadastroPedidoService pedidoService;

    public PedidoController(CadastroPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }
}
