package com.caina.pautaservices.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caina.pautaservices.beans.dtos.UsuarioDTO;
import com.caina.pautaservices.beans.exceptions.DuplicatedInfoException;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioDTO dto) {
        /* Validações */
        usuarioRepository.findByCpf(dto.getCpf()).ifPresent(usuario -> {
            throw new DuplicatedInfoException("CPF", usuario.getCpf());
        });

        usuarioRepository.findByEmailContato(dto.getEmailContato()).ifPresent(usuario -> {
            throw new DuplicatedInfoException("E-mail", usuario.getEmailContato());
        });
        
        Usuario usuario = usuarioRepository.save(Usuario.builder()
            .cpf(dto.getCpf())
            .nome(dto.getNome())
            .emailContato(dto.getEmailContato())
        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

}
