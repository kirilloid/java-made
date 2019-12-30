package ru.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseTradeReader implements ITradeReader {
    private final Pattern typeRe;
    private final Pattern priceRe;

    public BaseTradeReader() {
        typeRe = Pattern.compile("\"type\": (\\w+),");
        priceRe = Pattern.compile("\"price\": (\\d+)");
    }

    abstract protected ITrade tradeFactory(String tradeType, int price);

    public ITrade read(BufferedReader reader) {
        try {
            readFirstLine(reader);
            String tradeType = readTradeType(reader);
            int price = readPrice(reader);
            readLastLine(reader);

            return tradeFactory(tradeType, price);
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

    private String readTradeType(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        Matcher matcher = typeRe.matcher(str);
        if (!matcher.find()) {
            throw new IllegalStateException("cannot read TradeType");
        }
        return matcher.group(1);
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
