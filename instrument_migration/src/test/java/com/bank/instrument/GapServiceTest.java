package com.bank.instrument;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import com.bank.instrument.dto.ExchangePublishDto;
import com.bank.instrument.dto.PublishDto;
import com.bank.instrument.service.GapService;
import com.bank.instrument.service.impl.GapServiceIml;

public class GapServiceTest {

	@Test
	public void testPulishWithThread() throws InterruptedException {

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
					System.out.println("P Time:" + i + ",cost:" + System.currentTimeMillis());
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
					System.out.println("Time:" + i + ",cost:" + System.currentTimeMillis());
					gapService.publish(exchangePublishDto);
				}
			}
		};

		Thread t3 = new Thread() {
			@Override
			public void run() {
				for (int i = 1; i < 10; i++) {
					System.out.println("i" + i);
					gapService.listInternalPublishes();
					System.out.println("Publish:" + i + ",cost:" + System.currentTimeMillis());
					System.out.println("i" + i);
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

	@Test
	public void testPulish() throws InterruptedException {

		GapService gapService = new GapServiceIml();
		PublishDto publishDto = new PublishDto();
		publishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 1));
		publishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 2));
		publishDto.setPublishCode("PB_03_2018_");
		publishDto.setInstrumentCode("PRIME");
		publishDto.setLabel("Lead 13 March 2018");
		publishDto.setMarket("PB");
		gapService.publish(publishDto);

		ExchangePublishDto exchangePublishDto = new ExchangePublishDto();
		exchangePublishDto.setLastTradingDate(LocalDate.of(2019, Month.JANUARY, 15));
		exchangePublishDto.setDeliveryDate(LocalDate.of(2019, Month.JANUARY, 14));
		exchangePublishDto.setPublishCode("PB_03_2018");
		exchangePublishDto.setExchangeCode("PB_03_2018_");
		exchangePublishDto.setInstrumentCode("PRIME");
		exchangePublishDto.setLabel("Lead 13 March 2018");
		exchangePublishDto.setMarket("PB");
		exchangePublishDto.setTradable(false);
		gapService.publish(exchangePublishDto);
		gapService.listInternalPublishes();
	}
}
