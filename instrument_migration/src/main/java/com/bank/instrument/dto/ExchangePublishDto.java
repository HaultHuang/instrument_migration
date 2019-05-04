package com.bank.instrument.dto;

import com.bank.instrument.dto.base.BasePublishDto;

import java.util.Objects;

public class ExchangePublishDto extends BasePublishDto {

    private static final long serialVersionUID = 1637705437291646265L;

    private String exchangeCode;

    public boolean tradable;

    /**
     * @return the exchangeCode
     */
    public String getExchangeCode() {
        return exchangeCode;
    }

    /**
     * @param exchangeCode the exchangeCode to set
     */
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

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

    @Override
    public String toString() {
        return "ExchangePublishDto{" +
                "instrumentCode='" + getInstrumentCode() + '\'' +
                ", publishCode='" + getPublishCode() + '\'' +
                ", lastTradingDate=" + getLastTradingDate() +
                ", deliveryDate=" + getDeliveryDate() +
                ", market='" + getMarket() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", tradable='" + isTradable() + '\'' +
                ", exchangeCode='" + getExchangeCode() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangePublishDto)) return false;
        ExchangePublishDto that = (ExchangePublishDto) o;
        return Objects.equals(getInstrumentCode(), that.getInstrumentCode()) &&
                Objects.equals(getPublishCode(), that.getPublishCode()) &&
                Objects.equals(getLastTradingDate(), that.getLastTradingDate()) &&
                Objects.equals(getDeliveryDate(), that.getDeliveryDate()) &&
                Objects.equals(getMarket(), that.getMarket()) &&
                Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(isTradable(), that.isTradable()) &&
                Objects.equals(getExchangeCode(), that.getExchangeCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstrumentCode(), getPublishCode(), getLastTradingDate(), getDeliveryDate(), getMarket(), getLabel(), isTradable(), getExchangeCode());
    }
}
