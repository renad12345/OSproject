class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int completionTime;
    private int originalBurstTime;
    private int startTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime;
    }



    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getOriginalBurstTime() {
        return originalBurstTime;
    }


    @Override
    public String toString() {
        return "P" + id + ": Arrival Time = " + arrivalTime + ", Burst Time = " + burstTime + "ms";
    }

    public int getID() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

}