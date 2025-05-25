package com.caina.pautaservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caina.pautaservices.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
    
}
