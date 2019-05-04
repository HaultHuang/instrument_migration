package com.bank.instrument.rule;

public enum MappingKeyEnum {
	MARKET("getMarket"),
	LABEL("getLabel"),
	PUBLISH_CODE("getPublishCode"),
	EXCHANGE_CODE("getExchangeCode");
	
	private String key;
	
	MappingKeyEnum(String key) {
		this.key=key;
	}
	
	public static MappingKeyEnum getMappingKey(String key) {
		for(MappingKeyEnum mappingKey:MappingKeyEnum.values()) {
			if(mappingKey.getKey().equals(key)) {
				return mappingKey;
			}
		}
		return null;
	}
	
	public String getKey() {
		return this.key;
	}
	
}