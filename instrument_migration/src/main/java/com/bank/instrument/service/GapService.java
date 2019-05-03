package com.bank.instrument.service;

import com.bank.instrument.dto.base.BasePublishDto;

public interface GapService {

	void publish(BasePublishDto basePubicDto);
	
	void listInternalPublishes();
}
