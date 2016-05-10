public class SemaphoreImpl implements Semaphore {
    private volatile int permits;
    final Object lock = new Object();

    public SemaphoreImpl(int permits) {
        this.permits = permits;
    }

    @Override
    public void acquire() {
        acquire(1);
    }

    @Override
    public void acquire(int permits) {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " try to acquire " + permits + " permits");
            System.out.println("Available permits: " + this.getAvailablePermits());
            if (this.permits >= permits) {
                this.permits -= permits;
                System.out.println(Thread.currentThread().getName() + " successfully acquired " + permits + " permits; "
                        + this.getAvailablePermits() + " left");
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    lock.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void release() {
        release(1);
    }


    @Override
    public void release(int permits) {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName()+" Released!");
            lock.notifyAll();
            this.permits += permits;
            System.out.println("Available permits after release: " + this.getAvailablePermits());
        }
    }

    @Override
    public int getAvailablePermits() {
        synchronized (lock) {
            return this.permits;
        }
    }
}
