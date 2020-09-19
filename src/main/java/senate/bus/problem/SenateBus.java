package senate.bus.problem;

import java.util.Random;

public class SenateBus {
    public static void main (String args[]){
        SharedData resource = new SharedData();
        int bus_id = 0;
        int rider_id = 0;                           // id assigned for the buses and riders for easy demostration
        long bus_diff = 0;
        long rider_diff = 0;
        long time_curr = 0;
        long time_prev_bus = System.currentTimeMillis();
        long time_prev_rider = System.currentTimeMillis();
        double mean_rider = 10000;
        double mean_bus=120000;           // The mean of the exponential distributions of inter-arrival times of riders and buses declared
        double random_bus = 0.0;
        double random_rider = 0;
        double rider_waitTime = 0;
        double bus_waitTime = 0;

        random_bus = new Random().nextDouble();
        rider_waitTime = Math.round(Math.log10(random_bus)*-1*mean_rider);  //Time before the next rider arrives
        random_rider = new Random().nextDouble();
        bus_waitTime = Math.round(Math.log10(random_rider)*-1*mean_bus);     //Time before the next bus arrives

        while(Boolean.TRUE) {

            time_curr = System.currentTimeMillis();
            rider_diff = time_curr - time_prev_rider;                      //Time passed after the previous rider
            bus_diff = time_curr - time_prev_bus;                          //Time passed after the previous bus


            if(rider_diff >= rider_waitTime){                           //Check if it is the time for the next rider to come
                Rider new_rider = new Rider(rider_id++,resource);
                new Thread(new_rider).start();
                time_prev_rider = time_curr;
                random_rider  = new Random().nextDouble();
                rider_waitTime = Math.round(Math.log10(random_rider)*-1*mean_rider); //Calculate the inter arrival time before the next rider arrives
            }
            if(bus_diff >= bus_waitTime){                           //Checking if it is the time for the next bus to come

                Bus new_bus = new Bus(bus_id++,resource);
                new Thread(new_bus).start();
                time_prev_bus = time_curr;
                random_bus = new Random().nextDouble();
                bus_waitTime = Math.round(Math.log10(random_bus)*-1*mean_bus);//Calculate the inter arrival time before the next bus arrives
            }
        }
    }
}
