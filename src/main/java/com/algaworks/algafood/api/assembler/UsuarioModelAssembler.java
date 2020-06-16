package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {

        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModel);

        Link linkUsuarios = linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios");
        usuarioModel.add(linkUsuarios);

        Link linkGrupoUsuario = linkTo(methodOn(UsuarioGrupoController.class).listar(usuario.getId())).withRel("grupos-usuario");
        usuarioModel.add(linkGrupoUsuario);

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {

        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
    }
}
