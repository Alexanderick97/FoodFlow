package com.CodeChefs.usuario_ms.repository;

import com.CodeChefs.usuario_ms.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRol(String rol);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByActivoTrue();
}