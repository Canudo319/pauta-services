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
public class UsuarioDTO {
    
    private Integer usuarioID;
    @NotNull
	private String cpf;
    @NotNull
	private String nome;
    @NotNull
	private String emailContato;
	
	private Boolean ativo;

}
