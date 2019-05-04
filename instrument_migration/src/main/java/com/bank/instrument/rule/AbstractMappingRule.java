package com.bank.instrument.rule;

import com.bank.instrument.dto.ExchangePublishDto;
import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.PublishDto;
import com.bank.instrument.dto.base.BasePublishDto;
import com.bank.instrument.rule.enums.MappingKeyEnum;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AbstractMappingRule implements Rule {

    private Map<MappingKeyEnum, MappingKeyEnum> mappingRules = new ConcurrentHashMap<>();

    /**
     * publish external publish into internal
     *
     * @param externalPublishDto
     * @param internalPublishes
     */
    @Override
    public void publishByRules(BasePublishDto externalPublishDto, Collection<InternalPublishDto> internalPublishes) {
        // match existing internal publishes by rules
        List<InternalPublishDto> existInternalPublishDtos = matchInternalPublishByRules(externalPublishDto, internalPublishes);
        // merge external publish dto into internal by rules
        if (!existInternalPublishDtos.isEmpty()) {
            existInternalPublishDtos.forEach(existInternalPublishDto ->
                    mergeToInternalPublishes(externalPublishDto, internalPublishes, existInternalPublishDto)
            );
        } else {
            mergeToInternalPublishes(externalPublishDto, internalPublishes, null);
        }
    }

    /**
     * Merge external publish into internal
     *
     * @param externalPublishDto
     * @param internalPublishes
     * @param existInternalPublishDto
     */
    private void mergeToInternalPublishes(BasePublishDto externalPublishDto, Collection<InternalPublishDto> internalPublishes, InternalPublishDto existInternalPublishDto) {
        if (externalPublishDto instanceof ExchangePublishDto) {
            ExchangePublishDto exchangePublishDto = (ExchangePublishDto) externalPublishDto;
            if (existInternalPublishDto != null) {
                existInternalPublishDto.setTradable(exchangePublishDto.isTradable());
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
        } else if (externalPublishDto instanceof PublishDto) {
            PublishDto publishDto = (PublishDto) externalPublishDto;
            if (existInternalPublishDto != null) {
                existInternalPublishDto.setDeliveryDate(publishDto.getDeliveryDate());
                existInternalPublishDto.setLastTradingDate(publishDto.getLastTradingDate());
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

    /**
     * Match all internal publishes that meet with the rules
     *
     * @param externalPublishDto
     * @param internalPublishes
     * @return the list of internal publishes
     */
    @Override
    public List<InternalPublishDto> matchInternalPublishByRules(BasePublishDto externalPublishDto,
                                                                Collection<InternalPublishDto> internalPublishes) {
        return internalPublishes.stream().filter(internalPublishDto -> ruleFilter(externalPublishDto, internalPublishDto))
                .collect(Collectors.toList());
    }

    /**
     * filter internal publishes by mapping rule
     *
     * @param externalPublishDto the external publish dto
     * @param internalPublishDto the internal publish dto
     * @return if the basePublishDto match any internal publishes
     */
    private boolean ruleFilter(BasePublishDto externalPublishDto, InternalPublishDto internalPublishDto) {
        Map<MappingKeyEnum, MappingKeyEnum> rules = getMappingRules();
        boolean allRulesMatched = true;
        for (Entry<MappingKeyEnum, MappingKeyEnum> ruleEntry : rules.entrySet()) {
            String internalKey = ruleEntry.getKey().getKey();
            String externalKey = ruleEntry.getValue().getKey();
            try {
                Class<?> internalClazz = Class.forName(InternalPublishDto.class.getName());
                Method internalMethod;
                try {
                    internalMethod = internalClazz.getDeclaredMethod(internalKey);
                } catch (Exception e) {
                    internalMethod = internalClazz.getSuperclass().getDeclaredMethod(internalKey);
                }
                Object internalMapKey = internalMethod.invoke(internalPublishDto);
                Object externalMapKey = getExternalMappingKey(externalPublishDto, externalKey);
                // if there is a mapping rule for exchange publishByRules
                // then it doesn't fit for normal publishByRules(for example: exchangeCode)
                // so the externalMappingKey might not be found
                // in that way, mark externalMappingKey as null
                // and ignore the mapping rule
                if (externalMapKey == null) {
                    continue;
                }
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

    /**
     * Get external publish's mapping key by rule's key
     *
     * @param basePublishDto
     * @param externalKey
     * @return the mapping key's value
     */
    private String getExternalMappingKey(BasePublishDto basePublishDto, String externalKey) {
        String externalMappingKey = null;
        try {
            if (basePublishDto instanceof ExchangePublishDto) {
                Class<?> externalClazz = Class.forName(ExchangePublishDto.class.getName());
                Method externalMethod;
                try {
                    externalMethod = externalClazz.getDeclaredMethod(externalKey);
                } catch (Exception e) {
                    externalMethod = externalClazz.getSuperclass().getDeclaredMethod(externalKey);
                }
                externalMappingKey = (String) externalMethod.invoke(basePublishDto);
            } else if (basePublishDto instanceof PublishDto) {
                Class<?> externalClazz = Class.forName(ExchangePublishDto.class.getName());
                Method externalMethod = null;
                try {
                    externalMethod = externalClazz.getDeclaredMethod(externalKey);
                } catch (Exception e) {
                    externalMethod = externalClazz.getSuperclass().getDeclaredMethod(externalKey);
                }
                externalMappingKey = (String) externalMethod.invoke(basePublishDto);
            }
        } catch (Exception e) {
        }
        return externalMappingKey;
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
    public void setMappingRules(Map<MappingKeyEnum, MappingKeyEnum> mappingRules) {
        this.mappingRules = mappingRules;
    }
}
