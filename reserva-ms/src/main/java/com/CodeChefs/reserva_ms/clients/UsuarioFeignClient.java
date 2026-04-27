package com.CodeChefs.reserva_ms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-ms", url = "http://localhost:8081")
public interface UsuarioFeignClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioResponse getUsuarioById(@PathVariable("id") int id);

    // Clase interna para mapear la respuesta
    class UsuarioResponse {
        private int id;
        private String nombre;
        private String email;
        private boolean activo;

        // Getters y Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public boolean isActivo() { return activo; }
        public void setActivo(boolean activo) { this.activo = activo; }
    }
}