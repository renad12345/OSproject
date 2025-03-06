import java.util.List;

public class PerformanceMetrics {
    private Process[] processes;
    private int totalTime;

    public PerformanceMetrics(Process[] processes, int totalTime) {
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


//double avgTurnaroundTime = processes.length > 0 ? (double) totalTurnaroundTime / processes.length : 0;
//double avgWaitingTime = processes.length > 0 ? (double) totalWaitingTime / processes.length : 0;
//double cpuUtilization = totalTime > 0 ? ((double) executionTime / totalTime) * 100 : 0;

        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.length;
        double avgWaitingTime = (double) totalWaitingTime / processes.length;
        double cpuUtilization = ((double) executionTime / totalTime) * 100;

        System.out.printf("Average Turnaround Time: %.2f ms\n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f ms\n", avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}
