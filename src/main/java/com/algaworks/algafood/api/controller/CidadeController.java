package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    @Override
    public CollectionModel<CidadeModel> listar() {

        List<Cidade> cidades = cidadeRepository.findAll();

        List<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(cidades);

        cidadesModel.forEach(cidadeModel -> {

            Link linkCidadeId = linkTo(methodOn(CidadeController.class)
                    .buscar(cidadeModel.getId()))
                    .withSelfRel();
            cidadeModel.add(linkCidadeId);

            Link linkCidades = linkTo(methodOn(CidadeController.class)
                    .listar())
                    .withRel("cidades");
            cidadeModel.add(linkCidades);

            Link linkEstadoId = linkTo(methodOn(EstadoController.class)
                    .buscar(cidadeModel.getEstado().getId()))
                    .withSelfRel();
            cidadeModel.getEstado().add(linkEstadoId);
        });

        CollectionModel<CidadeModel> cidadesCollectionModel = new CollectionModel<>(cidadesModel);

        cidadesCollectionModel.add(linkTo(methodOn(CidadeController.class).listar()).withSelfRel());

        return cidadesCollectionModel;
    }

    @Override
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {

        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

//        cidadeModel.add(new Link("http://localhost:8080/cidades/1", IanaLinkRelations.SELF));
//        cidadeModel.add(new Link("http://localhost:8080/cidades/1"));
//        cidadeModel.add(
//                WebMvcLinkBuilder.linkTo(CidadeController.class)
//                        .slash(cidadeModel.getId())
//                        .withSelfRel()
//        );
        Link linkCidadeId = linkTo(methodOn(CidadeController.class)
                .buscar(cidadeModel.getId()))
                .withSelfRel();
        cidadeModel.add(linkCidadeId);

//        cidadeModel.add(new Link("http://localhost:8080/cidades", "cidades"));
//        cidadeModel.add(new Link("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
//        cidadeModel.add(
//                WebMvcLinkBuilder.linkTo(CidadeController.class)
//                        .withRel("cidades")
//        );

        Link linkCidades = linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades");

        cidadeModel.add(linkCidades);

//        cidadeModel.getEstado().add(new Link("http://localhost:8080/estados/1"));

//        WebMvcLinkBuilder.linkTo(EstadoController.class)
//                .slash(cidadeModel.getEstado().getId())
//                .withSelfRel();

        Link linkEstadoId = linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel();

        cidadeModel.getEstado().add(linkEstadoId);

        return cidadeModel;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

        try {
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }
}
