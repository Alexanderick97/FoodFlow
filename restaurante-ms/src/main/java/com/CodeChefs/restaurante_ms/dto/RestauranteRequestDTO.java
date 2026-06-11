package com.CodeChefs.restaurante_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar un restaurante")
public class RestauranteRequestDTO {

    @Schema(description = "Nombre del restaurante", example = "Pizzeria Centro", required = true)
    @NotBlank(message = "El nombre del restaurante es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Dirección física del restaurante", example = "Av. Siempre Viva 123")
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    @Size(max = 20, message = "El teléfono no puede tener más de 20 caracteres")
    private String telefono;

    @Schema(description = "Hora de apertura (formato HH:mm)", example = "09:00")
    private String horarioApertura;

    @Schema(description = "Hora de cierre (formato HH:mm)", example = "22:00")
    private String horarioCierre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorarioApertura() { return horarioApertura; }
    public void setHorarioApertura(String horarioApertura) { this.horarioApertura = horarioApertura; }

    public String getHorarioCierre() { return horarioCierre; }
    public void setHorarioCierre(String horarioCierre) { this.horarioCierre = horarioCierre; }
}