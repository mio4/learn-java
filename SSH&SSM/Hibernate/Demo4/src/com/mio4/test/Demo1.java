package com.mio4.test;

import com.mio4.domain.Customer;
import com.mio4.domain.Linkman;
import com.mio4.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 测试一对多
 */
public class Demo1 {

	/**
	 * 双向关联
	 */
	@Test
	public void test1(){
		Session session = HibernateUtils.getSession();
		Transaction tr = session.beginTransaction();

		Customer c1 = new Customer();
		c1.setCust_name("mio1");
		Linkman l1 = new Linkman();
		l1.setLkm_name("l1");
		Linkman l2 = new Linkman();
		l2.setLkm_name("l2");

		c1.getLinkmans().add(l1);
		c1.getLinkmans().add(l2);
		l1.setCustomer(c1);
		l2.setCustomer(c1);

		session.save(c1);
		session.save(l1);
		session.save(l2);

		tr.commit();

	}

}
