package com.intuit.schedule.controller;

import com.intuit.schedule.entity.Customer;
import com.intuit.schedule.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleControllerTest {

    @Mock
    private CustomerService customerService;

    private ScheduleController scheduleController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scheduleController = new ScheduleController(customerService);
    }

    @Test
    public void testGetCustomerName() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        ResponseEntity<Customer> response = scheduleController.getCustomerName(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testGetCustomerNameNotFound() {
        Long customerId = 2L;
        when(customerService.findById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = scheduleController.getCustomerName(customerId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNextCustomer() {
        Customer customer = new Customer(); // Set up a customer object
        when(customerService.getNextCustomer()).thenReturn(customer);

        ResponseEntity<Customer> response = scheduleController.getNextCustomer();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customers = new ArrayList<>(); // Set up a list of customers
        when(customerService.getCustomer()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = scheduleController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer(); // Set up a customer object
        when(customerService.save(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = scheduleController.createCustomer(customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        Long customerId = 3L;
        Customer customer = new Customer(); // Set up a customer object
        customer.setId(customerId);
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerService.save(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = scheduleController.updateCustomer(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CheckedIn", response.getBody().getStatus());
    }

    @Test
    public void testUpdateCustomerNotFound() {
        Long customerId = 4L;
        when(customerService.findById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = scheduleController.updateCustomer(customerId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
