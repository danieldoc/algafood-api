package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel toModel(Estado restaurante) {
        return modelMapper.map(restaurante, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
