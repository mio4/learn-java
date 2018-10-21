package com.mio4.dao;

import com.mio4.domain.Product;

import java.util.List;

public interface IProductDao {
	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;
}
