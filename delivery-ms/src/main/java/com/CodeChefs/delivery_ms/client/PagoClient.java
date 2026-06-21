package com.CodeChefs.delivery_ms.client;

import com.CodeChefs.delivery_ms.dto.PagoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pago-ms", url = "http://localhost:8088")
public interface PagoClient {

    @GetMapping("/pagos/orden/{idOrden}")
    PagoResponseDTO obtenerPagoPorOrden(
            @PathVariable Long idOrden);

}
