package com.CodeChefs.ordenMS.repository;



import com.CodeChefs.ordenMS.model.Orden;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
