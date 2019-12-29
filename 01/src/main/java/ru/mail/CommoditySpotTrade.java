package ru.mail;

public class CommoditySpotTrade extends BaseTrade {
    public TradeType getTradeType() {
        return TradeType.COMMODITY_SPOT;
    }

    CommoditySpotTrade(int price) {
        this.price = price;
    }
}
