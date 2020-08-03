package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.FotoProdutoModel;
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

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {

        FotoProdutoModel fotoProdutoModel = createModelWithId(foto.getId(), foto);

        modelMapper.map(foto, FotoProdutoModel.class);

        Long restauranteId = foto.getProduto().getRestaurante().getId();

        fotoProdutoModel.add(algaLinks.linkToFotoProduto(restauranteId, foto.getProduto().getId()));

        fotoProdutoModel.add(algaLinks.linkToProduto(restauranteId, foto.getProduto().getId(), "produto"));

        return fotoProdutoModel;
    }
}
