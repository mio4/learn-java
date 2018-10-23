package com.mio4.service;

import com.mio4.dao.CustomerDao;
import com.mio4.domain.Customer;

public class CustomerService {

	public void addCustomer(Customer c) {
		new CustomerDao().addCustomer(c);
	}
}
