package ru.mail;

public class FxSpotTrade extends BaseTrade {
    public TradeType getTradeType() {
        return TradeType.FX_SPOT;
    }

    FxSpotTrade(int price) {
        this.price = price;
    }
}
