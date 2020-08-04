package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    @Override
    public CollectionModel<EstadoModel> listar() {

        List<Estado> estados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionModel(estados);
    }

    @GetMapping("/{estadoId}")
    @Override
    public EstadoModel buscar(@PathVariable Long estadoId) {

        return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {

        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    @Override
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {

        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{estadoId}")
    @Override
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}
