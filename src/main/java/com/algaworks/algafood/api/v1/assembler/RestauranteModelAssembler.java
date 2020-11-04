package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        if (algaSecurity.podeConsultarCozinhas()) {
            Long cozinhaId = restauranteModel.getCozinha().getId();
            restauranteModel.getCozinha()
                    .add(algaLinks.linkToCozinha(cozinhaId));
        }

        if (algaSecurity.podeConsultarCidades()) {
            EnderecoModel enderecoModel = restauranteModel.getEndereco();
            if ((enderecoModel != null)
                    && (enderecoModel.getCidade() != null)) {
                Long cidadeId = enderecoModel.getCidade().getId();
                enderecoModel.getCidade()
                        .add(algaLinks.linkToCidade(cidadeId));

            }
        }

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));

        if (algaSecurity.podeGerenciarCadastroRestaurantes())
            restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restauranteModel.getId(), "responsaveis"));

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToProdutos(restauranteModel.getId(), "produtos"));

        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida())
                restauranteModel.add(algaLinks.linkToAtivacaoRestaurante(restauranteModel.getId(), "ativar"));

            if (restaurante.inativacaoPermitida())
                restauranteModel.add(algaLinks.linkToInativacaoRestaurante(restauranteModel.getId(), "inativar"));
        }

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida())
                restauranteModel.add(algaLinks.linkToAberturaRestaurante(restauranteModel.getId(), "abrir"));

            if (restaurante.fechamentoPermitido())
                restauranteModel.add(algaLinks.linkToFechamentoRestaurante(restauranteModel.getId(), "fechar"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        final CollectionModel<RestauranteModel> restauranteModels = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModels.add(algaLinks.linkToRestaurantes());

        return restauranteModels;
    }
}
