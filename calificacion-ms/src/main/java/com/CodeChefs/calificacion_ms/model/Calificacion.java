package com.CodeChefs.calificacion_ms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int pedidoId;  // FK al orden-ms

    @Column(nullable = false)
    private int restauranteId;  // FK al restaurante-ms

    @Column(nullable = false)
    private int usuarioId;  // FK al usuario-ms

    @Column(nullable = false)
    private int puntuacion;  // 1 a 5 estrellas

    @Column(length = 500)
    private String comentario;

    @Column(nullable = false)
    private LocalDate fecha;

    // Constructor vacío
    public Calificacion() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public int getRestauranteId() { return restauranteId; }
    public void setRestauranteId(int restauranteId) { this.restauranteId = restauranteId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}