package com.bank.instrument.rule.enums;

public enum MappingKeyEnum {
	MARKET("getMarket"),
	LABEL("getLabel"),
	PUBLISH_CODE("getPublishCode"),
	EXCHANGE_CODE("getExchangeCode");
	
	private String key;
	
	MappingKeyEnum(String key) {
		this.key=key;
	}
	
	public String getKey() {
		return this.key;
	}
	
}
