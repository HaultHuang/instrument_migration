package com.bank.instrument.dto;

import com.bank.instrument.dto.base.BasePublishDto;

import java.util.Objects;

public class InternalPublishDto extends BasePublishDto {

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

    @Override
    public String toString() {
        return "InternalPublishDto{" +
                "instrumentCode='" + getInstrumentCode() + '\'' +
                ", publishCode='" + getPublishCode() + '\'' +
                ", lastTradingDate=" + getLastTradingDate() +
                ", deliveryDate=" + getDeliveryDate() +
                ", market='" + getMarket() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", tradable='" + isTradable() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalPublishDto)) return false;
        InternalPublishDto that = (InternalPublishDto) o;
        return Objects.equals(getInstrumentCode(), that.getInstrumentCode()) &&
                Objects.equals(getPublishCode(), that.getPublishCode()) &&
                Objects.equals(getLastTradingDate(), that.getLastTradingDate()) &&
                Objects.equals(getDeliveryDate(), that.getDeliveryDate()) &&
                Objects.equals(getMarket(), that.getMarket()) &&
                Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(isTradable(), that.isTradable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstrumentCode(), getPublishCode(), getLastTradingDate(), getDeliveryDate(), getMarket(), getLabel(), isTradable());
    }
}
