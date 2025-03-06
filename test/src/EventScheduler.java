import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EventScheduler {
    private PriorityQueue<Event> eventQueue; // Dynamic list for events
    private PriorityQueue<Process> readyQueue;
    private int currentTime;
    private Process currentProcess;
    private Event currentCompletedEvent;
    private int statrTime;
    private static List<String> ganttChart;

    public EventScheduler(Process[] processes) {
        eventQueue = new PriorityQueue<>(); // Use ArrayList for dynamic event storage
        readyQueue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getBurstTime() == p2.getBurstTime())
                return p1.getArrivalTime() - p2.getArrivalTime();
            return p1.getBurstTime() - p2.getBurstTime();
        });

        ganttChart = new ArrayList<>();
        currentTime = 0;
        statrTime = 0;
        currentProcess = null;
        currentCompletedEvent = null;
        int eventCount = processes.length;

        //Skip forward in time to the first arriving process
        if (!eventQueue.isEmpty() && eventQueue.peek().time > 0) {
            statrTime = eventQueue.peek().time;
            currentTime = statrTime;
        }

        for (Process p : processes) {
            int arrivalTime = p.getArrivalTime();
            Event newEvent = new Event(arrivalTime, p, "Arrival");
            eventQueue.add(newEvent);
        }
    }

    public void schedule() {
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.remove();
            currentTime = event.time;
            if (event.status.equals("Arrival"))
                handleArrivedProcess(event);
            else if (event.status.equals("Completed"))
                handleCompletedProcess(event.process);
        }

    }

    public void handleArrivedProcess(Event e) {
        readyQueue.add(e.process);

        if (currentProcess == null || e.process.getBurstTime() < currentProcess.getBurstTime()) {
            readyQueue.remove(e.process);
            preempt(currentProcess, e.process);
        }

    }

    public void preempt(Process runningProcess, Process newProcess) {
        if (runningProcess != null) {
            int remaining = runningProcess.getBurstTime() - (currentTime - runningProcess.getStartTime());
            runningProcess.setBurstTime(remaining);
            ganttChart.add(runningProcess.getStartTime() + " - " + currentTime + "  P" + runningProcess.getID());

            if (currentCompletedEvent != null) {
                eventQueue.remove(currentCompletedEvent);
                currentCompletedEvent = null;
            }

            // Context switch
            currentTime++;
            ganttChart.add((currentTime - 1) + " - " + currentTime + "  " + "CS");

            if (runningProcess.getBurstTime() > 0) {
                readyQueue.add(runningProcess);
            }
        }

        newProcess.setStartTime(currentTime);
        currentProcess = newProcess;

        currentCompletedEvent = new Event(currentTime + newProcess.getBurstTime(), newProcess, "Completed");
        eventQueue.add(currentCompletedEvent);
    }

    public void handleCompletedProcess(Process completedProcess) {
        completedProcess.setCompletionTime(currentTime);
        ganttChart.add(completedProcess.getStartTime() + " - " + currentTime + "  P" + completedProcess.getID());
        currentCompletedEvent = null;
        currentProcess = null;

        if (!readyQueue.isEmpty()) {
            //context switch if there is processes in ready queue
            currentTime++;
            ganttChart.add(currentTime - 1 + " - " + currentTime + "  " + "CS");


            Process newProcess = readyQueue.poll();
            preempt(null, newProcess);
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getStatrTime() {
        return statrTime;
    }

    public static void printGanttChart() {
        System.out.println("Time  Process/CS");
        for (String row : ganttChart) {
            System.out.println(row);
        }
    }

    public static class Event implements Comparable<Event> {
        int time;
        Process process;
        String status;


        public Event(int time, Process process, String status) {
            this.time = time;
            this.process = process;
            this.status = status;
        }

        @Override
        public int compareTo(Event o) {
            return this.time - o.time;
        }
    }
}
