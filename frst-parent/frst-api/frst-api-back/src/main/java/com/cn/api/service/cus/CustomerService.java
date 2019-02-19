package com.cn.api.service.cus;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.domain.cus.Customer;

@RequestMapping("/back")
public interface CustomerService {

	@RequestMapping("/getCustomerList")
	List<Customer> getCustomerList();
	
}
