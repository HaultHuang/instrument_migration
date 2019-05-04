package com.bank.instrument.rule;

import com.bank.instrument.rule.enums.MappingKeyEnum;

/**
 * Map key by exchangeCode and publishCode
 *
 * @author HuangHao
 */
public class DefaultMappingRule extends AbstractMappingRule {

    private static volatile DefaultMappingRule defaultMappingRule = null;

    private DefaultMappingRule() {
        addMappingRule(MappingKeyEnum.PUBLISH_CODE, MappingKeyEnum.EXCHANGE_CODE);
    }

    public static DefaultMappingRule getInstance() {
        if (defaultMappingRule == null) {
            synchronized (DefaultMappingRule.class) {
                if (defaultMappingRule == null) {
                    defaultMappingRule = new DefaultMappingRule();
                }
            }
        }
        return defaultMappingRule;
    }
}
