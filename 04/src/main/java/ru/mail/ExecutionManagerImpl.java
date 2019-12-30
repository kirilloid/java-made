package ru.mail;

public class ExecutionManagerImpl implements ExecutionManager {
    public Context execute(Runnable... tasks) {
        return new ContextImpl(tasks);
    }
}
