package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoInput {

    MultipartFile arquivo;
    String descricao;
}
