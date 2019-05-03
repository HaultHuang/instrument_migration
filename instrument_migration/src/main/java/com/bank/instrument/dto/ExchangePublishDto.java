package com.bank.instrument.dto;

import com.bank.instrument.dto.base.BasePublishDto;

public class ExchangePublishDto extends BasePublishDto{

	private static final long serialVersionUID = 1637705437291646265L;

	private String exchangeCode;
	
	public boolean tradable;

	/**
	 * @return the exchangeCode
	 */
	public String getExchangeCode() {
		return exchangeCode;
	}

	/**
	 * @param exchangeCode the exchangeCode to set
	 */
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	/**
	 * @return the tradable
	 */
	public boolean isTradable() {
		return tradable;
	}

	/**
	 * @param tradable the tradable to set
	 */
	public void setTradable(boolean tradable) {
		this.tradable = tradable;
	}
}
