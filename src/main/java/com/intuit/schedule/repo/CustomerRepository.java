package com.intuit.schedule.repo;

import com.intuit.schedule.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByIsVipAndStatus(boolean isVip, String status);

}