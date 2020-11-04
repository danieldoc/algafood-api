package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {

        FotoProdutoModel fotoProdutoModel = createModelWithId(foto.getId(), foto);

        modelMapper.map(foto, FotoProdutoModel.class);

        if (algaSecurity.podeConsultarRestaurantes()) {
            Long restauranteId = foto.getProduto().getRestaurante().getId();

            fotoProdutoModel.add(algaLinks.linkToFotoProduto(restauranteId, foto.getProduto().getId()));

            fotoProdutoModel.add(algaLinks.linkToProduto(restauranteId, foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }
}
