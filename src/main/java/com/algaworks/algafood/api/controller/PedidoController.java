package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private EmissaoPedidoService cadastroPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pedidoPagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidoPage = cadastroPedido.listar(PedidoSpecs.construirFiltro(filtro), pageableTraduzido);

        pedidoPage = new PageWrapper<>(pedidoPage, pageable);

        return pedidoPagedResourcesAssembler.toModel(pedidoPage, pedidoResumoModelAssembler);
    }

    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscarPorCodigo(@PathVariable String codigoPedido) {

        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @Override
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

    private Pageable traduzirPageable(Pageable apiPageable) {

        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
//                "subtotal", "subtotal",
//                "taxaFrete", "taxaFrete",
//                "valorTotal", "valorTotal",
                "status", "status",
                "dataCriacao", "dataCriacao"
//                "nomeCliente", "cliente.nome",
//                "restauranteNome", "restaurante.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
