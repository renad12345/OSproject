import java.util.ArrayList;

public class EventScheduler {
    private ArrayList<Event> eventQueue; // Dynamic list for events
    private Queue readyQueue;
    private int currentTime;
    private Process currentProcess;

    public EventScheduler(Queue processes) {
        eventQueue = new ArrayList<>(); //Use ArrayList for dynamic event storage
        readyQueue = new Queue(processes.size);
        currentTime = 0;
        currentProcess = null;

// Add processes to eventQueue
for (int i = 0; i < processes.size; i++) {
Process p = processes.arr[i];
int arrivalTime = p.getArrivalTime(); 
Event newEvent = new Event(arrivalTime, p); //Create a new Event object
eventQueue.add(newEvent);//Add the event to the eventQueue list
}

        //sorting eventQueue(sorts events by arrival time in ascending order)
        //finds the smallest event (earliest arrival time) and moves it to its correct position
        for (int i = 0; i < eventQueue.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < eventQueue.size(); j++) {
                if (eventQueue.get(j).time < eventQueue.get(minIndex).time) {
                    minIndex = j;
                }
            }
            //swap the smallest element found with the current element at index i
            Event temp = eventQueue.get(i);
            eventQueue.set(i, eventQueue.get(minIndex));
            eventQueue.set(minIndex, temp);
        }
    }

    public ArrayList<Event> getEventQueue() {
        return eventQueue;
    }
    
    public Queue getReadyQueue() {
        return readyQueue;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public static class Event {
        int time;
        Process process;

        public Event(int time, Process process) {
            this.time = time;
            this.process = process;
        }
    }
}

