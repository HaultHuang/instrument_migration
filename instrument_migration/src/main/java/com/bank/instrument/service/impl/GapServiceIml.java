package com.bank.instrument.service.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.rule.DefaultMappingRule;
import com.bank.instrument.rule.Rule;
import com.bank.instrument.service.GapService;

public class GapServiceIml implements GapService {

	private ConcurrentLinkedQueue<InternalPublishDto> internalPublishes = new ConcurrentLinkedQueue<>();

	@Override
	public void publish(BasePublishDto basePublishDto) {
		Rule rule = new DefaultMappingRule();
		rule.publish(basePublishDto, internalPublishes);
	}

	@Override
	public Collection<InternalPublishDto> listInternalPublishes() {
		internalPublishes.stream().forEach(p->System.out.println(p));
		return internalPublishes;
	}
}
