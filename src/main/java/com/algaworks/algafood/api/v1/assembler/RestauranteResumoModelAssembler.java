package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResumoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteResumoModelAssembler() {
        super(RestauranteController.class, RestauranteResumoModel.class);
    }

    @Override
    public RestauranteResumoModel toModel(Restaurante restaurante) {

        RestauranteResumoModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        if (algaSecurity.podeConsultarCozinhas()) {
            Long cozinhaId = restauranteModel.getCozinha().getId();
            restauranteModel.getCozinha()
                    .add(algaLinks.linkToCozinha(cozinhaId));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteResumoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        final CollectionModel<RestauranteResumoModel> restauranteResumoModels = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteResumoModels.add(algaLinks.linkToRestaurantes());

        return restauranteResumoModels;
    }
}
