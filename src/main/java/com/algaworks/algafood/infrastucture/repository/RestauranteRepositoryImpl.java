package com.algaworks.algafood.infrastucture.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        StringBuilder jpql = new StringBuilder("from Restaurante where 0=0");

        Map<String, Object> parametros = new HashMap<>();

        if (StringUtils.hasLength(nome)) {
            jpql.append(" and nome like :nome");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append(" and taxaFrete >= :taxaInicial");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append(" and taxaFrete <= :taxaFinal");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach(query::setParameter);

        return query.getResultList();
    }
}
