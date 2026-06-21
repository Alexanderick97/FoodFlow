package com.CodeChefs.PagoMS.repository;

import com.CodeChefs.PagoMS.model.Pago;
import org.SpringFramework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, long> {
  Optional<Pago> findByIdOrden(Long idOrden);

}
