public class EventScheduler {
    private Event[] eventQueue;
    private Queue readyQueue;
    private int currentTime;
    private Process currentProcess;
    private int eventCount;//track the number of events in the array

    public EventScheduler(Queue processes) {
    int maxSize = processes.size; // Assume the max size is the size of the processes
     eventQueue = new Event[maxSize]; // Create an array to hold the events
        readyQueue = new Queue(processes.size);
        currentTime = 0;
        currentProcess = null;
         eventCount = 0;

        //move through the queue without dequeuing
       for (int i = 0; i < maxSize; i++) {
 Process p = processes.arr[i]; // Access process without removing it            
eventQueue[eventCount++] = new Event(p.getArrivalTime(), p);
                    }

        //Sort events by time in ascending order (simplified sorting)
        for (int i = 0; i < eventCount; i++) {
            for (int j = i + 1; j <eventCount; j++) {
                if (eventQueue[i].time > eventQueue[j].time) {
                    Event temp = eventQueue[i];
                    eventQueue[i]=eventQueue[j];
                    eventQueue[j]=temp;
                }
            }
        }
    }

   public Event[] getEventQueue() {
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

    public class Event {
        int time;
        Process process;

        public Event(int time, Process process) {
            this.time = time;
            this.process = process;
        }
    }
}
