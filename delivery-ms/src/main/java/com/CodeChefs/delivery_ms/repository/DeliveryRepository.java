package com.CodeChefs.deliveryMS.repository;

import com.deliveryMS.model.delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
    
}