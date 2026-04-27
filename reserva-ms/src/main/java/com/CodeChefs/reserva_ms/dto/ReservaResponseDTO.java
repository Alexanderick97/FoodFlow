package com.CodeChefs.reserva_ms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponseDTO {

    private int id;
    private int restauranteId;
    private int usuarioId;
    private LocalDate fecha;
    private LocalTime hora;
    private int numeroPersonas;
    private String estado;
    private boolean activo;
    private String comentarios;

    // Constructor
    public ReservaResponseDTO(int id, int restauranteId, int usuarioId, LocalDate fecha,
                              LocalTime hora, int numeroPersonas, String estado,
                              boolean activo, String comentarios) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
        this.estado = estado;
        this.activo = activo;
        this.comentarios = comentarios;
    }

    // Getters
    public int getId() { return id; }
    public int getRestauranteId() { return restauranteId; }
    public int getUsuarioId() { return usuarioId; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public int getNumeroPersonas() { return numeroPersonas; }
    public String getEstado() { return estado; }
    public boolean isActivo() { return activo; }
    public String getComentarios() { return comentarios; }
}