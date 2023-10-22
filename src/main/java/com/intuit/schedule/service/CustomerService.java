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

        List<Customer> vipCustomers = repository.findByIsVipAndStatus(true, Customer.StatusType.booked);
        PriorityQueue<Customer> vipQueue = getCustomers(vipCustomers);

        List<Customer> nonVipCustomers = repository.findByIsVipAndStatus(false, Customer.StatusType.booked);
        PriorityQueue<Customer> nonVipCustomerQueue = getCustomers(nonVipCustomers);

        if (!vipQueue.isEmpty() && vipCount.get()!=2) {
            log.info("in vip peek is:{}", nonVipCustomerQueue.peek());
            vipCount.getAndIncrement();
            return vipQueue.peek();
        }
        if (vipCount.get() == 2 && !nonVipCustomerQueue.isEmpty()) {
            vipCount.set(0);
            return nonVipCustomerQueue.peek();
        } else if (vipQueue.isEmpty()) {
            return nonVipCustomerQueue.peek();
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
