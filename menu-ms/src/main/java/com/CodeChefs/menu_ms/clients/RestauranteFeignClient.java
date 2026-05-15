package com.CodeChefs.menu_ms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurante-ms", url = "http://localhost:8082")
public interface RestauranteFeignClient {

    @GetMapping("/api/v1/restaurantes/{id}")
    RestauranteResponse getRestauranteById(@PathVariable("id") int id);

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