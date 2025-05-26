package com.caina.pautaservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caina.pautaservices.model.Pauta;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.model.VotosPauta;

public interface VotosPautaRepository extends JpaRepository<VotosPauta, Integer> {
    
    @Query("""
        select a from VotosPauta a
        where
            a.associado = :associado
            and a.pauta = :pauta
    """)
    Optional<VotosPauta> isUserAbleToVote(
        @Param("associado") Usuario associado,
        @Param("pauta") Pauta pauta
    );

}
