import java.util.*;
import java.lang.*;
import java.io.*;

public class hrrn_dy
{
	public static void main(String[] args) throws Exception{
		Time time = new Time();
		Input ip = new Input(time);

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
	int current_running_process_time_left = -1;
	Process currentRunningProcess = null;

	public ExecutionThread(Input ip,Time time){
		this.ip = ip;
		this.time = time;
	}

	public void run(){
		while(time.isOver()){
			int current_time = time.getTime();
			checkAllProcessForArrival(current_time);
			if(current_running_pid == -1){
				//no process are currently scheduled

				Process processToSchedule = ip.getProcessForScheduling();			 //get the first element from queue
				if(processToSchedule!=null){
					currentRunningProcess = processToSchedule;
					current_running_pid = processToSchedule.getPid();
					current_running_process_time_left = processToSchedule.getBurstTime();
				}
			}else{
				//a process is scheduled so do nothing							
			}

			updateDisplay(current_time);

			current_running_process_time_left--;    //decreament current running process burst time left
			checkForProcessCompletion(current_time);

			// ip.printAllProcess();

			time.increamentTime();
		}
		ip.printAllProcessSummary();
	}

	public void updateDisplay(int currentTime){
		System.out.println("Time: " + currentTime + " Process Scheduled " + current_running_pid);
	}

	public void checkAllProcessForArrival(int currentTime){
		Iterator i = ip.getNewQueue().iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			if(p.getArrivalTime()==currentTime){
				ip.insertIntoArrivedQueue(p);
			}
		}
	}

	public void checkForProcessCompletion(int currentTime){
		if(current_running_process_time_left == 0){
			current_running_pid = -1;
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

	public Input(Time time){
		this.newQueue = new SortedList(new SortByArrivalTime());
		this.arrivedProcess = new SortedList(new SortByResponseRatio(time));
		this.completedProcess = new SortedList(new SortByResponseRatio(time));
	}

	public int getNoProcess(){
		return completedProcess.size();
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

	synchronized public void printAllProcess(){
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
		int sum_tat = 0;
		int sum_wt = 0;
		Iterator i = newQueue.iterator();
		System.out.println("ALL PROCESS");
		while(i.hasNext()){
			Process p =(Process)i.next();
			sum_tat += p.getTAT();
			sum_wt += p.getWT();
			System.out.println(p);
		}

		float average_tat = (float)sum_tat/this.getNoProcess();
		float average_wt = (float)sum_wt/this.getNoProcess();

		System.out.println("\nAverage TAT "+average_tat+"\nAverage WT "+average_wt);

	}
}

class Time
{
	int current_time = 0;
	int max_time = 20;
	public Time(){

	}

	public int getTime(){
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
	int max_process_to_schedule = 10;

	public InputThread(Input ip,Time time){
		this.ip = ip;
		this.time = time;
	}

	public void run(){
		Random rand = new Random();
		for(int i = 0;i<max_process_to_schedule;i++){
			int pid = i;
			int arrival_time = rand.nextInt(max_process_to_schedule) +  time.getTime();
			int burst_time = rand.nextInt(3) + 1;
			Process p = new Process(pid,arrival_time,burst_time);
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
	int completion_time;
	int tat = -1;
	int wt = -1;

	public Process(int pid,int arrival_time,int burst_time){
		this.pid=pid;
		this.arrival_time=arrival_time;
		this.burst_time=burst_time;
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
	public void setCompletionTime(int completion_time){
		this.completion_time = completion_time;
	}
	public int getTAT(){
		return this.tat;
	}
	public int getWT(){
		return this.wt;
	}
  public String toString(){
  	this.tat = this.completion_time-this.arrival_time;
  	this.wt = tat-this.burst_time;
    return "PID "+this.pid +" AT " + this.arrival_time + " BT "+this.burst_time+" TAT "+tat+" WT "+wt;
  }
}

class SortByResponseRatio implements Comparator<Process>
{

	Time time;
	public SortByResponseRatio(Time time){
		this.time = time;
	}


	public int compare(Process p1, Process p2){

		int current_time = time.getTime();
		int p1_wait_time = p1.getArrivalTime() - current_time;
		int p2_wait_time = p2.getArrivalTime() - current_time;
		int p1_burst_time = p1.getBurstTime();
		int p2_burst_time = p2.getBurstTime();

		float p1_ratio = (float)(p1_wait_time + p1_burst_time) / p1_burst_time;
		float p2_ratio = (float)(p2_wait_time + p2_burst_time) / p2_burst_time;

		if(p1_ratio<p2_ratio){
			return -1;
		}else if(p2_ratio<p1_ratio){
			return 1;
		}else{
			return 0;
		}

		// return p1.getArrivalTime() - p2.getArrivalTime();
	}
}

class SortByArrivalTime implements Comparator<Process>
{

	public SortByArrivalTime(){
	}


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
        Collections.sort(this, this.comparator);
        return result;
    }
}
