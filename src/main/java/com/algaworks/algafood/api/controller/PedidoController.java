package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastucture.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private EmissaoPedidoService cadastroPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
        Page<Pedido> pedidoPage = cadastroPedido.listar(PedidoSpecs.construirFiltro(filtro), pageable);

        List<PedidoResumoModel> pedidoResumoModels = pedidoResumoModelAssembler.toCollectionModel(pedidoPage.getContent());

        return new PageImpl<>(pedidoResumoModels, pageable, pedidoPage.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscarPorCodigo(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(cadastroPedido.buscarOuFalhar(codigoPedido));
    }

    @PostMapping
    public PedidoModel incluir(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            Usuario cliente = new Usuario();
            cliente.setId(1L);
            pedido.setCliente(cliente);

            pedido = cadastroPedido.salvar(pedido);

            return pedidoModelAssembler.toModel(pedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
