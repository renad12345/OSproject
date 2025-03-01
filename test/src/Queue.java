public class Queue {
    Process[] arr;
    int size;
    int capacity;

    Queue(int qSize) {
        size = 0;
        capacity = qSize;
        arr = new Process[qSize];
    }

    void enqueue(Process x) {

        // If queue is full
        if (size == capacity) {
            return;
        }

        arr[size] = x;

        // Increment queue size.
        size++;
    }

    Process dequeue() {

        // If queue is empty
        if (size == 0) {
            return null;
        }

        // Shift all the elements
        // to the left.
        //# Process p= new Process(arr[0]);
        Process p = arr[0];
        for (int i = 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }

        // decrement queue size
        size--;
        return p;
    }

    void sort() {
        for (int i = 1; i < size; ++i) {
            Process processKey = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && (arr[j].getBurstTime() > processKey.getBurstTime() ||
                    (arr[j].getBurstTime() == processKey.getBurstTime() && arr[j].getArrivalTime() > processKey.getArrivalTime()))) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = processKey;
        }
    }

    public Process getFront() {
        if (size == 0) return null;

        return arr[0];
    }
}
