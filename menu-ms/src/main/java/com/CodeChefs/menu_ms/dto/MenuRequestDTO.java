package com.CodeChefs.menu_ms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MenuRequestDTO {

    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private double precio;

    private String categoria;

    private String imagenUrl;

    // Getters y Setters
    public int getRestauranteId() { return restauranteId; }
    public void setRestauranteId(int restauranteId) { this.restauranteId = restauranteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}