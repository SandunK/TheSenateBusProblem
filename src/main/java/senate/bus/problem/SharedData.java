package senate.bus.problem;

import java.util.concurrent.Semaphore;

class SharedData {
    private int waiting = 0;
    private final Semaphore riderMutex = new Semaphore(1);  //To protect waiting_riders
    private final Semaphore bus = new Semaphore(0);    //Signals when the bus has arrived
    private final Semaphore boarded = new Semaphore(0);// Signals that a rider has boarded

    // Getters
    int getWaiting() {
        return waiting;
    }

    Semaphore getRiderMutex() {
        return riderMutex;
    }

    Semaphore getBus() {
        return bus;
    }

    Semaphore getBoarded() {
        return boarded;
    }

    // Setters
    void incrementWaiting() {
        waiting += 1;
    }
    void setWaiting(int waiting) {
        this.waiting = waiting;
    }
}
