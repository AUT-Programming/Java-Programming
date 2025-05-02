package HelloThread;

public class Challenge {
    static int value = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> value = 1);
        Thread t2 = new Thread(() -> System.out.println("Value: " + value));

        t1.start(); t2.start();
    }
}

