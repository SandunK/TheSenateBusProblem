package senate.bus.problem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bus implements Runnable{

    private int totalRidersWaitingToBoard;   //A variable to hold the number of riders available for the bus
    private int bus_id;
//    private int totalBoardedRiders = 0;   //A variable to hold the number of riders boarded into the bus
    private SharedData resource;
    private static final Logger logger = LogManager.getLogger(Bus.class);
    private int waiting;
    /*
    Each bus is assigned a unique identifier so that it is easy to demonstrate the procedure when there are more than one bus.
    */
    Bus(int index, SharedData resource) {
        this.resource = resource;
        this.bus_id = index;
        this.waiting=resource.getWaiting();

    }

    public void run() {
        try {
            resource.getRiderMutex().acquire();                             //bus locks the mutex
            logger.info("The bus " + bus_id + " has arrived to the bus stop and locked it!!");//The number of riders available for the bus is 50 out of all the passengers in the boarding area. If the waiting passengers is less than 50, then all can get in.

            totalRidersWaitingToBoard = Math.min(waiting, 50);
            logger.info("waiting  : " + waiting + " To board : " + totalRidersWaitingToBoard);

            for (int i = 0; i < totalRidersWaitingToBoard; i++) {     //A loop to get all the available riders  on board
                logger.info("The bus " + bus_id + " is ready to board " + i + "th rider");
                resource.getBus().release();                              //bus signals that it has arrived and can take a passenger on board
                logger.info("The bus " + bus_id + " released the bus lock " + i + "th rider");
                resource.getBoarded().acquire();                          //Allows one rider to get on board
                logger.info("The bus " + bus_id + " acquired boarded !!!!! !!");
            }

                /*
                If the number of riders waiting earlier was greater than 50, then the remainig riders is waiting_riders - 50
                Else if the number of riders who were waiting earlier was less than 50, then all of them got on board. therefore the number of remaining riders is 0.
                */
            waiting = Math.max((waiting - 50), 0);
            resource.setWaiting(waiting);
            resource.getRiderMutex().release();                             //bus unlocks the mutex

        } catch (InterruptedException ex) {              //Exception if the above procedure got interupted in the middle
            logger.error( "The bus " + bus_id + "'s thread got interrupted !!", ex);
        }

        logger.info("The bus " + bus_id + " departed with " + totalRidersWaitingToBoard + " riders on board!");
    }
}
