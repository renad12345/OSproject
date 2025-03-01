class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public Process(Process process) {
        id = process.getID();
        burstTime = process.getBurstTime();
        arrivalTime = process.getArrivalTime();
    }


    @Override
    public String toString() {
        return "P" + id + ": Arrival Time = " + arrivalTime + ", Burst Time = " + burstTime + "ms";
    }

    public int getID(){
        return id;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
}
