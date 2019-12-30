package ru.mail;

public class ContextImpl implements Context {
    private final Thread.UncaughtExceptionHandler handler =
            new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread myThread, Throwable e) {
                    ContextImpl.this.addFailed();
                }
            };

    class MyThread extends Thread {
        private final Runnable task;

        MyThread(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            if (interrupted()) {
                ContextImpl.this.addInterrupted();
                return;
            }
            long startTime = System.nanoTime();
            task.run();
            long endTime = System.nanoTime();
            int delta = (int)((endTime - startTime) / 1_000_000);
            ContextImpl.this.addFinished(delta);
        }
    }

    private final Object barrier = new Object();
    private final ExecutionStatisticsImpl stats = new ExecutionStatisticsImpl();
    private final MyThread[] threads;
    private final int total;
    private volatile int completed;
    private volatile int interrupted;
    private volatile int failed;

    ContextImpl(Runnable... tasks) {
        total = tasks.length;
        threads = new MyThread[total];
        for (int i = 0; i < total; i++) {
            threads[i] = new MyThread(tasks[i]);
            threads[i].setUncaughtExceptionHandler(handler);
        }
        (new Thread() {
            public void run() {
                for (int i = 0; i < ContextImpl.this.total; i++) {
                    ContextImpl.this.threads[i].start();
                }
            }
        }).start();
    }

    public void addFinished(int time) {
        synchronized(barrier) {
            completed++;
            stats.addTime(time);
            barrier.notifyAll();
        }
    }

    public void addInterrupted() {
        synchronized(barrier) {
            interrupted++;
            barrier.notifyAll();
        }
    }

    public void addFailed() {
        synchronized(barrier) {
            failed++;
            barrier.notifyAll();
        }
    }

    public int getCompletedTaskCount() {
        int value;
        synchronized(barrier) {
            value = completed;
        }
        return value;
    }

    public int getFailedTaskCount() {
        int value;
        synchronized(barrier) {
            value = failed;
        }
        return value;
    }

    public int getInterruptedTaskCount() {
        int value;
        synchronized(barrier) {
            value = interrupted;
        }
        return value;
    }

    public void interrupt() {
        for (int i = 0; i < total; i++) {
            threads[i].interrupt();
        }
    }

    public boolean isFinished() {
        boolean value;
        synchronized(barrier) {
            value = (completed + failed + interrupted == total);
        }
        return value;
    }

    public void onFinish(Runnable callback) {
        Thread thread = new Thread(() -> {
            ContextImpl.this.awaitTermination();
            callback.run();
        });
        thread.start();
    }

    public ExecutionStatistics getStatistics() {
        return stats;
    }

    public void awaitTermination() {
        synchronized(barrier) {
            while (!isFinished()) {
                try {
                    barrier.wait();
                } catch (InterruptedException e) {}
            }
        }
    }
}