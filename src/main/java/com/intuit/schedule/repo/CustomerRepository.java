package com.intuit.schedule.repo;

import com.intuit.schedule.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    //@Query(value = "SELECT * FROM customer WHERE is_vip = true", nativeQuery = true)
    //List<Customer> findByIsVipTrueAndStatus(String status);

    List<Customer> findByIsVipAndStatus(boolean isVip, String status);

    //@Query(value = "SELECT * FROM customer WHERE is_vip = false order by time desc", nativeQuery = true)
    //List<Customer> findByIsVipFalse();

}