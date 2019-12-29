package ru.mail;

public class BondTrade extends BaseTrade {
    public TradeType getTradeType() {
        return TradeType.BOND;
    }

    BondTrade(int price) {
        this.price = price;
    }
}
