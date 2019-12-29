package ru.mail;

public class Trade implements ITrade {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (price != trade.getPrice()) return false;
        return tp == trade.tp;
    }

    @Override
    public int hashCode() {
        int result = tp.hashCode();
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "(" + tp.toString() + ", " + price + ")";
    }
}
