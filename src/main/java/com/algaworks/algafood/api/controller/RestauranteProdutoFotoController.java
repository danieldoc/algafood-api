package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, FotoProdutoInput fotoProdutoInput) {
        var nomeArquivo = UUID.randomUUID().toString()
                .concat("_")
                .concat(fotoProdutoInput.getArquivo().getOriginalFilename());

        var arquivoFoto = Path.of("/home/daniel/Imagens/catalogo", nomeArquivo);

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
