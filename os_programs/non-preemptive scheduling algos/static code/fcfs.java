import java.util.*;
import java.lang.*;
import java.io.*;

class fcfs{

	static Vector<Process> process = new Vector<Process>();
	static Queue<Process> arrivedProcess = new LinkedList<>();
	static Queue<Process> completedProcess = new LinkedList<>(); 

	static int current_running_pid = -1;
	static int current_running_process_time_left = -1;
	static Process currentRunningProcess = null;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the no of process");
		int no = Integer.parseInt(br.readLine());
		for(int i=0;i<no;i++){
			String[] temp = br.readLine().split(" ");
			process.add(new Process(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2])));
		}

    	printAllProcess();
		
		for(int time=0;time<10;time++){
			checkAllProcessForArrival(time);
			if(current_running_pid == -1){
				//no process are currently scheduled

				Process processToSchedule = arrivedProcess.remove(); 			 //get the first element from queue
				currentRunningProcess = processToSchedule;
				current_running_pid = processToSchedule.getPid();
				current_running_process_time_left = processToSchedule.getBurstTime();


				
			}else{
				//a process is scheduled so do nothing							
			}

			updateDisplay(time);
			current_running_process_time_left--;    //decreament current running process burst time left
			checkForProcessCompletion(time);
		}
	}

	public static void checkForProcessCompletion(int currentTime){
		if(current_running_process_time_left == 0){
			current_running_pid = -1;
			currentRunningProcess.setCompletionTime(currentTime);
			completedProcess.add(currentRunningProcess);
			currentRunningProcess = null;
		}
	}

	public static void updateDisplay(int currentTime){
		System.out.println("Time: " + currentTime + " Process Scheduled " + current_running_pid);
	}

  public static void printAllProcess(){
    Iterator i = process.iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			System.out.println(p);
		}
  }

	public static void checkAllProcessForArrival(int currentTime){
		Iterator i = process.iterator();
		while(i.hasNext()){
			Process p =(Process)i.next();
			if(p.getArrivalTime()==currentTime){
				arrivedProcess.add(p);
			}
		}
	}
}

class Process{
	int pid;
	int arrival_time;
	int burst_time;
	int completion_time;

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
  public String toString(){
    return "PID "+this.pid +" AT " + this.arrival_time + " BT "+this.burst_time;
  }
}
