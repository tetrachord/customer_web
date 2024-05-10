package com.foo.web.controller;

import com.foo.persistence.model.CustomerEntity;
import com.foo.persistence.CustomerRepository;
import com.foo.web.model.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerDto customerDto) {

        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerRef(customerDto.getCustomerRef())
                .customerName(customerDto.getCustomerName())
                .addressLine1(customerDto.getAddressLine1())
                .addressLine2(customerDto.getAddressLine2())
                .town(customerDto.getTown())
                .county(customerDto.getCounty())
                .country(customerDto.getCountry())
                .postCode(customerDto.getPostCode())
                .build();

        customerRepository.save(customerEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/customer/{customerRef}", produces = "application/json")
    public ResponseEntity<CustomerDto> getCustomerByCustomerRef(@PathVariable("customerRef") String customerRef) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerRef(customerRef);

        if (optionalCustomerEntity.isPresent()) {

            CustomerEntity customerEntity = optionalCustomerEntity.get();

            CustomerDto customerDto = CustomerDto.builder()
                    .customerRef(customerEntity.getCustomerRef())
                    .customerName(customerEntity.getCustomerName())
                    .addressLine1(customerEntity.getAddressLine1())
                    .addressLine2(customerEntity.getAddressLine2())
                    .town(customerEntity.getTown())
                    .county(customerEntity.getCounty())
                    .country(customerEntity.getCountry())
                    .postCode(customerEntity.getPostCode())
                    .build();

            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
