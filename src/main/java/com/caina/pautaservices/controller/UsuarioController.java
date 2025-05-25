package com.caina.pautaservices.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caina.pautaservices.beans.dtos.UsuarioDTO;
import com.caina.pautaservices.beans.exceptions.DuplicatedInfoException;
import com.caina.pautaservices.beans.exceptions.NotFoundException;
import com.caina.pautaservices.model.Usuario;
import com.caina.pautaservices.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuario") 
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findUsuarioByID(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioRepository.findById(id).orElseThrow(() -> {
            return new NotFoundException("Usuario", id);
        }));
    }

    @GetMapping("/")
    public ResponseEntity<Page<Usuario>> filtrarUsuarios(
        @RequestParam(required = false) Optional<String> nome,
        @RequestParam(required = false) Optional<String> cpf,
        @RequestParam(required = false) Optional<String> email,
        @RequestParam(defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("nome"));

        return ResponseEntity.ok(usuarioRepository.findByNomeOrCpfOrEmailContato(
            nome.orElse(""),
            cpf.orElse(""),
            email.orElse(""),
            pageable
        ));
    }

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
            .nome(dto.getNome().toUpperCase())
            .emailContato(dto.getEmailContato())
        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

}
