package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("SELECT MAX(f.dataAtualizacao) FROM FormaPagamento f")
    OffsetDateTime getDataUltimaAtualizacao();

    @Query("SELECT MAX(f.dataAtualizacao) FROM FormaPagamento f WHERE f.id = :formaPagamentoId")
    OffsetDateTime getDataUltimaAtualizacaoById(Long formaPagamentoId);
}
