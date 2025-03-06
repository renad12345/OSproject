import java.util.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numProcesses;
        Process[] processes;

        //ask user to enter a valid number of processes
        do {
            System.out.print("Enter the number of processes: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();
            }
            numProcesses = scanner.nextInt();
        } while (numProcesses <= 0);


        processes = new Process[numProcesses];

        //reading processes data
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            Process p = new Process(i + 1, arrivalTime, burstTime);
            //processes.enqueue(p);
            processes[i] = p;
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
            System.out.println("P" + (i + 1) + ": Arrival time = " + processes[i].getArrivalTime() +
                    ", Burst time = " + processes[i].getBurstTime() + " ms");
        }

        EventScheduler E = new EventScheduler(processes);
        E.schedule();


        int totalTime = E.getCurrentTime() - E.getStatrTime();
        E.printGanttChart();

        PerformanceMetrics metrics = new PerformanceMetrics(processes, totalTime);
        metrics.calculateAndPrintMetrics();


        scanner.close();

    }


}