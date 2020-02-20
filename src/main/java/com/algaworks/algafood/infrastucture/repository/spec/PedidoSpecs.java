package com.algaworks.algafood.infrastucture.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class PedidoSpecs {

    private PedidoSpecs() {
    }

    public static Specification<Pedido> construirFiltro(PedidoFilter filtro) {
        return (Root<Pedido> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            var predicates = new ArrayList<Predicate>();
            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            if (filtro.getClienteId() != null)
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));

            if (filtro.getRestauranteId() != null)
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));

            if (filtro.getDataCriacaoInicio() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));

            if (filtro.getDataCriacaoFim() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
