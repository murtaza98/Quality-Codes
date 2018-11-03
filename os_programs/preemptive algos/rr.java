import java.util.*;
import java.lang.*;
import java.io.*;

class rr
{
	public static void main(String[] args) throws Exception{
		Input ip = new Input();
		Time time = new Time();

		InputThread ipThread = new InputThread(ip,time);
		ExecutionThread execThread = new ExecutionThread(ip,time);

		ipThread.start();
		ipThread.join();
		execThread.start();
	}
}

class ExecutionThread extends Thread
{
	Input ip;
	Time time;

	int time_quantum = 3;

	int current_running_pid = -1;
	int current_running_process_time_left = 999;
	Process currentRunningProcess = null;
	int time_quantum_used = 999;

	public ExecutionThread(Input ip,Time time){
		this.ip = ip;
		this.time = time;
	}

	public void run(){
		while(time.isOver()){
			int current_time = time.getTime();

			checkForProcessArrival(current_time);


			// boolean checkPreemption = checkForArrivalAndPreemption(current_time,current_running_process_time_left);

			if(current_running_process_time_left == 999 || isTimeQuantumExpired()){ //check if a process is scheduled or check for preemption

				//get new process from arrived queue and schedule it
				Process processToSchedule = ip.getProcessForScheduling();			 //get the first element from queue
				if(processToSchedule!=null){
					currentRunningProcess = processToSchedule;
					current_running_pid = processToSchedule.getPid();
					current_running_process_time_left = processToSchedule.getRemainingTime();
					time_quantum_used = 0; 		 
				}
			}else{
				//no preemption
			}

			updateDisplay(current_time);

			if(currentRunningProcess!=null){
				//decreament current running process burst time left in counter and object
				current_running_process_time_left--;    
				currentRunningProcess.decreamentRemainingTime();

				checkForProcessCompletion(current_time);
			}

			// ip.printAllProcess();

			time_quantum_used = time_quantum_used + 1;

			if(isTimeQuantumExpired()){
				//add current process to arrived queue if available
				if(currentRunningProcess != null){
					System.out.println("---- Preemption of process "+currentRunningProcess.getPid()+" --------");
					currentRunningProcess.setArrivalTime(current_time); //note that this -1 is done bcs the preemption
					ip.insertIntoArrivedQueue(currentRunningProcess);

					//reset values
					currentRunningProcess = null;
					current_running_pid = -1;
					current_running_process_time_left = 999;
					time_quantum_used = 999;
				}
			}

			time.increamentTime();
		}

		System.out.println("\nProcess Summary\n");
		System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
		ip.printAllProcessSummary();

	}

	private void checkForProcessArrival(int current_time){
		Iterator i = ip.getNewQueue().iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			if(p.getArrivalTime()==current_time){
				ip.insertIntoArrivedQueue(p);
			}
		}
	}

	private void updateDisplay(int currentTime){
		System.out.println("Time: " + currentTime + " Process Scheduled " + current_running_pid);
	}

	private void checkForProcessCompletion(int currentTime){
		if(current_running_process_time_left == 0){
			current_running_pid = -1;
			current_running_process_time_left = 999;
			time_quantum_used = 999;
			currentRunningProcess.setCompletionTime(currentTime);
			ip.insertIntoCompletedQueue(currentRunningProcess);
			currentRunningProcess = null;
		}
	}

	private boolean isTimeQuantumExpired(){
		return (time_quantum == time_quantum_used) ? true : false;
	}

}

class Input
{
	SortedList newQueue;
	SortedList arrivedProcess;
	SortedList completedProcess;

	public Input(){
		this.newQueue = new SortedList(new SortByOriginalArrivalTime());
		this.arrivedProcess = new SortedList(new SortByArrivalTime());
		this.completedProcess = new SortedList(null);
	}

	public SortedList getNewQueue(){
		return this.newQueue;
	}

	public SortedList getArrivedQueue(){
		return this.arrivedProcess;
	}

	public SortedList getCompletedQueue(){
		return this.completedProcess;
	}

	synchronized public void insertIntoNewQueue(Process p){
		newQueue.add(p);
	}

	synchronized public void insertIntoArrivedQueue(Process p){
		arrivedProcess.add(p);
	}

	synchronized public void insertIntoCompletedQueue(Process p){
		completedProcess.add(p);
	}

	synchronized public Process getProcessForScheduling(){
		if(arrivedProcess.isEmpty()){
			return null;
		}else{
			return (Process)arrivedProcess.removeFirst();
		}
	}

	public void printAllProcess(){
		Iterator i = newQueue.iterator();
		System.out.println("New Queue");
		while(i.hasNext()){
			Process p =(Process)i.next();
			System.out.println(p);
		}
		i = arrivedProcess.iterator();
		System.out.println("Arrived Queue");
		while(i.hasNext()){
			Process p =(Process)i.next();
			System.out.println(p);
		}
		i = completedProcess.iterator();
		System.out.println("Completed Queue");
		while(i.hasNext()){
			Process p =(Process)i.next();
			System.out.println(p);
		}
	}

	public void printAllProcessSummary(){
		Iterator i = newQueue.iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			System.out.println(p);
		}
	}
}

class Time
{
	int current_time = 0;
	int max_time = 15;
	int time_quantum = 3;
	public Time(){

	}

	synchronized public int getTime(){
		return this.current_time;
	}

	synchronized public void increamentTime(){
		this.current_time = this.current_time + 1;
	}

	public boolean isOver(){
		return ((max_time - current_time) > 0) ? true : false;
	}
}

class InputThread extends Thread
{
	Input ip;
	Time time;
	int max_process_to_schedule = 15;
	int max_burst_time = 7;

	public InputThread(Input ip,Time time){
		this.ip = ip;
		this.time = time;
	}

	public void run(){
		Random rand = new Random();
		for(int i = 0;i<max_process_to_schedule;i++){
			int pid = i;
			int arrival_time = rand.nextInt(max_process_to_schedule) +  time.getTime();
			int burst_time = rand.nextInt(max_burst_time) + 1;
			Process p = new Process(pid,arrival_time,burst_time);
			ip.insertIntoNewQueue(p);
			// ip.printAllProcess();
		}
	}
}

class Process
{
	int pid;
	int original_arrival_time;		//this will contain the time when the process first arrived
	int arrival_time;
	int burst_time;
	int completion_time = -1;
	int remaining_time;
	int turn_around_time = -1;
	int waiting_time = -1;

	public Process(int pid,int arrival_time,int burst_time){
		this.pid=pid;
		this.arrival_time=arrival_time;
		this.original_arrival_time=arrival_time;
		this.burst_time=burst_time;
		this.remaining_time = burst_time;
	}
	public int getPid(){
		return this.pid;
	}
	public int getOriginalArrivalTime(){
		return this.original_arrival_time;
	}
	public int getArrivalTime(){
		return this.arrival_time;
	}
	public int getBurstTime(){
		return this.burst_time;
	}
	public int getRemainingTime(){
		return this.remaining_time;
	}
	public void setArrivalTime(int arrival_time){
		this.arrival_time=arrival_time;
	}
	public void setCompletionTime(int completion_time){
		this.completion_time = completion_time;
		computeProcessSummary();
	}
	private void computeProcessSummary(){
		this.turn_around_time = this.completion_time - this.original_arrival_time + 1;
		this.waiting_time = this.turn_around_time - this.burst_time;
	}
	public void decreamentRemainingTime(){
		this.remaining_time = this.remaining_time - 1;
	}
	public String toString(){
		return this.pid + "\t" + this.original_arrival_time + "\t"+this.burst_time + "\t" + this.completion_time + "\t" + this.turn_around_time +"\t" + this.waiting_time;
	}
}

class SortByOriginalArrivalTime implements Comparator<Process>
{
	public int compare(Process p1, Process p2){
		return p1.getOriginalArrivalTime() - p2.getOriginalArrivalTime();
	}
}

class SortByArrivalTime implements Comparator<Process>
{
	public int compare(Process p1, Process p2){
		return p1.getArrivalTime() - p2.getArrivalTime();
	}
}

class SortedList<Process> extends LinkedList<Process>
{
    private Comparator<Process> comparator;

    public SortedList(final Comparator<Process> compare)
    {
        this.comparator = compare;
    }

    /**
    * this ignores the index and delegates to .add() 
    * so it will be sorted into the correct place immediately.
    */
    @Override
    public void add(int index, Process element)
    {
        this.add(element);     
    }

    @Override
    public boolean add(final Process e)
    {
        final boolean result = super.add(e);
        if(this.comparator != null){
        	Collections.sort(this, this.comparator);
        }
        return result;
    }
}
