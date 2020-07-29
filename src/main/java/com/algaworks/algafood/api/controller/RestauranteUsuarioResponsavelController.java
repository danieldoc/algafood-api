package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        var usuariosModel = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToResponsavelRestauranteAssociacao(restauranteId, "associar"));

        usuariosModel.forEach(usuarioModel ->
                usuarioModel.add(algaLinks.linkToResponsavelRestauranteDesassociacao(restauranteId, usuarioModel.getId(), "desassociar")));

        return usuariosModel;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{usuarioId}")
    @Override
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {

        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{usuarioId}")
    @Override
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {

        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}
