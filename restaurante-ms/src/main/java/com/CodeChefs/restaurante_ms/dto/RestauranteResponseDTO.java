package com.CodeChefs.restaurante_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de respuesta de un restaurante")
public class RestauranteResponseDTO {

    @Schema(description = "Identificador único del restaurante", example = "1")
    private int id;

    @Schema(description = "Nombre del restaurante", example = "Pizzeria Centro")
    private String nombre;

    @Schema(description = "Dirección del restaurante", example = "Av. Siempre Viva 123")
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;

    @Schema(description = "Hora de apertura", example = "09:00")
    private String horarioApertura;

    @Schema(description = "Hora de cierre", example = "22:00")
    private String horarioCierre;

    @Schema(description = "Indica si el restaurante está activo", example = "true")
    private boolean activo;

    @Schema(description = "Calificación promedio del restaurante (1-5)", example = "4.5")
    private double calificacionPromedio;

    public RestauranteResponseDTO(int id, String nombre, String direccion, String telefono,
                                  String horarioApertura, String horarioCierre,
                                  boolean activo, double calificacionPromedio) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.activo = activo;
        this.calificacionPromedio = calificacionPromedio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getHorarioApertura() { return horarioApertura; }
    public String getHorarioCierre() { return horarioCierre; }
    public boolean isActivo() { return activo; }
    public double getCalificacionPromedio() { return calificacionPromedio; }
}