package com.bank.instrument.dto;

import com.bank.instrument.dto.base.BasePublishDto;

public class PublishDto extends BasePublishDto{

	private static final long serialVersionUID = 4494168584950976332L;

	
	@Override
	public String toString() {
		return new StringBuilder("PublishDto: lastTradingDate:").append(getLastTradingDate())
				.append(", deliveryDate:").append(getDeliveryDate())
				.append(", market:").append(getMarket())
				.append(", label:").append(getLabel()).toString();
	}
}
