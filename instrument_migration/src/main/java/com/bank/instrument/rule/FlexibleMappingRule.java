package com.bank.instrument.rule;

import java.util.Map;

public class FlexibleMappingRule extends AbstractMappingRule {

	public FlexibleMappingRule(Map<MappingKeyEnum, MappingKeyEnum> rules) {
		setMappingRules(rules);
	}
}
