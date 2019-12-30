import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import ru.mail.*;

import java.util.Random;

public class ExecutionTest {
    public static Runnable runLoop(final int iterations, final int timePeriteration) {
        return () -> {
            int k = 0;
            for (int i = 0; i < iterations; i++) {
                k += i;
                try {
                    Thread.sleep(timePeriteration);
                } catch (InterruptedException e) {
                    // System.out.println("interrupted task");
                    break;
                }
                if (Math.random() < 0.1) {
                    throw new IllegalArgumentException("It's not your day");
                }
            }
        };
    }

    @Test
    public void randomTest() {
        ExecutionManager em = new ExecutionManagerImpl();
        Random random = new Random();
        int totalCompleted = 0;
        int totalFailed = 0;
        int totalInterrupted = 0;
        for (int i = 0; i < 100; i++) {
            final int taskCount = (int)Math.floor(5. + Math.random() * 10.);
            Runnable[] tasks = new Runnable[taskCount];
            for (int j = 0; j < taskCount; j++) {
                int iterations = (int)Math.floor(5. + Math.random() * 10.);
                tasks[j] = runLoop(iterations, 10);
            }
            Context ctx = em.execute(tasks);
            if (Math.random() < 0.5) {
                ctx.interrupt();
            } else {
                int wait = (int) Math.floor(Math.random() * 25.);
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    // ignore interruptions
                }
                ctx.interrupt();
            }
            ctx.awaitTermination();
            final int completed = ctx.getCompletedTaskCount();
            final int failed = ctx.getFailedTaskCount();
            final int interrupted = ctx.getInterruptedTaskCount();
            totalCompleted += completed;
            totalFailed += failed;
            totalInterrupted += interrupted;
            final int totalTasksHandled = completed + failed + interrupted;
            assertEquals(totalTasksHandled, taskCount);
        }
//        System.out.println(totalCompleted);
//        System.out.println(totalFailed);
//        System.out.println(totalInterrupted);
        assertTrue(totalCompleted != 0);
        assertTrue(totalFailed != 0);
        assertTrue(totalInterrupted != 0);
    }
}


