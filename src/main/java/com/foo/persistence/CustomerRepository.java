package com.foo.persistence;

import com.foo.persistence.model.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCustomerRef(String customerRef);
}
