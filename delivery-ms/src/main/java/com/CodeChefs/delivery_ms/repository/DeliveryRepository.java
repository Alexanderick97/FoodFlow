package com.CodeChefs.delivery_ms.repository;

import com.CodeChefs.delivery_ms.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
    
}
