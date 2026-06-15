package com.CodeChefs.usuario_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar un usuario")
public class UsuarioRequestDTO {

    @Schema(description = "Nombre completo del usuario", example = "Juan Perez", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Correo electrónico (único)", example = "juan@example.com", required = true)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    private String email;

    @Schema(description = "Número de teléfono", example = "+56912345678")
    @Size(max = 20, message = "El telefono no puede tener mas de 20 caracteres")
    private String telefono;

    @Schema(description = "Dirección del usuario", example = "Av. Siempre Viva 123")
    private String direccion;

    @Schema(description = "Rol del usuario", example = "CLIENTE", allowableValues = {"CLIENTE", "REPARTIDOR", "ADMIN"})
    @NotBlank(message = "El rol es obligatorio")
    private String rol;


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}