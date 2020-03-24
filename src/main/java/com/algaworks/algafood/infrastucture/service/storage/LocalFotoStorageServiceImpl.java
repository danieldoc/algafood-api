package com.algaworks.algafood.infrastucture.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageServiceImpl implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getNomeArquivoPath(nomeArquivo);

            return Files.newInputStream(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel recuperar arquivo.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getNomeArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getNomeArquivoPath(nomeArquivo);

            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel excluir arquivo.", e);
        }
    }

    private Path getNomeArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal()
                .getDiretorioFotos().
                resolve(Path.of(nomeArquivo));
    }
}
