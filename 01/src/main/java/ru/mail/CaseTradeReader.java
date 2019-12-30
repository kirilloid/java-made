package ru.mail;

public class CaseTradeReader extends BaseTradeReader {
   protected ITrade tradeFactory(String tradeType, int price) {
      TradeType tt = readTradeType(tradeType);
      return new Trade(tt, price);
   }
   
   private TradeType readTradeType(String str) {
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
