package com.maple.study.easy.mock.demo;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.hamcrest.core.AnyOf;
import org.junit.Test;

import com.maple.study.easy.mock.demo.dao.ExchangeRate;
import com.maple.study.easy.mock.demo.dao.entity.Currency;

public class CurrencyTest {

	private Currency usdObject = new Currency(2.50, "USD");
	private Currency cadObject = new Currency(2.50, "CAD");
	
	@Test
	public void testToEuros() throws Exception {
		Currency expected = new Currency(3.75, "EUR");
		ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);
		EasyMock.expect(mock.getRate("USD", "EUR")).andReturn(1.5);
		EasyMock.replay(mock);
		Currency actual = usdObject.toEuros(mock);
		assertEquals(actual, expected);
	}
	
	@Test
	public void testExchangeRateServiceUnavailable() throws Exception {
		ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);
		EasyMock.expect(mock.getRate("USD", "EUR")).andThrow(new Exception());
		EasyMock.replay(mock);
		Currency actual = usdObject.toEuros(mock);
		assertNull(actual);
	}
	
	@Test
	public void testMockToEuros() throws Exception {
	    Currency expected = new Currency(3.75, "EUR");
	    ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);
	    EasyMock.expect(mock.getRate("USD", "EUR")).andReturn(1.5);
	    EasyMock.replay(mock);
	    Currency actual = usdObject.toEuros(mock);
	    assertEquals(expected, actual);
	    EasyMock.verify(mock);
	}
	@Test
	public void testNiceMockToEuros() throws Exception {
		Currency expectedUSD = new Currency(3.75, "EUR");
		Currency expectedCAD = new Currency(5, "EUR");
		ExchangeRate mock = EasyMock.createNiceMock(ExchangeRate.class);
		EasyMock.expect(mock.getRate("USD", "EUR")).andReturn(1.5);
		EasyMock.expect(mock.getRate("CAD", "EUR")).andReturn(2.0).times(1,5);
		EasyMock.expect(mock.getRate(EasyMock.notNull(), EasyMock.notNull())).andReturn(4.0).times(1, 5);
		EasyMock.replay(mock);
		Currency actualUSD = usdObject.toEuros(mock);
		Currency actualCAD = cadObject.toEuros(mock);
		actualCAD = cadObject.toEuros(mock);
		assertEquals(expectedUSD, actualUSD);
		assertEquals(expectedCAD, actualCAD);
		Currency otherObject = new Currency(2.5, "RMB").toEuros(mock);
		assertEquals(new Currency(10, "EUR"), otherObject);
		EasyMock.verify(mock);
	}

}
