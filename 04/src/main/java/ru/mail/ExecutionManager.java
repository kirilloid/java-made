package ru.mail;

public interface ExecutionManager {
    Context execute(Runnable... tasks);
}

