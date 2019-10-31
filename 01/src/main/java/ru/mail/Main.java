package ru.mail;

import ru.mail.TradeReader;
import ru.mail.TradeType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) {
        assert(args.length > 0);
        String fileName = args[0];
        Trade tr = read(fileName);
        if (tr != null) {
            System.out.println(tr.toString());
        }
    }

    public static Trade read(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            TradeReader tr = new TradeReader();
            return tr.read(reader);
        } catch (Exception e) {
            return null;
        }
    }
}
