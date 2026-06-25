import com.CodeChefs.pago_ms.client.OrdenClient;
import com.CodeChefs.pago_ms.dto.OrdenResponseDTO;
import com.CodeChefs.pago_ms.model.Pago;
import com.CodeChefs.pago_ms.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repo;

    @Autowired
    private OrdenClient ordenClient;

    public List<Pago> listar() {
        return repo.findAll();
    }

    public Pago guardarPago(Pago pago) {
    return repo.save(pago);
}

    public Pago procesarPago(Pago pago) {

        // Consultar la orden en orden-ms
        OrdenResponseDTO orden =
                ordenClient.obtenerOrden(pago.getIdOrden());

        // Verificar que exista
        if (orden == null) {
            throw new RuntimeException("La orden no existe");
        }

        // El pago queda asociado a la orden
        pago.setEstado("PAGADO");

        return repo.save(pago);
    }

    public Pago obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Pago obtenerPorOrden(Long idOrden) {
    return repo.findByIdOrden(idOrden)
            .orElse(null);
}
}
