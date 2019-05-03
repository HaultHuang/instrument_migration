package com.bank.instrument.dto.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Base publish dto
 * 
 * @author HuangHao
 *
 */
public class BasePublishDto implements Serializable{

	private static final long serialVersionUID = 5147696293720330609L;

	private String instrumentCode;

	private String publicCode;
	
	private Date lastTradingDate;
	
	private Date deliveryDate;
	
	private String market;
	
	private String label;

	/**
	 * @return the instrumentCode
	 */
	public String getInstrumentCode() {
		return instrumentCode;
	}

	/**
	 * @param instrumentCode the instrumentCode to set
	 */
	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}

	/**
	 * @return the publicCode
	 */
	public String getPublicCode() {
		return publicCode;
	}

	/**
	 * @param publicCode the publicCode to set
	 */
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}

	/**
	 * @return the lastTradingDate
	 */
	public Date getLastTradingDate() {
		return lastTradingDate;
	}

	/**
	 * @param lastTradingDate the lastTradingDate to set
	 */
	public void setLastTradingDate(Date lastTradingDate) {
		this.lastTradingDate = lastTradingDate;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @param market the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
