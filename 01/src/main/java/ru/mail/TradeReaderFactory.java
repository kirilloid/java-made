package ru.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeReaderFactory extends TradeReader {
    @Override()
    protected ITrade tradeCreator(TradeType tp, int price) {
        return tp.createTrade(price);
    }
}
