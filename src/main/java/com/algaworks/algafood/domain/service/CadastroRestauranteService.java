package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastucture.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.algafood.infrastucture.repository.spec.RestauranteComNomeSemelhanteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> buscarTodos(String nome) {
        RestauranteComFreteGratisSpec restauranteComFreteGratisSpec = new RestauranteComFreteGratisSpec();
        RestauranteComNomeSemelhanteSpec restauranteComNomeSemelhanteSpec = new RestauranteComNomeSemelhanteSpec(nome);

        return restauranteRepository.findAll(restauranteComFreteGratisSpec.and(restauranteComNomeSemelhanteSpec));
    }

    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public boolean naoExistePorId(Long id) {
        return !existePorId(id);
    }

    public boolean existePorId(Long id) {
        return restauranteRepository.existsById(id);
    }
}
