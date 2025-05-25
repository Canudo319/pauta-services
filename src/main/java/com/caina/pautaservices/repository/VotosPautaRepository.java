package com.caina.pautaservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caina.pautaservices.model.VotosPauta;

public interface VotosPautaRepository extends JpaRepository<VotosPauta, Integer> {
    
}
