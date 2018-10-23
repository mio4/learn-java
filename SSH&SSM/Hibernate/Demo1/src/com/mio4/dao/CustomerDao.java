package com.mio4.dao;

import com.mio4.domain.Customer;
import com.mio4.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerDao {
	/**
	 * 保存一个用户
	 */
	public void addCustomer(Customer c) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(c);
		transaction.commit();
		session.close();
	}
}
