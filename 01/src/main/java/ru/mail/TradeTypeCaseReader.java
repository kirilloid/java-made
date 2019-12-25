package ru.mail;

public class TradeTypeCaseReader implements ITradeTypeReader {
   public TradeType read(String str) {
      switch (str) {
         case "BOND":
            return TradeType.BOND;
         case "COMMODITY_SPOT":
            return TradeType.COMMODITY_SPOT;
         case "FX_SPOT":
            return TradeType.FX_SPOT;
         case "IR_SWAP":
            return TradeType.IR_SWAP;
         default:
            throw new IllegalArgumentException("Incorrect TradeType passed " + str);
      }
   }
}
