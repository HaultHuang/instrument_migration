package com.bank.instrument.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.rule.MappingKeyEnum;
import com.bank.instrument.rule.Rule;
import com.bank.instrument.rule.RuleFactory;
import com.bank.instrument.rule.RuleType;
import com.bank.instrument.service.GapService;

public class GapServiceIml implements GapService {

	private ConcurrentLinkedQueue<InternalPublishDto> internalPublishes = new ConcurrentLinkedQueue<>();

	private Rule rule;
	
	public GapServiceIml() {
		rule = RuleFactory.getRule(RuleType.DEFAULT, null);
	}
	
	@Override
	public void publish(BasePublishDto basePublishDto) {
		rule.publish(basePublishDto, internalPublishes);
	}

	@Override
	public Collection<InternalPublishDto> listInternalPublishes() {
		internalPublishes.stream().forEach(p->System.out.println(p));
		return internalPublishes;
	}
	
	@Override
	public void setFlexibleRule(Map<MappingKeyEnum, MappingKeyEnum> rules) {
		rule = RuleFactory.getRule(RuleType.FLEXIBLE, rules);
	}
}
