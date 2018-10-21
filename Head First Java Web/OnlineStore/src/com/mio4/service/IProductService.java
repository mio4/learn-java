package com.mio4.service;

import com.mio4.domain.Product;

import java.util.List;

public interface IProductService {
	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;
}
