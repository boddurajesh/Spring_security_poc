package com.security.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.Customer;
import com.security.repository.CustomerRepo;
import com.security.service.JwtService;
import com.security.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	
	
	@Autowired
	private CustomerRepo crepo;
	
	
	@Autowired
	private MyUserDetailsService userDtls;

	

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwt;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome to ashokit";
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody Customer c) {
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(c.getUname(), c.getPwd());

		try {
			org.springframework.security.core.Authentication authenticate = authManager.authenticate(token);

			if (authenticate.isAuthenticated()) {
				String jwtToken = jwt.generateToken(c.getUname());
				return new ResponseEntity<>(jwtToken, HttpStatus.OK);
			}

		} catch (Exception e) {
			//logger
		}

		return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/register")
	public String registerCustomer(@RequestBody Customer customer) {
		
		return userDtls.saveCustomer(customer);
		
	
	}

}
