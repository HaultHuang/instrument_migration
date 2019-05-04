package com.bank.instrument.rule;


public class FlexibleMappingRule extends AbstractMappingRule {

    private static FlexibleMappingRule flexibleMappingRule = null;

    private FlexibleMappingRule() {

    }

    public static FlexibleMappingRule getInstance() {
        if (flexibleMappingRule == null) {
            synchronized (DefaultMappingRule.class) {
                if (flexibleMappingRule == null) {
                    flexibleMappingRule = new FlexibleMappingRule();
                }
            }
        }
        return flexibleMappingRule;
    }
}
