package com.CodeChefs.reserva_ms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int restauranteId;  // FK al restaurante-ms

    @Column(nullable = false)
    private int usuarioId;      // FK al usuario-ms

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private int numeroPersonas;

    @Column(nullable = false, length = 50)
    private String estado;  // PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA

    @Column(nullable = false)
    private boolean activo = true;

    private String comentarios;

    // Constructor vacío
    public Reserva() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRestauranteId() { return restauranteId; }
    public void setRestauranteId(int restauranteId) { this.restauranteId = restauranteId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public int getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(int numeroPersonas) { this.numeroPersonas = numeroPersonas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}