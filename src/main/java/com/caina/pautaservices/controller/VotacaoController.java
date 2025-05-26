package com.caina.pautaservices.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caina.pautaservices.beans.dtos.ResultadoVotacaoDTO;
import com.caina.pautaservices.beans.dtos.VoteDTO;
import com.caina.pautaservices.beans.enums.ResultadoStatus;
import com.caina.pautaservices.beans.enums.VoteStatus;
import com.caina.pautaservices.beans.exceptions.BusinessException;
import com.caina.pautaservices.beans.exceptions.NotFoundException;
import com.caina.pautaservices.model.Pauta;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.model.VotosPauta;
import com.caina.pautaservices.repository.PautaRepository;
import com.caina.pautaservices.repository.UsuarioRepository;
import com.caina.pautaservices.repository.VotosPautaRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/votacao")
public class VotacaoController {
    
    @Autowired
    private VotosPautaRepository votosPautaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @GetMapping("/isAbleToVote")
    public ResponseEntity<Map<String, VoteStatus>> isAbleToVote(
        @RequestParam Integer associadoID,
        @RequestParam Integer pautaID
    ) {

        Usuario associado = usuarioRepository.findById(associadoID).orElseThrow(() -> {
            return new NotFoundException("Usuario", associadoID);
        });

        Pauta pauta = pautaRepository.findById(pautaID).orElseThrow(() -> {
            return new NotFoundException("Pauta", pautaID);
        });
        
        VoteStatus status = votosPautaRepository.isUserAbleToVote(associado, pauta).isPresent()
            ? VoteStatus.UNABLE_TO_VOTE
            : VoteStatus.ABLE_TO_VOTE;

        return ResponseEntity.ok(Map.of("status", status));
    }
    
    @PostMapping("/vote")
    public ResponseEntity<VotosPauta> voteMethod(@Valid @RequestBody VoteDTO dto) {
        String voto = switch(dto.getVoto().trim().toUpperCase()) {
            case "S", "SIM", "SS" -> "S";
            case "N", "NAO", "NÃO", "NN" -> "N";
            default -> throw new BusinessException("Voto Invalido");
        };

        Usuario associado = usuarioRepository.findById(dto.getAssociadoID()).orElseThrow(() -> {
            return new NotFoundException("Usuario", dto.getAssociadoID());
        });

        Pauta pauta = pautaRepository.findById(dto.getPautaID()).orElseThrow(() -> {
            return new NotFoundException("Pauta", dto.getPautaID());
        });

        /* Não permite votar em uma pauta que ainda não abriu ou já se encerrou */
        LocalDateTime horaVoto = LocalDateTime.now();
        if(horaVoto.isBefore(pauta.getDataAbertura())){
            throw new BusinessException("Essa Pauta ainda não foi aberta");
        }
        if(horaVoto.isAfter(pauta.getDataEncerramento())){
            throw new BusinessException("Essa Pauta já foi encerrada");
        }

        /* Trava para não deixar o associado votar mais de uma vez na mesma pauta */
        votosPautaRepository.isUserAbleToVote(associado, pauta).ifPresent(v -> {
            throw new BusinessException(String.format("Esse associado já votou '%s' para essa Pauta", v.getVoto()));
        });

        VotosPauta votosPauta = VotosPauta.builder()
            .pauta(pauta)
            .associado(associado)
            .voto(voto)
        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(votosPautaRepository.save(votosPauta));
    }
    
    @GetMapping("/result/{pautaID}")
    public ResponseEntity<ResultadoVotacaoDTO> findResult(@PathVariable Integer pautaID) {
        Pauta pauta = pautaRepository.findById(pautaID).orElseThrow(() -> new NotFoundException("Pauta", pautaID));

        ResultadoVotacaoDTO resultado = votosPautaRepository.findResultado(pauta).orElseThrow(() -> {
            return new BusinessException("Essa Pauta ainda não possui votos");
        });
        
        /* Se a pauta ainda não foi encerrada, O Resultado é indefindo  */
        LocalDateTime horarioConferencia = LocalDateTime.now();
        if(horarioConferencia.isBefore(pauta.getDataEncerramento())){
            resultado.setVencedor(ResultadoStatus.INDEFINIDO);
        }else{
            Long valorResultado = resultado.getVotosAfavor() - resultado.getVotosContra();
            if(valorResultado == 0){
                resultado.setVencedor(ResultadoStatus.EMPATE);
            }else if(valorResultado > 1){
                resultado.setVencedor(ResultadoStatus.A_FAVOR);
            }else{
                resultado.setVencedor(ResultadoStatus.CONTRA);
            }
        }

        return ResponseEntity.ok(resultado);
    }
    
}
