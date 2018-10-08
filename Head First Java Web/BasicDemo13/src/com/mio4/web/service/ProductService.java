package com.mio4.web.service;

import com.mio4.web.domain.Product;

import java.util.List;

public class ProductService {

	/**
	 * 查询所有的商品
	 * @return list<Product>
	 */
	public List<Product> findAll(){
		return ProductDao().findAll();
	}
}
