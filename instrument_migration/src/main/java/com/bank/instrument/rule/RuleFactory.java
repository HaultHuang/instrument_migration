package com.bank.instrument.rule;

import java.util.Map;

public class RuleFactory {

	public static Rule getRule(RuleType ruleType, Map<MappingKeyEnum, MappingKeyEnum> rules) {
		Rule rule;
		switch (ruleType) {
		case FLEXIBLE:
			rule = new FlexibleMappingRule(rules);
			break;
		case DEFAULT:
		default:
			rule = new DefaultMappingRule();
			break;
		}
		return rule;
	}
}
