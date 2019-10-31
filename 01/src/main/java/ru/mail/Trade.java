package ru.mail;

public class Trade {
    private final TradeType tp;
    private final int price;
    public Trade(TradeType tp, int price) {
        this.tp = tp;
        this.price = price;
    }

    public TradeType getTradeType() {
        return tp;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "(" + tp.toString() + ", " + Integer.toString(price) + ")";
    }
}
