import java.util.*;
import java.lang.*;
import java.io.*;

class priority_preempt
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

	int current_running_pid = -1;
	int current_process_priority = 999;
	int current_running_process_time_left = 999;
	Process currentRunningProcess = null;

	public ExecutionThread(Input ip,Time time){
		this.ip = ip;
		this.time = time;
	}

	public void run(){
		while(time.isOver()){
			int current_time = time.getTime();

			boolean checkPreemption = checkForArrivalAndPreemption(current_time,current_running_process_time_left);

			if(current_process_priority == 999 || checkPreemption){ //check if a process is scheduled or check for preemption

				//add current process to arrived queue if available
				if(currentRunningProcess != null){
					System.out.println("---- Preemption of process "+currentRunningProcess.getPid()+" --------");
					ip.insertIntoArrivedQueue(currentRunningProcess);
				}

				//get new process from arrived queue and schedule it
				Process processToSchedule = ip.getProcessForScheduling();			 //get the first element from queue
				if(processToSchedule!=null){
					currentRunningProcess = processToSchedule;
					current_running_pid = processToSchedule.getPid();
					current_running_process_time_left = processToSchedule.getRemainingTime();
					current_process_priority = processToSchedule.getPriority();
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

			time.increamentTime();
		}

		System.out.println("\nProcess Summary\n");
		System.out.println("PID\tPT\tAT\tBT\tCT\tTAT\tWT");
		ip.printAllProcessSummary();

	}

	public boolean checkForArrivalAndPreemption(int current_time, int current_running_process_time_left){
		//this function will check 3 condition:-
		//1:- if no process is arrived, then it should return false
		//2:- if processes are arrived, then it will return true if a process is available with smaller burst time
		//3:- if no processes are arrived with smaller burst time, it will simply put the arrived processes in arrived queue

		boolean flag = false;

		Iterator i = ip.getNewQueue().iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			if(p.getArrivalTime()==current_time){

				if(p.getPriority()<current_process_priority){
					flag = true;
				}
				ip.insertIntoArrivedQueue(p);
			}
		}
		return flag;
	}

	public void updateDisplay(int currentTime){
		System.out.println("Time: " + currentTime + " Process Scheduled " + current_running_pid);
	}

	public int checkAllProcessForArrival(int currentTime){			
		//if some process has arrived,then this function will return the minimum burst time among all arrived process
		//but if no process is arrived then it will return 999
		int lowestBurstTime = 999;
		Iterator i = ip.getNewQueue().iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			if(p.getArrivalTime()==currentTime){
				if(p.getBurstTime()<lowestBurstTime){
					lowestBurstTime = p.getBurstTime();
				}
				ip.insertIntoArrivedQueue(p);
			}
		}
		return lowestBurstTime;
	}

	public void checkForProcessCompletion(int currentTime){
		if(current_running_process_time_left == 0){
			current_running_pid = -1;
			current_process_priority = 999;
			current_running_process_time_left = 999;
			currentRunningProcess.setCompletionTime(currentTime);
			ip.insertIntoCompletedQueue(currentRunningProcess);
			currentRunningProcess = null;
		}
	}
}

class Input
{
	SortedList newQueue;
	SortedList arrivedProcess;
	SortedList completedProcess;

	public Input(){
		this.newQueue = new SortedList(new SortByArrivalTime());
		this.arrivedProcess = new SortedList(new SortByPriority());
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
	int max_time = 20;
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
	int max_priority = 10;

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
			int priority = rand.nextInt(max_priority);
			Process p = new Process(pid,arrival_time,burst_time,priority);
			ip.insertIntoNewQueue(p);
			// ip.printAllProcess();
		}
	}
}

class Process
{
	int pid;
	int arrival_time;
	int burst_time;
	int priority;
	int completion_time = -1;
	int remaining_time;
	int turn_around_time = -1;
	int waiting_time = -1;

	public Process(int pid,int arrival_time,int burst_time,int priority){
		this.pid=pid;
		this.arrival_time=arrival_time;
		this.burst_time=burst_time;
		this.priority=priority; 
		this.remaining_time = burst_time;
	}
	public int getPid(){
		return this.pid;
	}
	public int getArrivalTime(){
		return this.arrival_time;
	}
	public int getBurstTime(){
		return this.burst_time;
	}
	public int getPriority(){
		return this.priority;
	}
	public int getRemainingTime(){
		return this.remaining_time;
	}
	public void setCompletionTime(int completion_time){
		this.completion_time = completion_time;
		computeProcessSummary();
	}
	private void computeProcessSummary(){
		this.turn_around_time = this.completion_time - this.arrival_time + 1;
		this.waiting_time = this.turn_around_time - this.burst_time;
	}
	public void decreamentRemainingTime(){
		this.remaining_time = this.remaining_time - 1;
	}
	public String toString(){
		return this.pid + "\t" + this.priority + "\t" + this.arrival_time + "\t"+this.burst_time + "\t" + this.completion_time + "\t" + this.turn_around_time +"\t" + this.waiting_time;
	}
}

class SortByPriority implements Comparator<Process>
{
	public int compare(Process p1, Process p2){
		return p1.getPriority() - p2.getPriority();
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
