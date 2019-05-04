package com.bank.instrument.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.bank.instrument.dto.ExchangePublishDto;
import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.PublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.service.GapService;

public class GapServiceIml implements GapService {

	private Map<String, InternalPublishDto> internalPublishMap = new HashMap<>();
	
	public InternalPublishDto getPublishCache(String publishCode) {
		InternalPublishDto internalPublishCache = internalPublishMap.get(publishCode);
		return (InternalPublishDto)internalPublishCache.clone();
	}
	
	@Override
	public void publish(BasePublishDto basePubicDto) {
		if (basePubicDto instanceof ExchangePublishDto) {
			ExchangePublishDto exchangePublishDto = (ExchangePublishDto) basePubicDto;
			String exchangeCode = exchangePublishDto.getExchangeCode();
			if (internalPublishMap.containsKey(exchangeCode)) {
				InternalPublishDto existInternalPubishDto = internalPublishMap.get(exchangeCode);
				existInternalPubishDto.setTradable(exchangePublishDto.isTradable());
			} else {
				InternalPublishDto internalPublishDto = new InternalPublishDto();
				internalPublishDto.setLastTradingDate(exchangePublishDto.getLastTradingDate());
				internalPublishDto.setDeliveryDate(exchangePublishDto.getDeliveryDate());
				internalPublishDto.setMarket(exchangePublishDto.getMarket());
				internalPublishDto.setLabel(exchangePublishDto.getLabel());
				internalPublishDto.setTradable(exchangePublishDto.isTradable());
				internalPublishMap.put(exchangeCode, internalPublishDto);
			}
		} else if (basePubicDto instanceof PublishDto) {
			PublishDto publishDto = (PublishDto) basePubicDto;
			String publishCode = publishDto.getPublishCode();
			if (internalPublishMap.containsKey(publishCode)) {
				InternalPublishDto existInternalPubishDto = internalPublishMap.get(publishCode);
				existInternalPubishDto.setDeliveryDate(publishDto.getDeliveryDate());
				existInternalPubishDto.setLastTradingDate(publishDto.getLastTradingDate());
			} else {
				InternalPublishDto internalPublishDto = new InternalPublishDto();
				internalPublishDto.setLastTradingDate(publishDto.getLastTradingDate());
				internalPublishDto.setDeliveryDate(publishDto.getDeliveryDate());
				internalPublishDto.setMarket(publishDto.getMarket());
				internalPublishDto.setLabel(publishDto.getLabel());
				internalPublishDto.setTradable(true);
				internalPublishMap.put(publishCode, internalPublishDto);
			}
		}
	}

	@Override
	public Collection<InternalPublishDto> listInternalPublishes() {
		internalPublishMap.forEach((key, value) -> System.out.println(value.toString()));
		System.out.println("-----------");
		return internalPublishMap.values();
	}
}
