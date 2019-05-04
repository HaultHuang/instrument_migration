package com.bank.instrument.rule;

import java.util.Collection;
import java.util.Map;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;

public interface Rule {

	void publish(BasePublishDto basePublishDto, Collection<InternalPublishDto> internalPublishes);
	
	void addMappingRule(MappingKeyEnum publishKey,MappingKeyEnum exchangeKey);
	
	Map<MappingKeyEnum,MappingKeyEnum> getMappingRules();
	
	InternalPublishDto matchInternalPublishByRules(BasePublishDto basePublishDto, Collection<InternalPublishDto> internalPublishes); 
}
