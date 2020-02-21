package com.algaworks.algafood.infrastucture.repository.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String offset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var predicates = new ArrayList<Predicate>();

        var functionConvertTzDataCriacao = builder.function("convert_tz",
                Date.class,
                root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(offset));

        var functionDateDataCriacao = builder.function("date",
                Date.class,
                functionConvertTzDataCriacao);

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        if (filter.getRestauranteId() != null)
            predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));

        if (filter.getDataCriacaoInicio() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));

        if (filter.getDataCriacaoFim() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.where(predicates.toArray(Predicate[]::new));
        return manager.createQuery(query).getResultList();
    }
}
