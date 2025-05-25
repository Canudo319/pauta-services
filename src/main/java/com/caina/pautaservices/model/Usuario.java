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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ConverterRegistration(converter = LocalDateTimeConverter.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioID;

    @Column(name = "cpf", nullable = false)
	private String cpf;
    
    @Column(name = "nome", nullable = false)
	private String nome;
    
    @Column(name = "email_contato", nullable = false)
	private String emailContato;
    
    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column(name = "ativo", nullable = false)
    @Builder.Default
	private Boolean ativo = true;
    
    @Column(name = "data_alteracao", nullable = false)
    @Builder.Default
	private LocalDateTime dataAlteracao = LocalDateTime.now();
}
