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

            sharedData.getRiderMutex().acquire();                //rider locks the mutex
            int waiting = sharedData.getWaiting()+1;
            logger.info("The Rider " + id + " is waiting !! Total waiting riders: "+waiting);
            sharedData.incrementWaiting();                 //increment the number of waiting riders by one
            sharedData.getRiderMutex().release();                //release the mutex
            sharedData.getBus().acquire();                  //acquire the bus semaphore to get on board
            logger.info("The Rider " + id + "got onboard.");
            sharedData.getBoarded().release();              //once boarded, release the boarded semaphore

        } catch (InterruptedException ex) {
            logger.info("The Rider" + id + "'s thread got interrupted !!", ex);

        }
    }
}
