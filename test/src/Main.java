import java.util.Scanner;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        Queue processes = new Queue(numProcesses);
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            Process p = new Process(i + 1, arrivalTime, burstTime);
            processes.enqueue(p);
        }

        PSJF(processes);
/*
        System.out.print("\nNumber of processes= " + numProcesses + " (");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + (i + 1));
            if (i < numProcesses - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");

        System.out.println("Arrival times and burst times as follows:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("P" + (i + 1) + ": Arrival time = " + processes[i].getArrivalTime() +
                    ", Burst time = " + processes[i].getBurstTime() + " ms");
        }
*/
        scanner.close();

    }

    public static void PSJF(Queue processes) {
         EventScheduler scheduler = new EventScheduler(processes); //Initialize event scheduler with the queue of processes
        //save process which their start time = current time in queue 'available'
        //Queue available = new Queue(processes.size);
        Queue available = scheduler.getReadyQueue(); //Get ready queue (where processes will be placed when ready to execute)
        EventScheduler.Event[] eventQueue = scheduler.getEventQueue(); //Get event queue(which stores processes based on arrival time)
        System.out.println("num of processs: " + processes.size);

        int time = 0;

        Process last = null;
        Process leastBTprocess;
        int eventIndex = 0; //Track processed events


     //Skip forward in time to the first arriving process
    if (eventQueue.length > 0 && eventQueue[0].time > 0) {
        time = eventQueue[0].time;
    }

        while (eventIndex < eventQueue.length || available.size != 0) {
         System.out.println("still proccess in queue");
        System.out.println("Current time: " + time);

        //Add new arriving processes to the available queue
        while (eventIndex < eventQueue.length && eventQueue[eventIndex].time == time) {
        Process newprocess=eventQueue[eventIndex].process; 
        System.out.println(" processs checked: " + newprocess.getID());
            available.enqueue(newprocess);
            System.out.println("Process added to available: " + newprocess.getID());
            eventIndex++;
        }



           /* //repeat until all processes done with processing
        while (processes.size != 0 || available.size != 0) {
            System.out.println("still proccess in queue");
            System.out.println("current time: " + time);


            int PSize = processes.size;

            //to fill available queue, iterate over all processes in received queue
            //if their start time = current time, enqueue them to 'available' queue
            for (int i = 0; i < PSize; i++) {
                Process process = processes.dequeue();
                System.out.println(" processs checked: " + process.getID());

                if (process.getArrivalTime() <= time) {
                    available.enqueue(process);
                    System.out.println(" processs added to avaliable: " + process.getID());
                }
                //if the process start time != current time, return them back to 'processes' queue
                else {
                    processes.enqueue(process);
                    System.out.println("processs was not added to avaliable: " + process.getID());
                }

            }*/

            //if no processes available, skip this iteration and go to time+1
            if (available.size == 0) {
                time++;
                System.out.println("no processs available");
                continue;
            }

            //if there some processes available, sort them incrementally according to their burst
            //time

            available.sort();
            System.out.println("there is processes available, sort");


            leastBTprocess = available.getFront();
            System.out.println("the least burst is: " + leastBTprocess.getID() + ", its BT: " + leastBTprocess.getBurstTime());

            //context switch if recent process differs from previous process
            if (last != null && leastBTprocess.getID() != last.getID()) {
                System.out.println("Context switch: ");
                time++;

            }

            leastBTprocess.setBurstTime(leastBTprocess.getBurstTime() - 1);
            System.out.println("burst time after decrementing: " + leastBTprocess.getID() + "its BT: " + leastBTprocess.getBurstTime());


            //just when the process burstTime = 0, remove it from available queue
            if (leastBTprocess.getBurstTime() == 0) {
                System.out.println("burst time for proccess is 0, its id" + leastBTprocess.getID());
                available.dequeue();
            }

            last = leastBTprocess;
            //increment one time unit for processing
            time++;


        }

        System.out.println("last time: " + time);

    }

}