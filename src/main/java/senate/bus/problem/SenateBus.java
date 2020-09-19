package senate.bus.problem;

import java.util.Random;

public class SenateBus {
    public static void main (String args[]){
        SharedData resource = new SharedData();
        int bus_id=0, rider_id=0;                           //Ids are assigned for buses and riders for the easy demonstration of the program
        long diff_bus=0,diff_rider=0,time_curr=0,time_prev_bus=System.currentTimeMillis(),time_prev_rider=System.currentTimeMillis();
        double mean_rider=30000,mean_bus=1200000;           //Declaring the mean of the exponential distributions of inter-arrival times of riders and buses
        double rand_bus  = 0.0,rand_rider=0,wait_time_rider=0,wait_time_bus=0;

        rand_bus = new Random().nextDouble();
        wait_time_rider = Math.round(Math.log10(rand_bus)*-1*mean_rider);  //Calculating the time before the next bus arrives

        rand_rider = new Random().nextDouble();
        wait_time_bus = Math.round(Math.log10(rand_rider)*-1*mean_bus);     //Calculating the time before the next bus arrives
        //System.out.println("rand - "+Math.round(rand*100.0)/100.0+ " wait_time_rider -  "+wait_time_rider+" wait_time_bus - "+wait_time_bus);

        while(Boolean.TRUE) {

            time_curr=System.currentTimeMillis();
            diff_rider=time_curr-time_prev_rider;                      //Calculating the time passed after the previous rider
            diff_bus=time_curr-time_prev_bus;                          //Calculating the time passed after the previous bus


            if(diff_rider==wait_time_rider){                           //Checking if it is the time for the next rider to come
                //System.out.println("rand - "+Math.round(rand*100.0)/100.0+ " wait_time_rider -  "+wait_time_rider);
                Rider new_rider = new Rider(rider_id++,resource);
                new Thread(new_rider).start();
                time_prev_rider=time_curr;

                rand_rider  = new Random().nextDouble();
                wait_time_rider = Math.round(Math.log10(rand_rider)*-1*mean_rider); //Calculating the inter arrival time before the next rider arrives
            }
            if(diff_bus==wait_time_bus){                           //Checking if it is the time for the next bus to come
                //System.out.println("rand - "+Math.round(rand*100.0)/100.0+ " wait_time_bus - "+wait_time_bus);
                Bus new_bus = new Bus(bus_id++,resource);
                new Thread(new_bus).start();
                time_prev_bus=time_curr;

                rand_bus  = new Random().nextDouble();
                wait_time_bus = Math.round(Math.log10(rand_bus)*-1*mean_bus);//Calculating the inter arrival time before the next bus arrives
            }
        }
    }
}
