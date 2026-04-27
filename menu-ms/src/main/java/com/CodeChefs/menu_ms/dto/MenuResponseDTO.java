package com.CodeChefs.menu_ms.dto;

public class MenuResponseDTO {

    private int id;
    private int restauranteId;
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;
    private boolean disponible;
    private String imagenUrl;

    // Constructor
    public MenuResponseDTO(int id, int restauranteId, String nombre, String descripcion,
                           double precio, String categoria, boolean disponible, String imagenUrl) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.disponible = disponible;
        this.imagenUrl = imagenUrl;
    }

    // Getters
    public int getId() { return id; }
    public int getRestauranteId() { return restauranteId; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public boolean isDisponible() { return disponible; }
    public String getImagenUrl() { return imagenUrl; }
}