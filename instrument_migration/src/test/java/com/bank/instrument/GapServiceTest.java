package com.bank.instrument;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.instrument.dto.InternalPublishDto;
import org.junit.Assert;
import org.junit.Test;

import com.bank.instrument.dto.ExchangePublishDto;
import com.bank.instrument.dto.PublishDto;
import com.bank.instrument.rule.MappingKeyEnum;
import com.bank.instrument.service.GapService;
import com.bank.instrument.service.impl.GapServiceIml;

public class GapServiceTest {

    /**
     * Test case for publish by default rule:
     * exchangeCode->publishCode
     */
    @Test
    public void testPublishWithDefaultRule() {
        InternalPublishDto expectResult = new InternalPublishDto();
        expectResult.setTradable(false);
        expectResult.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        expectResult.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        expectResult.setLabel("Lead 13 March 2018");
        expectResult.setMarket("PB");
        expectResult.setPublishCode("PB_03_2018");

        GapService gapService = new GapServiceIml();
        PublishDto publishDto = new PublishDto();
        publishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        publishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        publishDto.setPublishCode("PB_03_2018");
        publishDto.setInstrumentCode("PRIME");
        publishDto.setLabel("Lead 13 March 2018");
        publishDto.setMarket("PB");
        gapService.publish(publishDto);
        ExchangePublishDto exchangePublishDto = new ExchangePublishDto();
        exchangePublishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 15));
        exchangePublishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 14));
        exchangePublishDto.setPublishCode("PB_03_2018");
        exchangePublishDto.setExchangeCode("PB_03_2018");
        exchangePublishDto.setInstrumentCode("PRIME");
        exchangePublishDto.setLabel("Lead 13 March 2018");
        exchangePublishDto.setMarket("PB");
        exchangePublishDto.setTradable(false);
        gapService.publish(exchangePublishDto);
        Collection<InternalPublishDto> result = gapService.listInternalPublishes();
        Assert.assertTrue(result.size() == 1);
        Assert.assertEquals(result.iterator().next(), expectResult);
    }

    /**
     * Test case for after publishing only matches one internal publish
     * and keep updating it
     * According to
     * Default rule ：exchangeCode->publishCode
     * ->
     * Multiple Flexible rule:(wired rules)
     * exchangeCode->Market
     * publicCode->Label
     */
    @Test
    public void testPublishWithFlexibleRule() {
        InternalPublishDto expectResult = new InternalPublishDto();
        expectResult.setTradable(true);
        expectResult.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        expectResult.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        expectResult.setLabel("Lead 13 March 2018");
        expectResult.setMarket("PB");
        expectResult.setPublishCode("PB_03_2018");

        GapService gapService = new GapServiceIml();
        PublishDto publishDto = new PublishDto();
        publishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        publishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        publishDto.setPublishCode("PB_03_2018");
        publishDto.setInstrumentCode("LME");
        publishDto.setLabel("Lead 13 March 2018");
        publishDto.setMarket("PB");
        gapService.publish(publishDto);
        Collection<InternalPublishDto> result = gapService.listInternalPublishes();
        Assert.assertTrue(result.size() == 1);
        Assert.assertEquals(result.iterator().next(), expectResult);

        ExchangePublishDto exchangePublishDto = new ExchangePublishDto();
        exchangePublishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 15));
        exchangePublishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 14));
        exchangePublishDto.setPublishCode("PB_03_2018");
        exchangePublishDto.setExchangeCode("PB_03_2018");
        exchangePublishDto.setInstrumentCode("PRIME");
        exchangePublishDto.setLabel("Lead 13 March 2018");
        exchangePublishDto.setMarket("PB");
        exchangePublishDto.setTradable(false);
        gapService.publish(exchangePublishDto);
        result = gapService.listInternalPublishes();
        // the PRIME's exchangeCode matches LME's publishCode
        // after merging, there should only be one internal publish
        expectResult.setTradable(false);
        Assert.assertTrue(result.size() == 1);
        Assert.assertTrue(result.iterator().next().equals(expectResult));
        // config new flexible rule for key mapping
        Map<MappingKeyEnum, MappingKeyEnum> rules = new HashMap<MappingKeyEnum, MappingKeyEnum>();
        rules.put(MappingKeyEnum.MARKET, MappingKeyEnum.EXCHANGE_CODE);
        rules.put(MappingKeyEnum.LABEL, MappingKeyEnum.PUBLISH_CODE);
        gapService.setFlexibleRules(rules);

        ExchangePublishDto exchangePublishDto2 = new ExchangePublishDto();
        exchangePublishDto2.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 29));
        exchangePublishDto2.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 28));
        exchangePublishDto2.setPublishCode("Lead 13 March 2018");
        exchangePublishDto2.setExchangeCode("PB");
        exchangePublishDto2.setInstrumentCode("PRIME");
        exchangePublishDto2.setLabel("Lead 14 March 2018");
        exchangePublishDto2.setMarket("PB");
        exchangePublishDto2.setTradable(true);
        gapService.publish(exchangePublishDto2);
        gapService.listInternalPublishes();
        expectResult.setTradable(true);
        Assert.assertTrue(result.size() == 1);
        Assert.assertTrue(result.iterator().next().equals(expectResult));

        PublishDto publishDto2 = new PublishDto();
        publishDto2.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 11));
        publishDto2.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 22));
        publishDto2.setPublishCode("Lead 13 March 2018");
        publishDto2.setInstrumentCode("LME");
        publishDto2.setLabel("Label normal Publish");
        publishDto2.setMarket("PB");
        gapService.publish(publishDto2);
        result = gapService.listInternalPublishes();
        expectResult.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 11));
        expectResult.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 22));
        Assert.assertTrue(result.size() == 1);
        Assert.assertTrue(result.iterator().next().equals(expectResult));
    }


    /**
     * Test case for after publishing, there match several existing internal publishes
     * according to
     * default rule : exchangeCode->publishCode
     * ->
     * flexible rule: market->market
     */
    @Test
    public void testPublishWithFlexibleRuleWithSeveralMatched() {
        InternalPublishDto expectResult = new InternalPublishDto();
        expectResult.setTradable(true);
        expectResult.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        expectResult.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        expectResult.setLabel("Lead 13 March 2019");
        expectResult.setMarket("PB");
        expectResult.setPublishCode("PB_03_2019");

        GapService gapService = new GapServiceIml();
        PublishDto publishDto = new PublishDto();
        publishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
        publishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
        publishDto.setPublishCode("PB_03_2019");
        publishDto.setInstrumentCode("LME");
        publishDto.setLabel("Lead 13 March 2019");
        publishDto.setMarket("PB");
        gapService.publish(publishDto);
        Collection<InternalPublishDto> result = gapService.listInternalPublishes();
        Assert.assertTrue(result.size() == 1);
        Assert.assertTrue(result.iterator().next().equals(expectResult));

        ExchangePublishDto exchangePublishDto = new ExchangePublishDto();
        exchangePublishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 15));
        exchangePublishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 14));
        exchangePublishDto.setPublishCode("PB_04_2019");
        exchangePublishDto.setExchangeCode("PB_04_2019");
        exchangePublishDto.setInstrumentCode("PRIME");
        exchangePublishDto.setLabel("Lead 13 April 2019");
        exchangePublishDto.setMarket("PB");
        exchangePublishDto.setTradable(true);
        gapService.publish(exchangePublishDto);
        result = gapService.listInternalPublishes();
        // the PRIME's exchangeCode matches LME's publishCode
        // after merging, there should only be one internal publish
        Assert.assertTrue(result.size() == 2);
        // config new flexible rule for key mapping
        Map<MappingKeyEnum, MappingKeyEnum> rules = new HashMap<MappingKeyEnum, MappingKeyEnum>();
        rules.put(MappingKeyEnum.MARKET, MappingKeyEnum.MARKET);
        gapService.setFlexibleRules(rules);

        ExchangePublishDto exchangePublishDto2 = new ExchangePublishDto();
        exchangePublishDto2.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 29));
        exchangePublishDto2.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 28));
        exchangePublishDto2.setPublishCode("Lead 13 March 2019");
        exchangePublishDto2.setExchangeCode("PB");
        exchangePublishDto2.setInstrumentCode("PRIME");
        exchangePublishDto2.setLabel("Lead 14 March 2019");
        exchangePublishDto2.setMarket("PB");
        exchangePublishDto2.setTradable(false);
        gapService.publish(exchangePublishDto2);
        gapService.listInternalPublishes();
        Assert.assertTrue(result.size() == 2);
        result.forEach(r -> Assert.assertFalse(r.isTradable()));
        PublishDto publishDto2 = new PublishDto();
        publishDto2.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 11));
        publishDto2.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 22));
        publishDto2.setPublishCode("Lead 13 March 2019");
        publishDto2.setInstrumentCode("LME");
        publishDto2.setLabel("Label normal Publish");
        publishDto2.setMarket("PB");
        gapService.publish(publishDto2);
        result = gapService.listInternalPublishes();
        Assert.assertTrue(result.size() == 2);
        result.forEach(r -> {
            Assert.assertEquals(r.getLastTradingDate(), LocalDate.of(2019, Month.JANUARY, 11));
            Assert.assertEquals(r.getDeliveryDate(), LocalDate.of(2019, Month.JANUARY, 22));
            Assert.assertFalse(r.isTradable());
        });
    }

    /**
     * Test case by publish with multi thread
     *
     * @throws InterruptedException
     */
    @Test
    public void testPublishWithThread() throws InterruptedException {

        GapService gapService = new GapServiceIml();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i < 3; i++) {
                    PublishDto publishDto = new PublishDto();
                    publishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, i));
                    publishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, i + 1));
                    publishDto.setPublishCode("PB_03_2018_" + i);
                    publishDto.setInstrumentCode("PRIME");
                    publishDto.setLabel("Lead 13 March 2018");
                    publishDto.setMarket("PB");
                    gapService.publish(publishDto);
                }
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i < 3; i++) {
                    ExchangePublishDto exchangePublishDto = new ExchangePublishDto();
                    exchangePublishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, i + 15));
                    exchangePublishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, i + 14));
                    exchangePublishDto.setPublishCode("PB_03_2018");
                    exchangePublishDto.setExchangeCode("PB_03_2018_" + i);
                    exchangePublishDto.setInstrumentCode("PRIME");
                    exchangePublishDto.setLabel("Lead 13 March 2018");
                    exchangePublishDto.setMarket("PB");
                    exchangePublishDto.setTradable(false);
                    gapService.publish(exchangePublishDto);
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i < 10; i++) {
                    gapService.listInternalPublishes();
                }
            }
        };
        t3.start();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        t3.join();
        gapService.listInternalPublishes();
    }
}
