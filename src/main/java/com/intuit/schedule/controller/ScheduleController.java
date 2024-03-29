package com.intuit.schedule.controller;

import com.intuit.schedule.entity.Customer;
import com.intuit.schedule.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/schedule/customer")
@Api(tags = "Customer Schedule API")
@Slf4j
public class ScheduleController {

    private final CustomerService service;

    @Autowired
    public ScheduleController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation("Get customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerName(@PathVariable Long id) {
        Optional<Customer> byId = service.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @ApiOperation("Get the next customer")
    @GetMapping("/nextCustomer")
    public ResponseEntity<Customer> getNextCustomer() {
        return ResponseEntity.ok(service.getNextCustomer());
    }


    @ApiOperation("Find all customers")
    @GetMapping("/findAll")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(service.getCustomer());
    }


    @ApiOperation("Create a new customer")
    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        log.info("customer is:{}", customer);
        customer.setTime(new Date());
        customer.setStatus(Customer.StatusType.booked);
        return ResponseEntity.ok(service.save(customer));
    }

    @ApiOperation("Update customer status")
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestParam String status) {
        Optional<Customer> customerOptional = service.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (Objects.equals(status, "canceled"))
                customer.setStatus(Customer.StatusType.cancel);
            else if (Objects.equals(status, "checkedIn"))
                customer.setStatus(Customer.StatusType.checkedIn);
            Customer updatedCustomer = service.save(customer);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
