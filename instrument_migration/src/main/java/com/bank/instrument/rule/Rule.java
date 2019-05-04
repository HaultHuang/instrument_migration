package com.bank.instrument.rule;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;

public interface Rule {

	void publishByRules(BasePublishDto basePublishDto, Collection<InternalPublishDto> internalPublishes);
	
	void addMappingRule(MappingKeyEnum publishKey,MappingKeyEnum exchangeKey);
	
	Map<MappingKeyEnum,MappingKeyEnum> getMappingRules();

	List<InternalPublishDto> matchInternalPublishByRules(BasePublishDto basePublishDto, Collection<InternalPublishDto> internalPublishes);

	void setMappingRules(Map<MappingKeyEnum, MappingKeyEnum> rules);
}
