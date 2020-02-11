package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
}
