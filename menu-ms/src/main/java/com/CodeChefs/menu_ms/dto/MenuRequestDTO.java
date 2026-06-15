package com.CodeChefs.menu_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar un plato")
public class MenuRequestDTO {

    @Schema(description = "ID del restaurante al que pertenece el plato", example = "1", required = true)
    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @Schema(description = "Nombre del plato", example = "Pizza Margarita", required = true)
    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripción del plato", example = "Tomate, mozzarella, albahaca")
    private String descripcion;

    @Schema(description = "Precio del plato en pesos", example = "8990", required = true)
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private double precio;

    @Schema(description = "Categoría del plato", example = "PRINCIPAL", allowableValues = {"ENTRADA", "PRINCIPAL", "POSTRE", "BEBIDA"})
    private String categoria;

    private String imagenUrl;

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