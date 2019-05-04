package com.bank.instrument.rule;

/**
 * Map key by exchangeCode and publishCode
 * @author HuangHao
 *
 */
public class DefaultMappingRule extends AbstractMappingRule{

	public DefaultMappingRule() {
		addMappingRule(MappingKeyEnum.PUBLISH_CODE, MappingKeyEnum.EXCHANGE_CODE);
	}

}
