package com.mio4.dao.impl;

import com.mio4.dao.IProductDao;
import com.mio4.domain.Product;
import com.mio4.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;


public class ProductDaoImpl implements IProductDao {

	/**
	 * 查询最新商品
	 * @return
	 * @throws Exception
	 */
	public List<Product> findNew() throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate limit 5";
		return qr.query(sql,new BeanListHandler<>(Product.class));
	}

	/**
	 * 查询最热商品
	 * @return
	 * @throws Exception
	 */
	public List<Product> findHot() throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = 1 limit 5";
		return qr.query(sql,new BeanListHandler<>(Product.class));
	}
}
