package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
     
	     public Customer findByUname(String cuname);
}
