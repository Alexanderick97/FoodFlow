package com.CodeChefs.usuario_ms.dto;

import java.time.LocalDate;

public class UsuarioResponseDTO {

    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String rol;
    private boolean activo;
    private LocalDate fechaRegistro;

    // Constructor
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

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
}