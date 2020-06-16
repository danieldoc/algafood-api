package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(restauranteId)).withSelfRel());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{usuarioId}")
    @Override
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {

        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{usuarioId}")
    @Override
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {

        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
    }
}
