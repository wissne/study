package com.maple.study.easy.mock.demo.dao.entity;

import com.maple.study.easy.mock.demo.dao.ExchangeRate;

public class Currency {
	private String units;
	private long amount;
	private int cents;

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getCents() {
		return cents;
	}

	public void setCents(int cents) {
		this.cents = cents;
	}

	public Currency() {
	}

	public Currency(String units, long amount, int cents) {
		this.units = units;
		this.amount = amount;
		this.cents = cents;
	}

	public Currency(double amount, String code) {
		this.units = code;
		setAmount(amount);
	}

	private void setAmount(double amount) {
		this.amount = new Double(amount).longValue();
		this.cents = (int) ((amount * 100.0) % 100);
	}

	public Currency toEuros(ExchangeRate converter) {
		if ("EUR".equals(units))
			return this;
		else {
			double input = amount + cents / 100.0;
			double rate;
			try {
				rate = converter.getRate(units, "EUR");
				double output = input * rate;
				return new Currency(output, "EUR");
			} catch (Exception ex) {
				return null;
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (amount != other.amount)
			return false;
		if (cents != other.cents)
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Currency [units=%s, amount=%s, cents=%s]", units,
				amount, cents);
	}

}
