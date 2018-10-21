package com.mio4.service.impl;

import com.mio4.dao.IProductDao;
import com.mio4.dao.impl.ProductDaoImpl;
import com.mio4.domain.Product;
import com.mio4.service.IProductService;

import java.util.List;

public class ProductServiceImpl implements IProductService {
	public List<Product> findNew() throws Exception{
		IProductDao productDao = new ProductDaoImpl();
		return productDao.findNew();
	}

	public List<Product> findHot() throws Exception{
		IProductDao productDao = new ProductDaoImpl();
		return productDao.findHot();
	}
}
