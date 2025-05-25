package com.caina.pautaservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caina.pautaservices.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailContato(String emailContato);

}
