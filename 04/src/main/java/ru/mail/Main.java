package ru.mail;

class Main {
    public static Runnable runLoop(final int timeInMs) {
        return () -> {
            int k = 0;
            final int iterations = timeInMs / 10;
            for (int i = 0; i < iterations; i++) {
                k += i;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("interrupted task");
                    break;
                }
            }
        };
    }

    public static Runnable failAfter(final int timeInMs) {
        return () -> {
            int k = 0;
            final int iterations = timeInMs / 10;
            for (int i = 0; i < iterations; i++) {
                k += i;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("interrupted task");
                    break;
                }
            }
            throw new IllegalStateException();
        };
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable[] tasks = new Runnable[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = i % 2 == 0 ? failAfter(i * 100) : runLoop(i * 100);
        }
        ExecutionManager em = new ExecutionManagerImpl();
        final Context ctx = em.execute(tasks);
        ctx.onFinish(() -> {
            ExecutionStatistics stats = ctx.getStatistics();
            System.out.println("min time = " + stats.getMinExecutionTimeInMs());
            System.out.println("max time = " + stats.getMaxExecutionTimeInMs());
            System.out.println("avg time = " + stats.getAverageExecutionTimeInMs());
            System.out.println("completed = " + ctx.getCompletedTaskCount());
            System.out.println("failed = " + ctx.getFailedTaskCount());
            System.out.println("interrupted = " + ctx.getInterruptedTaskCount());
        });
        try {
            Thread.sleep(200);
            ctx.interrupt();
        } catch (InterruptedException e) {
            return;
        }
    }
}
