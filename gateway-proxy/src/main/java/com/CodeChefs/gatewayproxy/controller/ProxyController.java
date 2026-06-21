package com.CodeChefs.gatewayproxy.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String usuarioUrl = getEnv("DESTINO_USUARIO", "http://localhost:8081");
    private final String restauranteUrl = getEnv("DESTINO_RESTAURANTE", "http://localhost:8082");
    private final String menuUrl = getEnv("DESTINO_MENU", "http://localhost:8083");
    private final String reservaUrl = getEnv("DESTINO_RESERVA", "http://localhost:8084");
    private final String calificacionUrl = getEnv("DESTINO_CALIFICACION", "http://localhost:8085");

    private String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }

    @RequestMapping("/api/v1/usuarios/**")
    public ResponseEntity<?> proxyUsuarios(HttpServletRequest request) {
        return forwardRequest(usuarioUrl, request);
    }

    @RequestMapping("/api/v1/restaurantes/**")
    public ResponseEntity<?> proxyRestaurantes(HttpServletRequest request) {
        return forwardRequest(restauranteUrl, request);
    }

    @RequestMapping("/api/v1/menu/**")
    public ResponseEntity<?> proxyMenu(HttpServletRequest request) {
        return forwardRequest(menuUrl, request);
    }

    @RequestMapping("/api/v1/reservas/**")
    public ResponseEntity<?> proxyReservas(HttpServletRequest request) {
        return forwardRequest(reservaUrl, request);
    }

    @RequestMapping("/api/v1/calificaciones/**")
    public ResponseEntity<?> proxyCalificaciones(HttpServletRequest request) {
        return forwardRequest(calificacionUrl, request);
    }

    private ResponseEntity<?> forwardRequest(String baseUrl, HttpServletRequest request) {
        try {
            String path = request.getRequestURI();
            String query = request.getQueryString();
            String fullUrl = baseUrl + path + (query != null ? "?" + query : "");
            HttpMethod method = HttpMethod.valueOf(request.getMethod());

            String body = null;
            if (request.getContentLength() > 0) {
                body = request.getReader().lines().reduce("", (acc, line) -> acc + line);
            }

            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.add(headerName, request.getHeader(headerName));
            }

            HttpEntity<?> entity = body != null ? new HttpEntity<>(body, headers) : new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(fullUrl, method, entity, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el Gateway: " + e.getMessage());
        }
    }
}