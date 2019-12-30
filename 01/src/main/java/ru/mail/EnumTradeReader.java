package ru.mail;

public class EnumTradeReader extends BaseTradeReader {
    protected ITrade tradeFactory(String tradeType, int price) {
        return TradeType.valueOf(tradeType).createTrade(price);
    }
}
