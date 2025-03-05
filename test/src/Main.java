import java.util.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numProcesses;

        //ask user to enter a valid number of processes
        do {
            System.out.print("Enter the number of processes: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();     }
            numProcesses = scanner.nextInt();
        } while (numProcesses <= 0);


        Queue processes = new Queue(numProcesses);
        List<Process> processList = new ArrayList<>();

        //reading processes data
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            Process p = new Process(i + 1, arrivalTime, burstTime);
            processes.enqueue(p);
            processList.add(new Process(p));
        }


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
            System.out.println("P" + (i + 1) + ": Arrival time = " + processes.arr[i].getArrivalTime() +
                    ", Burst time = " + processes.arr[i].getBurstTime() + " ms");
        }

        int totalTime = PSJF(processes, processList);
        PerformanceMetrics metrics = new PerformanceMetrics(processList, totalTime);
        metrics.calculateAndPrintMetrics();


        scanner.close();

    }

    public static int PSJF(Queue processes, List<Process> processList) {
        EventScheduler scheduler = new EventScheduler(processes); //Initialize event scheduler with the queue of processes
        Queue available = scheduler.getReadyQueue(); //Get ready queue (where processes will be placed when ready to execute)
        ArrayList<EventScheduler.Event> eventQueue = scheduler.getEventQueue(); //  //Get event queue(which stores processes based on arrival time)
        List <String> GanttChart = new ArrayList<>();


        int time = 0;
        int lastProccessID = -1;
        Process leastBTprocess;
        int eventIndex = 0; //Track processed events
        int segmentTime = 0; //it is used to set the start time for each process in ganttChart


        //Skip forward in time to the first arriving process
        if (!eventQueue.isEmpty() && eventQueue.get(0).time > 0) {
            time = eventQueue.get(0).time;
            segmentTime = time;
        }


        while (eventIndex < eventQueue.size() || available.size != 0) {
            //Add new arriving processes to the available queue
            while (eventIndex < eventQueue.size() && eventQueue.get(eventIndex).time <= time) {
                Process newprocess=eventQueue.get(eventIndex).process;
                available.enqueue(newprocess);
                eventIndex++;
            }

            //if no processes available, skip this iteration and go to time+1
            if (available.size == 0) {
                time++;
                continue;
            }

            //if there are some processes available, sort them incrementally according to their burst time
            available.sort();

            leastBTprocess = available.getFront();

            //context switch if recent process differs from previous process
            if(lastProccessID != leastBTprocess.getID()){

                if(lastProccessID != -1) {
                    GanttChart.add(segmentTime + " - " + time + "  P" + lastProccessID);
                    GanttChart.add(time + " - " + (time+1) + "  CS");
                    time++;
                }

                segmentTime = time;
            }

            //decrement the burst time for processed process by 1
            leastBTprocess.setBurstTime(leastBTprocess.getBurstTime() - 1);

            if (leastBTprocess.getBurstTime() == 0) {
                available.dequeue();
                for (Process p : processList) {
                    if (p.getID() == leastBTprocess.getID()) {
                        p.setCompletionTime(time + 1);
                        break;
                    }
                }
            }

            lastProccessID = leastBTprocess.getID();

            //increment one time unit for processing
            time++;
        }

        //adding last process to ganttChart
        GanttChart.add(segmentTime + " - " + time + "  P" + lastProccessID);

        printGanttChart(GanttChart);


        return time;
    }

    public static void printGanttChart(List <String> GanttChart){
        System.out.println("Time  Process/CS");
        for(String row: GanttChart){
            System.out.println(row);
        }
    }

/*

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

            }

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
if (leastBTprocess.getBurstTime() == 0) {
    available.dequeue();
    for (Process p : processList) {
        if (p.getID() == leastBTprocess.getID()) {
            p.setCompletionTime(time + 1);
            break;
        }
    }
}

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

    } */

}