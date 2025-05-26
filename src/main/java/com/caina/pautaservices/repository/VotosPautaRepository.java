package com.caina.pautaservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caina.pautaservices.beans.dtos.ResultadoVotacaoDTO;
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

    @Query("""
        select new com.caina.pautaservices.beans.dtos.ResultadoVotacaoDTO(
            a.pauta.pautaID as pautaID,
            a.pauta.tituloPauta as tituloPauta,
            a.pauta.descricaoPauta as descricaoPauta,
            a.pauta.dataEncerramento as dataEncerramento,
            count(a) as totalVotos,
            sum(case when a.voto = 'S' then 1 else 0 end) as votosAfavor,
            sum(case when a.voto = 'N' then 1 else 0 end) as votosContra
        ) from VotosPauta a
        where
            a.pauta = :pauta
        group by 1,2,3
    """)
    Optional<ResultadoVotacaoDTO> findResultado(@Param("pauta") Pauta pauta);

}
