package com.CodeChefs.calificacion_ms.dto;

import java.time.LocalDate;

public class CalificacionResponseDTO {

    private int id;
    private int pedidoId;
    private int restauranteId;
    private int usuarioId;
    private int puntuacion;
    private String comentario;
    private LocalDate fecha;

    // Constructor
    public CalificacionResponseDTO(int id, int pedidoId, int restauranteId, int usuarioId,
                                   int puntuacion, String comentario, LocalDate fecha) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.restauranteId = restauranteId;
        this.usuarioId = usuarioId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // Getters
    public int getId() { return id; }
    public int getPedidoId() { return pedidoId; }
    public int getRestauranteId() { return restauranteId; }
    public int getUsuarioId() { return usuarioId; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public LocalDate getFecha() { return fecha; }
}