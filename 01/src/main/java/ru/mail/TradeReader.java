package ru.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeReader {
    private BufferedReader reader;
    private Pattern typeRe;
    private Pattern priceRe;

    public TradeReader() {
        typeRe = Pattern.compile("\"type\": (\\w+),");
        priceRe = Pattern.compile("\"price\": (\\d+)");
    }

    public Trade read(BufferedReader reader) throws IllegalStateException {
        this.reader = reader;
        TradeType tp;
        int price;

        try {
            readFirstLine();
            tp = readTradeType();
            price = readPrice();
            readLastLine();

            Trade trade = new Trade(tp, price);
            return trade;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected void readFirstLine() throws IOException {
        String s = reader.readLine();
        assert("Trade: {".equals(s));
    }

    protected TradeType readTradeType() throws IOException {
        String str = reader.readLine();
        Matcher matcher = typeRe.matcher(str);
        if (!matcher.find()) {
            throw new IllegalStateException("cannot read TradeType");
        }
        String tpStr = matcher.group(1);
        TradeType tp = TradeType.valueOf(tpStr);
        return tp;
    }

    protected int readPrice() throws IOException {
        String str = reader.readLine();
        Matcher matcher = priceRe.matcher(str);
        if (!matcher.find()) {
            throw new IllegalStateException("cannot read price");
        }
        String tpStr = matcher.group(1);
        int price = Integer.parseInt(tpStr);
        return price;
    }

    protected void readLastLine() throws IOException {
        String str = reader.readLine();
        assert("}".equals(str));
    }
}
