package com.bank.instrument.rule;

import com.bank.instrument.rule.enums.MappingKeyEnum;
import com.bank.instrument.rule.enums.RuleType;

import java.util.Map;

public class RuleFactory {

    public static Rule getRule(RuleType ruleType, Map<MappingKeyEnum, MappingKeyEnum> rules) {
        Rule rule;
        switch (ruleType) {
            case FLEXIBLE:
                rule = FlexibleMappingRule.getInstance();
                rule.setMappingRules(rules);
                break;
            case DEFAULT:
            default:
                rule = DefaultMappingRule.getInstance();
                break;
        }
        return rule;
    }
}
