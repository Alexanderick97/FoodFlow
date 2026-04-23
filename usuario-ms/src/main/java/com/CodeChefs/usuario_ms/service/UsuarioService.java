package com.CodeChefs.usuario_ms.service;

import com.CodeChefs.usuario_ms.model.Usuario;
import com.CodeChefs.usuario_ms.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(int id) {
        log.info("Buscando usuario con id: {}", id);
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    public Usuario crearUsuario(Usuario usuario) {
        log.info("Creando nuevo usuario: {}", usuario.getEmail());
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(int id, Usuario usuarioActualizado) {
        log.info("Actualizando usuario con id: {}", id);
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setRol(usuarioActualizado.getRol());
            return usuarioRepository.save(usuario);
        });
    }

    public boolean eliminarUsuario(int id) {
        log.info("Eliminando usuario con id: {}", id);
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas
    public List<Usuario> buscarPorRol(String rol) {
        log.info("Buscando usuarios por rol: {}", rol);
        return usuarioRepository.findByRol(rol);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        log.info("Buscando usuarios por nombre: {}", nombre);
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Usuario> listarActivos() {
        log.info("Listando usuarios activos");
        return usuarioRepository.findByActivoTrue();
    }
}