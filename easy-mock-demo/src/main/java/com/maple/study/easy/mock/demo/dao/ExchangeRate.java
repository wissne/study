package com.maple.study.easy.mock.demo.dao;

public interface ExchangeRate {
	double getRate(String inputCurrency, String outputCurrency) throws Exception;
}
