package com.caina.pautaservices.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caina.pautaservices.beans.dtos.PautaDTO;
import com.caina.pautaservices.beans.exceptions.BusinessException;
import com.caina.pautaservices.beans.exceptions.NotFoundException;
import com.caina.pautaservices.config.utils.date.DateUtils;
import com.caina.pautaservices.model.Pauta;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.repository.PautaRepository;
import com.caina.pautaservices.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/pauta") 
public class PautaController {
    
    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/abertas")
    public ResponseEntity<Page<Pauta>> findPautasAbertas(
        @RequestParam(required = false) Optional<String> tituloPauta,
        @RequestParam(defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("dataEncerramento"));

        return ResponseEntity.ok(pautaRepository.findPautasAbertas(LocalDateTime.now(), tituloPauta.orElse(""), pageable));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Pauta> cadastrarPauta(@Valid @RequestBody PautaDTO dto) {
        /* Validações */
        Usuario autor = usuarioRepository.findById(dto.getAutorID()).orElseThrow(() -> {
            return new NotFoundException("Usuario", dto.getAutorID());
        });

        LocalDateTime dataAbertura = DateUtils.parseDTF(dto.getDataAbertura());

        /* Data de encerramento pode ser null, caso seja, pega a date de abertura
         * e adicona um minuto */
        LocalDateTime dataEncerramento = Optional.ofNullable(dto.getDataEncerramento())
            .map(s -> DateUtils.parseDTF(s))
            .orElse(dataAbertura.plusMinutes(1L));

        /* Valida se a data de encerramento é anterior a data de abertura */
        if(dataEncerramento.isBefore(dataAbertura)){
            throw new BusinessException("A abertura deve ser antes do encerramento");
        }

        Pauta pauta = Pauta.builder()
            .autor(autor)
            .tituloPauta(dto.getTituloPauta().toUpperCase().trim())
            .descricaoPauta(dto.getDescricaoPauta().trim())
            .dataAbertura(dataAbertura)
            .dataEncerramento(dataEncerramento)
        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(pautaRepository.save(pauta));
    }
    

}
