package ru.mail;

public interface ExecutionStatistics {
    int getMinExecutionTimeInMs();
    int getMaxExecutionTimeInMs();
    int getAverageExecutionTimeInMs();
}
