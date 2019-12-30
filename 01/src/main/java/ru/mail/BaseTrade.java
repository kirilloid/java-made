package ru.mail;

    public abstract class BaseTrade implements ITrade {
    protected int price;

    public abstract TradeType getTradeType();

    public int getPrice() {
        return price;
    }
}

