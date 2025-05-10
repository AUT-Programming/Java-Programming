package HelloThread;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 4; i++) {
            int taskId = i;
            executor.submit(() ->
                    System.out.println("Task " + taskId + " by " + Thread.currentThread().getName())
            );
        }

        executor.shutdown();
    }
}
