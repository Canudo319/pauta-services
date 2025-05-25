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
@Table(name = "tbl_pauta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ConverterRegistration(converter = LocalDateTimeConverter.class)
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pauta_id", nullable = false)
    private Integer pautaID;
    
    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "usuario_id", nullable = false)
	private Usuario autor;
    
    @Column(name = "titulo_pauta", nullable = false)
	private String tituloPauta;
    
    @Column(name = "descricao_pauta", nullable = false)
	private String descricaoPauta;
    
    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_abertura", nullable = false)
	private LocalDateTime dataAbertura;

    @Column(name = "data_encerramento", nullable = false)
	private LocalDateTime dataEncerramento;
    
    @Column(name = "data_alteracao", nullable = false)
    @Builder.Default
	private LocalDateTime dataAlteracao = LocalDateTime.now();
}
