import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Student implements Runnable{
    private long arrivalTime;
    private long defenceStartTime;
    private long totalDefenceTime;
    private Professor professor;
    private Assistant assistant;
    private String studentName;
    private String examinerName;
    private int score = 5;
    private boolean isDone = false;


    public Student(String studentName, Assistant assistant, Professor professor, long totalDefenceTime) {
        this.studentName = studentName;
        this.assistant = assistant;
        this.professor = professor;
        this.totalDefenceTime = totalDefenceTime;
    }


    @Override
    public void run() {
        arrivalTime = System.currentTimeMillis();

        // Da li je odbrana jos uvek traje?
        if(arrivalTime + totalDefenceTime - Main.initTime > 5000) return;

        while(!Main.isFinished.get() && !isDone){

            // idemo kod profesora
            if((new Random()).nextInt(2) == 0){
                if (professor.getSemaphore().tryAcquire() && !Main.isFinished.get()) {
                    try {
                        professor.getCyclicBarrier().await(500, TimeUnit.MILLISECONDS);
                        defenceStartTime = System.currentTimeMillis();
                    } catch (InterruptedException e) {
                        // Odbrana je zavrsena
                        return;
                    } catch (BrokenBarrierException | TimeoutException e) {
                        professor.getSemaphore().release();
                        tryAssistant();
                        continue;
                    }

                    try {Thread.sleep(totalDefenceTime);}
                    catch (InterruptedException e) {
                        // Odbrana je zavrsena
                        return;
                    }

                    professor.getSemaphore().release();
                    examinerName = professor.getThreadName();
                    score = professor.createScore();
                    isDone = true;
                }

            } else { // idemo kod asistenta
                tryAssistant();
            }
        }

        if(isDone) {
            Main.sumOfScores.addAndGet(score);
            Main.numberOfStudents.incrementAndGet();
            arrivalTime -= Main.initTime;
            defenceStartTime -= Main.initTime;

            System.out.println("Thread: <" + studentName + ">" +
                    " < Arrival time: " + arrivalTime + "ms>" +
                    " < Examiner: " + examinerName + ">" +
                    " < TTC: " + totalDefenceTime + "ms>" +
                    " < Defend start time: " + defenceStartTime + "ms>" +
                    " < Score: " + score + ">");
        }
    }

    private void tryAssistant(){
        if(assistant.getSemaphore().tryAcquire() && !isDone && !Main.isFinished.get()){
            try {
                defenceStartTime = System.currentTimeMillis();
                Thread.sleep(defenceStartTime);
                score = assistant.createScore();
                examinerName = assistant.getThreadName();
                assistant.getSemaphore().release();
                isDone = true;

            } catch (InterruptedException e) {
                // Odbrana je zavrsena
            }
        }
    }

}
