package com.CodeChefs.usuario_ms.service;

import com.CodeChefs.usuario_ms.dto.UsuarioRequestDTO;
import com.CodeChefs.usuario_ms.dto.UsuarioResponseDTO;
import com.CodeChefs.usuario_ms.model.Usuario;
import com.CodeChefs.usuario_ms.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@example.com");
        usuario.setTelefono("912345678");
        usuario.setDireccion("Av. Principal 123");
        usuario.setRol("CLIENTE");
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDate.now());

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNombre("Juan Perez");
        requestDTO.setEmail("juan@example.com");
        requestDTO.setTelefono("912345678");
        requestDTO.setDireccion("Av. Principal 123");
        requestDTO.setRol("CLIENTE");
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    void debeListarTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar usuario cuando el ID existe")
    void debeRetornarUsuarioCuandoIdExiste() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null cuando el usuario no existe")
    void debeRetornarNullCuandoIdNoExiste() {
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(999);

        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debe guardar un usuario válido")
    void debeGuardarUsuarioValido() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = usuarioService.crearUsuario(requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe actualizar un usuario existente")
    void debeActualizarUsuarioExistente() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = usuarioService.actualizarUsuario(1, requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(usuarioRepository, times(1)).findById(1);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe eliminar un usuario existente")
    void debeEliminarUsuarioExistente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean resultado = usuarioService.eliminarUsuario(1);

        assertTrue(resultado);
        verify(usuarioRepository, times(1)).existsById(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar usuario inexistente")
    void debeRetornarFalseAlEliminarUsuarioInexistente() {
        when(usuarioRepository.existsById(999)).thenReturn(false);

        boolean resultado = usuarioService.eliminarUsuario(999);

        assertFalse(resultado);
        verify(usuarioRepository, times(1)).existsById(999);
        verify(usuarioRepository, never()).deleteById(999);
    }
}