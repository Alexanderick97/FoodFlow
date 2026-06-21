package com.CodeChefs.pago_ms.client;

import com.CodeChefs.pago_ms.dto.OrdenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orden-ms", url = "http://localhost:8086")
public interface OrdenClient {

    @GetMapping("/ordenes/{id}")
    OrdenResponseDTO obtenerOrden(@PathVariable Long id);

}
