package com.bank.instrument.service;

import java.util.Collection;
import java.util.Map;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.rule.enums.MappingKeyEnum;

public interface GapService {

	void publish(BasePublishDto basePublishDto);

	Collection<InternalPublishDto> listInternalPublishes();
	
	void setFlexibleRules(Map<MappingKeyEnum,MappingKeyEnum> rules);
}
