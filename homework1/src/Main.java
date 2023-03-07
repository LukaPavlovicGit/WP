import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger sumOfScores = new AtomicInteger();
    public static AtomicInteger numberOfStudents = new AtomicInteger();
    public static AtomicBoolean isFinished = new AtomicBoolean(false);
    public static long initTime;

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Please enter number of students: ");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        CountDownLatch examiners = new CountDownLatch(2);
        Professor professor = new Professor(examiners);
        Assistant assistant = new Assistant(examiners);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(professor);
        executorService.execute(assistant);

        examiners.await();
        System.out.println("Defend starts...");

        initTime = System.currentTimeMillis();
        ScheduledExecutorService studentService = Executors.newScheduledThreadPool(N);

        for (int i = 0; i < N; i++) {
            long defending = (new Random()).nextInt(500) + 500; // koliko dugo brani
            long timeOfDefending = (new Random()).nextInt(1000); // kad pocinje sa odbranom
            String studentName = "Student " + (i + 1);
            Student student = new Student(studentName, assistant, professor, defending);
            studentService.schedule(student, timeOfDefending, TimeUnit.MILLISECONDS);
        }

        // Odbrana traje 5s
        Thread.sleep(5000);

        isFinished.set(true);
        studentService.shutdownNow();
        executorService.shutdownNow();

        System.out.println("Defend ends...");

        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Total number of students: " + numberOfStudents.intValue());
        System.out.println("Average score: " + df.format(sumOfScores.doubleValue() / numberOfStudents.doubleValue()));

    }
}