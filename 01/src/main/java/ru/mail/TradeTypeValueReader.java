package ru.mail;

public class TradeTypeValueReader implements ITradeTypeReader {
   public TradeType read(String str) {
      return TradeType.valueOf(str);
   }
}