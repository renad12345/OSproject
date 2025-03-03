import java.util.List;

public class PerformanceMetrics {
    private List<Process> processes;
    private int totalTime;

    public PerformanceMetrics(List<Process> processes, int totalTime) {
        this.processes = processes;
        this.totalTime = totalTime;
    }

    public void calculateAndPrintMetrics() {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int executionTime = 0;

        System.out.println("\nPerformance Metrics");
        System.out.println("-------------------------------------");

        for (Process p : processes) {
            int turnaroundTime = p.getCompletionTime() - p.getArrivalTime();
            int waitingTime = turnaroundTime - p.getOriginalBurstTime();

            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
            executionTime += p.getOriginalBurstTime();
        }

//double avgTurnaroundTime = processes.size() > 0 ? (double) totalTurnaroundTime / processes.size() : 0;
//double avgWaitingTime = processes.size() > 0 ? (double) totalWaitingTime / processes.size() : 0;
//double cpuUtilization = totalTime > 0 ? ((double) executionTime / totalTime) * 100 : 0;

      double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double avgWaitingTime = (double) totalWaitingTime / processes.size();
        double cpuUtilization = ((double) executionTime / totalTime) * 100;

        System.out.printf("Average Turnaround Time: %.2f ms\n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f ms\n", avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}
