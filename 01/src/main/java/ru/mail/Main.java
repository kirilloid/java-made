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
        try {
            ITrade tr = read(fileName);
            if (tr != null) {
                System.out.println(tr.toString());
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }
    }

    public static ITrade read(String fileName) throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(fileName))
        ) {
            ITradeReader tr = new EnumTradeReader();
            return tr.read(reader);
        }
    }
}
