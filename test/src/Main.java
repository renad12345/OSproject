//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    }

    void PSJF(Queue processes){
        //save proccess which their start time = current time in queue 'available'
        Queue available = new Queue(processes.size);
        int time = 0;

        while (processes.size != 0){
            int PSize = processes.size;

            //to fill available queue, iterate over all processes in received queue
            //if their start time = current time, enqueue them to 'available' queue
            for(int i =0; i<PSize ;i++)
                processe p = processes.dequeue();
                if(p.arrivalTime == time)
                    available.enqueue(p);
            //if the process start time != current time, return them back to 'processes' queue
                else processes.enqueue(p);

            time++;
            //if no processes available, skip this iteration and go to time+1
            if(available.size==0)
                continue;
                //if there some processes available, sort them incrementally according to their burst
            //time
            else
                available.sort();
            processe leastBTprocess = available.getFront();
            leastBTprocess.burstTime--;
            //just when the proccess burstTime = 0, remove it from available queue
            if(leastBTprocess.burstTime == 0)
                available.dequeue();


        }
    }
}