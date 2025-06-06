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
public class VoteDTO {
    
    @NotNull
    private Integer pautaID;
    @NotNull
    private Integer associadoID;
    @NotNull
    private String voto;

}
