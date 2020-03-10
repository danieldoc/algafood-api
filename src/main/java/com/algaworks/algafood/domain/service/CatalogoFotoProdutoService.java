package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId, produtoId);

        String nomeArquivoExistente = null;
        if (fotoProdutoOptional.isPresent()) {
            nomeArquivoExistente = fotoProdutoOptional.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoOptional.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        FotoProduto fotoProduto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorage.substituir(novaFoto, nomeArquivoExistente);

        return fotoProduto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }
}
