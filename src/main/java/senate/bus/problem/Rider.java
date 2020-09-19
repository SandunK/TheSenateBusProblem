package senate.bus.problem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rider implements Runnable {
    private final SharedData sharedData;
    private int id;
    private static final Logger logger = LogManager.getLogger(Rider.class);

    Rider(int index, SharedData sharedData) {
        this.id = index;
        this.sharedData = sharedData;
    }


    public void run() {
        try {

            /*
             *   First the rider increments the waiting rider count to get into the waiting rider set.
             *   Since the waiting_riders is a shared variable, the rider sgould acquire the mutex in order to access this variable.
             */
            sharedData.getRiderMutex().acquire();                //rider locks the mutex
            logger.info("senate.bus.problem.Rider " + id + " is waiting !!");
            sharedData.incrementWaiting();                 //increment the number of waiting riders by one
            sharedData.getRiderMutex().release();                //release the mutex
            sharedData.getBus().acquire();                  //acquire the bus semaphore to get on board
            logger.info("senate.bus.problem.Rider " + id + "got onboard.");            //acquire the bus semaphore to get on board
            sharedData.getBoarded().release();              //once boarded, release the boarded semaphore

        } catch (InterruptedException ex) { //Exception if the above procedure got interupted in the middle
            logger.info("senate.bus.problem.Rider" + id + "'s thread got interrupted !!", ex);

        }
    }
}
