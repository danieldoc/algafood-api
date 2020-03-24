package com.algaworks.algafood.infrastucture.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageServiceImpl implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminho(nomeArquivo);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var caminhoArquivo = getCaminho(novaFoto.getNomeArquivo());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                        storageProperties.getS3().getBucket(),
                        caminhoArquivo,
                        novaFoto.getInputStream(),
                        objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Nao foi possivel enviar arquivo para Amazon S3", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            var caminhoArquivo = getCaminho(nomeArquivo);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo
            );

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Nao foi possivel excluir arquivo na Amazon S3", e);
        }
    }

    private String getCaminho(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getBucket(), nomeArquivo);
    }
}
