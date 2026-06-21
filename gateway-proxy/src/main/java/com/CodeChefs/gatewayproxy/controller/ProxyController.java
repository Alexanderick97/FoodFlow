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

    @RequestMapping("/api/v1/usuarios/**")
    public ResponseEntity<?> proxyUsuarios(HttpServletRequest request) {
        return forwardRequest("http://localhost:8081", request);
    }

    @RequestMapping("/api/v1/restaurantes/**")
    public ResponseEntity<?> proxyRestaurantes(HttpServletRequest request) {
        return forwardRequest("http://localhost:8082", request);
    }

    @RequestMapping("/api/v1/menu/**")
    public ResponseEntity<?> proxyMenu(HttpServletRequest request) {
        return forwardRequest("http://localhost:8083", request);
    }

    @RequestMapping("/api/v1/reservas/**")
    public ResponseEntity<?> proxyReservas(HttpServletRequest request) {
        return forwardRequest("http://localhost:8084", request);
    }

    @RequestMapping("/api/v1/calificaciones/**")
    public ResponseEntity<?> proxyCalificaciones(HttpServletRequest request) {
        return forwardRequest("http://localhost:8085", request);
    }

    private ResponseEntity<?> forwardRequest(String baseUrl, HttpServletRequest request) {
        try {
            String path = request.getRequestURI();
            String query = request.getQueryString();
            String fullUrl = baseUrl + path + (query != null ? "?" + query : "");
            HttpMethod method = HttpMethod.valueOf(request.getMethod());

            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (!"host".equalsIgnoreCase(headerName) && !"connection".equalsIgnoreCase(headerName)) {
                    headers.add(headerName, request.getHeader(headerName));
                }
            }

            // Leer el cuerpo de la petición correctamente
            byte[] body = request.getInputStream().readAllBytes();
            HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);

            // Hacer la llamada al microservicio destino
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    fullUrl,
                    method,
                    entity,
                    byte[].class
            );

            // Devolver la respuesta al cliente
            return ResponseEntity.status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error en el Gateway: " + e.getMessage()).getBytes());
        }
    }
}