package com.bank.instrument.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bank.instrument.dto.ExchangePublishDto;
import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.PublishDto;
import com.bank.instrument.dto.base.BasePublishDto;

public abstract class AbstractMappingRule implements Rule {

	private Map<MappingKeyEnum, MappingKeyEnum> mappingRules = new HashMap<>();

	@Override
	public void publish(BasePublishDto basePublishDto, Collection<InternalPublishDto> internalPublishes) {
		// match existing internal publishes by rules
		InternalPublishDto existInternalPubishDto = matchInternalPublishByRules(basePublishDto, internalPublishes);
		if (basePublishDto instanceof ExchangePublishDto) {
			ExchangePublishDto exchangePublishDto = (ExchangePublishDto) basePublishDto;
			if (existInternalPubishDto != null) {
				existInternalPubishDto.setTradable(exchangePublishDto.isTradable());
			} else {
				InternalPublishDto internalPublishDto = new InternalPublishDto();
				internalPublishDto.setLastTradingDate(exchangePublishDto.getLastTradingDate());
				internalPublishDto.setDeliveryDate(exchangePublishDto.getDeliveryDate());
				internalPublishDto.setMarket(exchangePublishDto.getMarket());
				internalPublishDto.setLabel(exchangePublishDto.getLabel());
				internalPublishDto.setTradable(exchangePublishDto.isTradable());
				internalPublishDto.setPublishCode(exchangePublishDto.getPublishCode());
				internalPublishes.add(internalPublishDto);
			}
		} else if (basePublishDto instanceof PublishDto) {
			PublishDto publishDto = (PublishDto) basePublishDto;
			if (existInternalPubishDto != null) {
				existInternalPubishDto.setDeliveryDate(publishDto.getDeliveryDate());
				existInternalPubishDto.setLastTradingDate(publishDto.getLastTradingDate());
			} else {
				InternalPublishDto internalPublishDto = new InternalPublishDto();
				internalPublishDto.setLastTradingDate(publishDto.getLastTradingDate());
				internalPublishDto.setDeliveryDate(publishDto.getDeliveryDate());
				internalPublishDto.setMarket(publishDto.getMarket());
				internalPublishDto.setLabel(publishDto.getLabel());
				internalPublishDto.setTradable(true);
				internalPublishDto.setPublishCode(publishDto.getPublishCode());
				internalPublishes.add(internalPublishDto);
			}
		}
	}

	@Override
	public InternalPublishDto matchInternalPublishByRules(BasePublishDto basePublishDto,
			Collection<InternalPublishDto> internalPublishes) {
		return internalPublishes.stream().filter(internalPublishDto -> ruleFilter(basePublishDto, internalPublishDto))
				.findFirst().orElse(null);
	}

	/**
	 * filter
	 * 
	 * @param basePublishDto
	 * @param internalPublishDto
	 * @return
	 */
	private boolean ruleFilter(BasePublishDto basePublishDto, InternalPublishDto internalPublishDto) {
		Map<MappingKeyEnum, MappingKeyEnum> rules = getMappingRules();
		boolean allRulesMatched = true;
		for (Entry<MappingKeyEnum, MappingKeyEnum> ruleEntry : rules.entrySet()) {
			String internalKey = ruleEntry.getKey().getKey();
			String externalKey = ruleEntry.getValue().getKey();
			try {
				Class<?> internalClazz = Class.forName(InternalPublishDto.class.getName());
				Method internalMethod = null;
				try {
					internalMethod = internalClazz.getDeclaredMethod(internalKey);
				} catch (Exception e) {
					internalMethod = internalClazz.getSuperclass().getDeclaredMethod(internalKey);
				}
				Object internalMapKey = internalMethod.invoke(internalPublishDto);
				Object externalMapKey = getExternalMappingKey(basePublishDto, externalKey, internalClazz,
						internalMethod);
				if (!(internalMapKey != null && externalMapKey != null && internalMapKey.equals(externalMapKey))) {
					allRulesMatched = false;
					break;
				}
			} catch (Exception e) {
				allRulesMatched = false;
				break;
			}
		}
		return allRulesMatched;
	}

	private String getExternalMappingKey(BasePublishDto basePublishDto, String externalKey, Class<?> internalClazz,
			Method internalMethod)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String externalMappingkey = null;
		if (basePublishDto instanceof ExchangePublishDto) {
			Class<?> externalClazz = Class.forName(ExchangePublishDto.class.getName());
			Method externalMethod = null;
			try {
				externalMethod = externalClazz.getDeclaredMethod(externalKey);
			} catch (Exception e) {
				externalMethod = externalClazz.getSuperclass().getDeclaredMethod(externalKey);
			}
			externalMappingkey = (String) externalMethod.invoke((ExchangePublishDto) basePublishDto);
		} else if (basePublishDto instanceof PublishDto) {
			Class<?> externalClazz = Class.forName(ExchangePublishDto.class.getName());
			Method externalMethod = null;
			try {
				externalMethod = externalClazz.getDeclaredMethod(externalKey);
			} catch (Exception e) {
				externalMethod = externalClazz.getSuperclass().getDeclaredMethod(externalKey);
			}
			externalMappingkey = (String) externalMethod.invoke((ExchangePublishDto) basePublishDto);
		}
		return externalMappingkey;
	}

	@Override
	public void addMappingRule(MappingKeyEnum internalMappingKey, MappingKeyEnum externalMappingKey) {
		this.mappingRules.put(internalMappingKey, externalMappingKey);
	}

	@Override
	public Map<MappingKeyEnum, MappingKeyEnum> getMappingRules() {
		return mappingRules;
	}

	@Override
	public void setMappingRules(Map<MappingKeyEnum, MappingKeyEnum> rules) {
		this.mappingRules = rules;
	}
}
