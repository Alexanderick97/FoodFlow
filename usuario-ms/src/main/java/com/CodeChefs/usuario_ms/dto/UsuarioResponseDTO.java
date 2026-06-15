package com.CodeChefs.usuario_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Datos de respuesta de un usuario")
public class UsuarioResponseDTO {

    @Schema(description = "Identificador único del usuario", example = "1")
    private int id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Perez")
    private String nombre;

    @Schema(description = "Correo electrónico", example = "juan@example.com")
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;

    @Schema(description = "Dirección del usuario", example = "Av. Siempre Viva 123")
    private String direccion;

    @Schema(description = "Rol del usuario", example = "CLIENTE")
    private String rol;

    @Schema(description = "Indica si el usuario está activo", example = "true")
    private boolean activo;

    @Schema(description = "Fecha de registro del usuario", example = "2026-06-11")
    private LocalDate fechaRegistro;

    public UsuarioResponseDTO(int id, String nombre, String email, String telefono,
                              String direccion, String rol, boolean activo, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
}