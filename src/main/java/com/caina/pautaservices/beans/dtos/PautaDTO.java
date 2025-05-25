package com.caina.pautaservices.beans.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PautaDTO {
    
    private Integer pautaID;
    @NotNull
	private Integer autorID;
    @NotNull
	private String tituloPauta;
    @NotNull
	private String descricaoPauta;
    @NotNull
	private String dataAbertura;
    
	private String dataEncerramento;

}
