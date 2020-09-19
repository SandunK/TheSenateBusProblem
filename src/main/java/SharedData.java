import java.util.concurrent.Semaphore;

public class SharedData {
    private int waiting = 0;
    private final Semaphore riderMutex = new Semaphore(1);  //To protect waiting_riders
    private final Semaphore bus = new Semaphore(0);    //Signals when the bus has arrived
    private final Semaphore boarded = new Semaphore(0);// Signals that a rider has boarded

    public int getWaiting() {
        return waiting;
    }

    public  Semaphore getRiderMutex() {
        return riderMutex;
    }

    public  Semaphore getBus() {
        return bus;
    }

    public  Semaphore getBoarded() {
        return boarded;
    }

    public void incrementWaiting() {
        waiting += 1;
    }
}
