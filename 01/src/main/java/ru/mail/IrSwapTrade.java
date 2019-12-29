package ru.mail;

class IrSwapTrade extends BaseTrade {
    public TradeType getTradeType() {
        return TradeType.IR_SWAP;
    }

    IrSwapTrade(int price) {
        this.price = price;
    }
}
