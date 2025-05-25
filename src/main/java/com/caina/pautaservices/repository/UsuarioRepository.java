package com.caina.pautaservices.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caina.pautaservices.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailContato(String emailContato);

    @Query("""
        select a from Usuario a
        where
            (:nome = '' OR a.nome = :nome)
            and (:cpf = '' OR a.cpf = :cpf)
            and (:email = '' Or a.emailContato = :email)
    """)
    Page<Usuario> findByNomeOrCpfOrEmailContato(
        @Param("nome") String nome,
        @Param("cpf") String cpf,
        @Param("email") String email,
        Pageable pageable
    );

}
