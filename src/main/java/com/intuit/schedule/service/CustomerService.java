package com.intuit.schedule.service;

import com.intuit.schedule.entity.Customer;
import com.intuit.schedule.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> getCustomer(){
        return repository.findAll();
    }
    AtomicInteger vipCount = new AtomicInteger(0);

    public Customer getNextCustomer() {

        List<Customer> vipCustomers = repository.findByIsVipAndStatus(true, "booked");
        PriorityQueue<Customer> customerQueue = getCustomers(vipCustomers);

        List<Customer> nonVipCustomers = repository.findByIsVipAndStatus(false, "booked");
        PriorityQueue<Customer> nonVipCustomerQueue = getCustomers(nonVipCustomers);

        if (vipCount.get() == 2 && !nonVipCustomerQueue.isEmpty()) {
            vipCount.set(0);
            return nonVipCustomerQueue.peek();
        }

        if (!customerQueue.isEmpty()) {
            System.out.println("in vip peek is:" + nonVipCustomerQueue.peek());
            vipCount.getAndIncrement();
            return customerQueue.peek();
        }

        return null;
    }

    private PriorityQueue<Customer> getCustomers(List<Customer> nonVipCustomers) {
        // Sorting based on time and get the customers
        return nonVipCustomers.stream().collect(
                        () -> new PriorityQueue<>(Comparator.<Customer, Date>comparing(Customer::getTime).reversed()),
                        PriorityQueue::add,
                        AbstractQueue::addAll);
    }

    public Customer save(Customer customer){
        return repository.save(customer);
    }

    public Optional<Customer> findById(Long id){
       return  repository.findById(id);
    }
}
