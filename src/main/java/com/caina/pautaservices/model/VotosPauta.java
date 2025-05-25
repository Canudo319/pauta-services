package com.caina.pautaservices.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ConverterRegistration;

import com.caina.pautaservices.config.utils.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_votos_pauta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ConverterRegistration(converter = LocalDateTimeConverter.class)
public class VotosPauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "votos_pauta_id", nullable = false)
    private Integer votosPautaID;
    
    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "associado", referencedColumnName = "usuario_id", nullable = false)
	private Usuario associado;
    
    @Column(name = "voto", nullable = false)
	private String voto;
    
    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column(name = "data_alteracao", nullable = false)
    @Builder.Default
	private LocalDateTime dataAlteracao = LocalDateTime.now();
}
