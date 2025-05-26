package com.caina.pautaservices.beans.dtos;

import java.time.LocalDateTime;

import com.caina.pautaservices.beans.enums.ResultadoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoVotacaoDTO {
    
    private Integer pautaID;
    private String tituloPauta;
    private String descricaoPauta;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataEncerramento;
    private Long totalVotos;
    private Long votosAfavor;
    private Long votosContra;
    private ResultadoStatus vencedor;

    public ResultadoVotacaoDTO(Integer pautaID, String tituloPauta, String descricaoPauta, LocalDateTime dataEncerramento, Long totalVotos, Long votosAfavor, Long votosContra) {
        this.pautaID = pautaID;
        this.tituloPauta = tituloPauta;
        this.dataEncerramento = dataEncerramento;
        this.descricaoPauta = descricaoPauta;
        this.totalVotos = totalVotos;
        this.votosAfavor = votosAfavor;
        this.votosContra = votosContra;
    }
}
