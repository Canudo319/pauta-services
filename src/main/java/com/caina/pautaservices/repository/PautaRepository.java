package com.caina.pautaservices.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caina.pautaservices.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
    
    /* Busca somente as Pautas que ainda não estão encerradas */
    @Query("""
        select a from Pauta a
        where
            (:dataCorrente > a.dataAbertura AND :dataCorrente <= a.dataEncerramento)
            and (:tituloPauta = '' OR a.tituloPauta = :tituloPauta)
    """)
    Page<Pauta> findPautasAbertas(
        @Param("dataCorrente") LocalDateTime dataCorrente,
        @Param("tituloPauta") String tituloPauta,
        Pageable pageable
    );    

}
