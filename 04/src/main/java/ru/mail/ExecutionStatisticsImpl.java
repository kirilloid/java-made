package ru.mail;

import java.util.List;
import java.util.ArrayList;

public class ExecutionStatisticsImpl implements ExecutionStatistics {
    private final List<Integer> times = new ArrayList<>();

    public synchronized void addTime(final int time) {
        times.add(time);
    }

    public synchronized int getMinExecutionTimeInMs() {
        return times.stream().min(Integer::compare).get();
    }

    public synchronized int getMaxExecutionTimeInMs() {
        return times.stream().max(Integer::compare).get();
    }

    public synchronized int getAverageExecutionTimeInMs() {
        final int size = times.size();
        if (size == 0) return 0;
        final int total = times.stream().reduce(0, Integer::sum);
        return total / size;
    }
}
