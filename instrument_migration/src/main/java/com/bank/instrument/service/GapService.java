package com.bank.instrument.service;

import java.util.Collection;

import com.bank.instrument.dto.InternalPublishDto;
import com.bank.instrument.dto.base.BasePublishDto;

public interface GapService {

	void publish(BasePublishDto basePubicDto);

	Collection<InternalPublishDto> listInternalPublishes();
}
