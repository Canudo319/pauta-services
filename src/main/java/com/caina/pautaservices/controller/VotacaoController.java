package com.caina.pautaservices.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caina.pautaservices.beans.enums.VoteStatus;
import com.caina.pautaservices.beans.exceptions.NotFoundException;
import com.caina.pautaservices.model.Pauta;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.repository.PautaRepository;
import com.caina.pautaservices.repository.UsuarioRepository;
import com.caina.pautaservices.repository.VotosPautaRepository;


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
    public Map<String, VoteStatus> getMethodName(
        @RequestParam Integer associadoID,
        @RequestParam Integer pautaID
    ) {

        Usuario usuario = usuarioRepository.findById(associadoID).orElseThrow(() -> {
            return new NotFoundException("Usuario", associadoID);
        });

        Pauta pauta = pautaRepository.findById(pautaID).orElseThrow(() -> {
            return new NotFoundException("Pauta", pautaID);
        });
        
        VoteStatus status = votosPautaRepository.isUserAbleToVote(usuario, pauta).isPresent()
            ? VoteStatus.UNABLE_TO_VOTE
            : VoteStatus.ABLE_TO_VOTE;

        return Map.of("status", status);
    }
    

}
