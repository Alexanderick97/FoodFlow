package com.CodeChefs.usuario_ms.service;

import com.CodeChefs.usuario_ms.dto.UsuarioRequestDTO;
import com.CodeChefs.usuario_ms.dto.UsuarioResponseDTO;
import com.CodeChefs.usuario_ms.model.Usuario;
import com.CodeChefs.usuario_ms.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Convertir entidad a ResponseDTO
    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getRol(),
                usuario.isActivo(),
                usuario.getFechaRegistro()
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(int id) {
        log.info("Buscando usuario con id: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(this::convertirAResponseDTO).orElse(null);
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.map(this::convertirAResponseDTO).orElse(null);
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        log.info("Creando nuevo usuario: {}", dto.getEmail());

        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            log.warn("Email ya existe: {}", dto.getEmail());
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setRol(dto.getRol());
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDate.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(guardado);
    }

    public UsuarioResponseDTO actualizarUsuario(int id, UsuarioRequestDTO dto) {
        log.info("Actualizando usuario con id: {}", id);

        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Usuario usuario = optional.get();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarUsuario(int id) {
        log.info("Eliminando usuario con id: {}", id);
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas con ResponseDTO
    public List<UsuarioResponseDTO> buscarPorRol(String rol) {
        return usuarioRepository.findByRol(rol)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> listarActivos() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}