import java.util.Random;


public class SemaphoreTest {


    public SemaphoreTest(int permits) {
        this.semaphore = new SemaphoreImpl(permits);
    }

    SemaphoreImpl semaphore;

    public static void main(String[] args) {
        SemaphoreTest semaphoreTest = new SemaphoreTest(10);
        semaphoreTest.test();

    }

    public class Worker implements Runnable {
        @Override
        public void run() {

            System.out.println(Thread.currentThread().getName() + " started");
            semaphore.acquire(new Random().nextInt(10));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        }
    }

    public void test() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker()).start();
        }
    }
}
