package com.CodeChefs.calificacion_ms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "restaurante-ms", url = "http://localhost:8082")
public interface RestauranteFeignClient {

    @GetMapping("/api/v1/restaurantes/{id}")
    RestauranteResponse getRestauranteById(@PathVariable("id") int id);

    @PatchMapping("/api/v1/restaurantes/{id}/promedio")
    void actualizarPromedio(@PathVariable("id") int id,
                            @RequestParam("promedio") double promedio);

    // Clase interna para mapear la respuesta
    class RestauranteResponse {
        private int id;
        private String nombre;
        private boolean activo;

        // Getters y Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public boolean isActivo() { return activo; }
        public void setActivo(boolean activo) { this.activo = activo; }
    }
}