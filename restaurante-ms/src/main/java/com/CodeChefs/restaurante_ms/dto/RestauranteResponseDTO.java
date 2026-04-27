package com.CodeChefs.restaurante_ms.dto;

public class RestauranteResponseDTO {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioApertura;
    private String horarioCierre;
    private boolean activo;
    private double calificacionPromedio;

    // Constructor
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

    // Getters (solo getters, sin setters)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getHorarioApertura() { return horarioApertura; }
    public String getHorarioCierre() { return horarioCierre; }
    public boolean isActivo() { return activo; }
    public double getCalificacionPromedio() { return calificacionPromedio; }
}