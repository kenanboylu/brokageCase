package com.firm.brokage.repository;

import com.firm.brokage.model.CustomerModel;
import jakarta.persistence.IdClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
