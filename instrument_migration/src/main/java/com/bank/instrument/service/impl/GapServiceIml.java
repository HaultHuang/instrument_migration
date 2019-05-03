package com.bank.instrument.service.impl;

import java.util.List;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.service.GapService;

public class GapServiceIml implements GapService {

	private List<InternalPublishDto> internalPublishDtos;

	@Override
	public void publish(BasePublishDto basePubicDto) {

	}

	/**
	 * @return the internalPublishDtos
	 */
	public List<InternalPublishDto> getInternalPublishDtos() {
		return internalPublishDtos;
	}

	/**
	 * @param internalPublishDtos the internalPublishDtos to set
	 */
	public void setInternalPublishDtos(List<InternalPublishDto> internalPublishDtos) {
		this.internalPublishDtos = internalPublishDtos;
	}
}
