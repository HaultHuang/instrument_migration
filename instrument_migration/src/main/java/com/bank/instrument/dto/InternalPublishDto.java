package com.bank.instrument.dto;

import com.bank.instrument.dto.base.BasePublishDto;

public class InternalPublishDto extends BasePublishDto{

	private static final long serialVersionUID = 2686481776046866972L;

	private boolean tradable;

	/**
	 * @return the tradable
	 */
	public boolean isTradable() {
		return tradable;
	}

	/**
	 * @param tradable the tradable to set
	 */
	public void setTradable(boolean tradable) {
		this.tradable = tradable;
	}
}
