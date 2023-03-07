import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Assistant implements Runnable{

    private String threadName;
    private CountDownLatch countDownLatch;
    private Semaphore semaphore;

    public Assistant(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        threadName = "Assistant thread";
        semaphore = new Semaphore(1);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        System.out.println("Assistant initialised.");
        countDownLatch.countDown();
    }

    public int createScore(){
        return (new Random()).nextInt(5, 11);
    }

    public String getThreadName() {
        return threadName;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
