package ru.mail;

public enum TradeType {
    BOND,
    COMMODITY_SPOT,
    FX_SPOT,
    IR_SWAP;

    ITrade createTrade(int price) {
        return new Trade(this, price);
    }
}

