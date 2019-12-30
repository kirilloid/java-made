package ru.mail;

public enum TradeType {
    BOND {
        ITrade createTrade(int price) {
            return new BondTrade(price);
        }
    },
    COMMODITY_SPOT {
        ITrade createTrade(int price) {
            return new CommoditySpotTrade(price);
        }
    },
    FX_SPOT {
        ITrade createTrade(int price) {
            return new FxSpotTrade(price);
        }
    },
    IR_SWAP {
        ITrade createTrade(int price) {
            return new IrSwapTrade(price);
        }
    };

    abstract ITrade createTrade(int price);
}

