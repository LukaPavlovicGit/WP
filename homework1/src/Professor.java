import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Professor implements Runnable{

    private String threadName;
    private CountDownLatch countDownLatch;
    private CyclicBarrier cyclicBarrier;
    private Semaphore semaphore;

    public Professor(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
        threadName = "Professor";
        cyclicBarrier = new CyclicBarrier(2);
        semaphore = new Semaphore(2, true);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        System.out.println("Professor is initialized");
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

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
