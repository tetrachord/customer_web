package com.foo;

import com.foo.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    void shouldReturnHttpStatusCreatedForPost() {

        ResponseEntity<String> response = createCustomer();

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldReturnCustomerByCustomerRefForGet() {

        // given
        ResponseEntity<String> response = createCustomer();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CustomerDto expectedCustomerDto = new CustomerDto(
                "ABC123",
                "Joe Bloggs",
                "123, Some Street",
                "Some Area",
                "Some Town",
                "Some County",
                "UK",
                "CBA 321");

        // when
        CustomerDto actualCustomerDto = template.getForObject("/customer/ABC123", CustomerDto.class);

        // then
        assertEquals(expectedCustomerDto, actualCustomerDto);
    }

    @Test
    void shouldReturnNotFoundForGetByInvalidCustomerRef() {

        // given
        ResponseEntity<String> postResponse = createCustomer();

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        // when
        ResponseEntity<String> getResponse = template.getForEntity("/customer/XYZ456", String.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    private ResponseEntity<String> createCustomer() {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CustomerDto customerDto = new CustomerDto(
                "ABC123",
                "Joe Bloggs",
                "123, Some Street",
                "Some Area",
                "Some Town",
                "Some County",
                "UK",
                "CBA 321");

        HttpEntity<CustomerDto> request =
                new HttpEntity<>(customerDto, headers);

        // when
        return template.postForEntity("/customer", request, String.class);
    }
}
