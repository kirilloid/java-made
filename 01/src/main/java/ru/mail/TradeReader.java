package ru.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeReader {
    private Pattern typeRe;
    private Pattern priceRe;

    public TradeReader() {
        typeRe = Pattern.compile("\"type\": (\\w+),");
        priceRe = Pattern.compile("\"price\": (\\d+)");
    }

    public Trade read(BufferedReader reader, ITradeTypeReader ttReader) throws IllegalStateException {
        try {
            readFirstLine(reader);
            TradeType tp = readTradeType(reader, ttReader);
            int price = readPrice(reader);
            readLastLine(reader);

            return new Trade(tp, price);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void readFirstLine(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (!"Trade: {".equals(str)) {
            throw new IllegalStateException("First line of file is incorrect");
        }
    }

    private TradeType readTradeType(BufferedReader reader, ITradeTypeReader ttReader) throws IOException {
        String str = reader.readLine();
        Matcher matcher = typeRe.matcher(str);
        if (!matcher.find()) {
            throw new IllegalStateException("cannot read TradeType");
        }
        String tpStr = matcher.group(1);
        return ttReader.read(tpStr);
    }

    public int readPrice(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        Matcher matcher = priceRe.matcher(str);
        if (!matcher.find()) {
            throw new IllegalStateException("cannot read price");
        }
        String tpStr = matcher.group(1);
        return Integer.parseInt(tpStr);
    }

    private void readLastLine(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (!"}".equals(str)) {
            throw new IllegalStateException("Last line of file is incorrect");
        }
    }
}
