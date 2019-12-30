package ru.mail;

import java.io.BufferedReader;

public interface ITradeReader {
    public ITrade read(BufferedReader reader) throws IllegalStateException;
}
