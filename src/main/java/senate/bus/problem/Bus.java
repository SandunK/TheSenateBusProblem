package senate.bus.problem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bus implements Runnable{

    private int totalRidersWaitingToBoard;   // number of riders available for the bus
    private int bus_id;
    private SharedData resource;
    private static final Logger logger = LogManager.getLogger(Bus.class);
    private int waiting;

    Bus(int index, SharedData resource) {
        this.resource = resource;
        this.bus_id = index;
        this.waiting=resource.getWaiting();

    }

    public void run() {
        try {
            resource.getRiderMutex().acquire();                             //bus locks the Rider mutex
            logger.info("The bus " + bus_id + " has arrived to the bus stop and locked it!!");

            totalRidersWaitingToBoard = Math.min(waiting, 50);// If the waiting passengers is less than 50, then all can get in.
            logger.info("waiting  : " + waiting + " To board : " + totalRidersWaitingToBoard);

            for (int i = 0; i < totalRidersWaitingToBoard; i++) {     //A loop to get all the available riders  on board
                logger.info("The bus " + bus_id + " is ready to board " + i + "th rider");
                resource.getBus().release();                              //bus signals that it has arrived and can take a passenger on board
                logger.info("The bus " + bus_id + " released the bus lock " + i + "th rider");
                resource.getBoarded().acquire();                          //Allows one rider to get on board
                logger.info("The bus " + bus_id + " acquired boarded !!!!!");
            }


            waiting = Math.max((waiting - 50), 0); // waiting riders after on board process
            resource.setWaiting(waiting);       // update the global waiting variable
            resource.getRiderMutex().release();                  //bus unlocks the mutex

        } catch (InterruptedException ex) {
            logger.error( "The bus " + bus_id + "'s thread got interrupted !!", ex);
        }

        logger.info("The bus " + bus_id + " departed with " + totalRidersWaitingToBoard + " riders on board!");
    }
}
