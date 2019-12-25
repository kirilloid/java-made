package ru.mail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("No file name is provided");
        }
        if (args.length < 2) {
            throw new IllegalArgumentException("No trade type reader provided");
        }
        String fileName = args[0];
        ITradeTypeReader reader = new TradeTypeValueReader();
        try {
            Trade tr = read(fileName, reader);
            if (tr != null) {
                System.out.println(tr.toString());
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }
    }

    public static Trade read(String fileName, ITradeTypeReader ttReader) throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(fileName))
        ) {
            TradeReader tr = new TradeReader();
            return tr.read(reader, ttReader);
        }
    }
}
