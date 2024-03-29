package com.intuit.schedule.service;

import com.intuit.schedule.entity.Customer;
import com.intuit.schedule.repo.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomer() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<Customer> result = customerService.getCustomer();

        assertEquals(mockCustomers, result);
    }


    public void testGetNextCustomer() {
        List<Customer> mockVIPCustomers = new ArrayList<>();
        // Add some mock VIP customers to the list
        mockVIPCustomers.add(new Customer(1L, "Test123", "123456", true, Customer.StatusType.booked, new Date()));
        mockVIPCustomers.add(new Customer(2L, "Test321", "654321", true, Customer.StatusType.booked, new Date()));

        List<Customer> mockNonVIPCustomers = new ArrayList<>();
        // Add some mock non-VIP customers to the list
        mockNonVIPCustomers.add(new Customer(1L, "Test123", "123456", false, Customer.StatusType.booked, new Date()));
        mockNonVIPCustomers.add(new Customer(1L, "Test123", "123456", false, Customer.StatusType.booked, new Date()));

        when(customerRepository.findByIsVipAndStatus(true, Customer.StatusType.booked)).thenReturn(mockVIPCustomers);
        when(customerRepository.findByIsVipAndStatus(false, Customer.StatusType.booked)).thenReturn(mockNonVIPCustomers);

        Customer result1 = customerService.getNextCustomer();
        Customer result2 = customerService.getNextCustomer();
        Customer result3 = customerService.getNextCustomer();
        //  assertions:
        assertEquals(3L, result1.getId());
        assertEquals(3L, result3.getId());
    }
}

