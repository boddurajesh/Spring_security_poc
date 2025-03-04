package com.security.service;



import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entity.Customer;
import com.security.repository.CustomerRepo;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private CustomerRepo crepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Customer c = crepo.findByUname(username);

		return new User(c.getUname(), c.getPwd(), Collections.emptyList());

	}
	
	
	public String saveCustomer(Customer customer) {
		
		String encodedPwd = pwdEncoder.encode(customer.getPwd());
		customer.setPwd(encodedPwd);
		
		crepo.save(customer);
		
		return "Customer Registered Successfully";
		
	}

}
