package com.bank.instrument.dto.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BasePublishDto implements Serializable {

    private static final long serialVersionUID = 5147696293720330609L;

    private String instrumentCode;

    private String publishCode;

    private LocalDate lastTradingDate;

    private LocalDate deliveryDate;

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
     * @return the publishCode
     */
    public String getPublishCode() {
        return publishCode;
    }

    /**
     * @param publishCode the publishCode to set
     */
    public void setPublishCode(String publishCode) {
        this.publishCode = publishCode;
    }

    /**
     * @return the lastTradingDate
     */
    public LocalDate getLastTradingDate() {
        return lastTradingDate;
    }

    /**
     * @param lastTradingDate the lastTradingDate to set
     */
    public void setLastTradingDate(LocalDate lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
    }

    /**
     * @return the deliveryDate
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
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

    @Override
    public String toString() {
        return "BasePublishDto{" +
                "instrumentCode='" + instrumentCode + '\'' +
                ", publishCode='" + publishCode + '\'' +
                ", lastTradingDate=" + lastTradingDate +
                ", deliveryDate=" + deliveryDate +
                ", market='" + market + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasePublishDto)) return false;
        BasePublishDto that = (BasePublishDto) o;
        return Objects.equals(getInstrumentCode(), that.getInstrumentCode()) &&
                Objects.equals(getPublishCode(), that.getPublishCode()) &&
                Objects.equals(getLastTradingDate(), that.getLastTradingDate()) &&
                Objects.equals(getDeliveryDate(), that.getDeliveryDate()) &&
                Objects.equals(getMarket(), that.getMarket()) &&
                Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstrumentCode(), getPublishCode(), getLastTradingDate(), getDeliveryDate(), getMarket(), getLabel());
    }
}
