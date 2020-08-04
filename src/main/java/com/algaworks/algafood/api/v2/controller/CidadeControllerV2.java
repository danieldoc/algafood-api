package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerOpenApiV2;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(path = "cidades", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @GetMapping
    @Override
    public CollectionModel<CidadeModelV2> listar() {

        List<Cidade> cidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @GetMapping("/{cidadeId}")
    @Override
    public CidadeModelV2 buscar(@PathVariable Long cidadeId) {

        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {

        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getIdCidade());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    @Override
    public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
                                   @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

        try {
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }
}
