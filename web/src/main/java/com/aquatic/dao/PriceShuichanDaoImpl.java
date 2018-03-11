package com.aquatic.dao;


import java.util.List;

import org.hibernate.SQLQuery;


public class PriceShuichanDaoImpl implements
		PriceShuichanDao {

	@Override
	public List<Object> findYears() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT YEAR(price_shuichan.date) ")
		.append(" FROM price_shuichan")
		.append(" GROUP BY YEAR(price_shuichan.date)");
		//SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findNames() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT price_shuichan.name,COUNT(price_shuichan.name)  ")
		.append(" FROM price_shuichan")
		.append(" GROUP BY price_shuichan.name")
		.append(" HAVING COUNT(price_shuichan.name)>=500");
		//SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}


}
