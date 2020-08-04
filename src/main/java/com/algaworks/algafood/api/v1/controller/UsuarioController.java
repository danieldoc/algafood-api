package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSenhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    @Override
    public CollectionModel<UsuarioModel> listar() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarioModelAssembler.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioId}")
    @Override
    public UsuarioModel buscar(@PathVariable Long usuarioId) {

        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {

        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);

        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    @Override
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioInput) {

        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{usuarioId}/senha")
    @Override
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSenhaInput usuarioSenhaInput) {

        cadastroUsuario.atualizarSenha(usuarioId, usuarioSenhaInput.getSenhaAtual(), usuarioSenhaInput.getNovaSenha());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{usuarioId}")
    @Override
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuario.excluir(usuarioId);
    }
}
