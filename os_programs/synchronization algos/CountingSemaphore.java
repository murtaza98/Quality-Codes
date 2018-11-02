import java.util.*;
import java.lang.*;
import java.io.*;


class CountingSemaphore
{
	public static void main(String[] args) throws Exception{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int container_size = 10;
		
		Custom_Semaphore mutex = new Custom_Semaphore(1);
		Custom_Semaphore empty = new Custom_Semaphore(container_size);
		Custom_Semaphore full = new Custom_Semaphore(0);

		Container container = new Container(container_size);
		
		Producer producer = new Producer(mutex,empty,full,container);
		Consumer consumer = new Consumer(mutex,empty,full,container);
		
		producer.start();
		consumer.start();

	}
}


class Container{
	int total_size;
	int current_size;
	ArrayList<Integer> currentItems;
	
	
	public Container(int size){
		this.total_size = size;
		this.current_size = 0;
		currentItems = new ArrayList();
	}
	
	public boolean add(){
		current_size++;
		return true;
	}
	
	public boolean remove(){
		current_size--;
		return true;
	}	
}

class Consumer extends Thread
{
	Custom_Semaphore mutex;
	Custom_Semaphore empty;
	Custom_Semaphore full;

	Container container;
	
	public Consumer(Custom_Semaphore mutex,Custom_Semaphore empty,Custom_Semaphore full,Container container){
		this.mutex = mutex;
		this.empty = empty;
		this.full = full;
	}
	
	public void run(){
		for(int i = 0;i < 10;i++){
			full.sem_wait(this);
			mutex.sem_wait(this);
			//CS start
			System.out.println("Consumer consumed item "+i);
			//CS end
			mutex.sem_signal("Consumer");
			empty.sem_signal("Consumer");
		}
	}
}

class Producer extends Thread
{
	
	Custom_Semaphore mutex;
	Custom_Semaphore empty;
	Custom_Semaphore full;
	Container container;
	
	public Producer(Custom_Semaphore mutex,Custom_Semaphore empty,Custom_Semaphore full,Container container){
		this.mutex = mutex;
		this.empty = empty;
		this.full = full;
	}
	
	public void run(){
		for(int i = 0;i < 10;i++){
			empty.sem_wait(this);
			mutex.sem_wait(this);
			//CS start
			System.out.println("Producer produced item "+i);
			//CS end
			mutex.sem_signal("Producer");
			full.sem_signal("Producer");
		}
	}
}

class Custom_Semaphore{
	//true means occupied, false means not occupied 
	int count;
	String currentLock = "none";
	ArrayList<Producer> queueProducer = new ArrayList();
	ArrayList<Consumer> queueConsumer = new ArrayList();

	Custom_Semaphore(int capacity){
		this.count = capacity;
	}
	
	public void sem_wait(Consumer consumer){
		this.count--;
		if(this.count<0){
			queueConsumer.add(consumer);
			consumer.suspend();
		}		
	}

	public void sem_wait(Producer producer){
		this.count--;
		if(this.count<0){
			queueProducer.add(producer);
			producer.suspend();
		}
	}
	
	public void sem_signal(String caller){
		this.count++;
		if(this.count<=0){
			if(caller.equals("Producer")){
				Consumer consumer = queueConsumer.remove(0);
				consumer.resume();
			}else{
				Producer producer = queueProducer.remove(0);
				producer.resume();
			}
		}
	}	
}




