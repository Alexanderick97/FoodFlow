package com.CodeChefs.menu_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de respuesta de un plato")
public class MenuResponseDTO {

    @Schema(description = "Identificador único del plato", example = "1")
    private int id;

    @Schema(description = "ID del restaurante al que pertenece", example = "1")
    private int restauranteId;

    @Schema(description = "Nombre del plato", example = "Pizza Margarita")
    private String nombre;

    @Schema(description = "Descripción del plato", example = "Tomate, mozzarella, albahaca")
    private String descripcion;

    @Schema(description = "Precio del plato en pesos", example = "8990")
    private double precio;

    @Schema(description = "Categoría del plato", example = "PRINCIPAL")
    private String categoria;

    @Schema(description = "Indica si el plato está disponible", example = "true")
    private boolean disponible;

    @Schema(description = "URL de la imagen del plato", example = "https://ejemplo.com/pizza.jpg")
    private String imagenUrl;

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

    public int getId() { return id; }
    public int getRestauranteId() { return restauranteId; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public boolean isDisponible() { return disponible; }
    public String getImagenUrl() { return imagenUrl; }
}