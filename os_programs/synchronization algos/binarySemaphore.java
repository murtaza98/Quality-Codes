import java.util.*;
import java.lang.*;
import java.io.*;

class Container{
	int total_size;
	int current_size;
	ArrayList<Integer> currentItems;
	
	
	Container(int size){
		this.total_size = size;
		this.current_size = 0;
		currentItems = new ArrayList();
	}
	
	public boolean add(int item){
		if(current_size != total_size){
			currentItems.add(item);
			current_size++;
			System.out.println("produced "+ item);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean remove(){
		if(!currentItems.isEmpty()){
			int consumed = currentItems.remove(0);
			current_size--;
			System.out.println("consumed " + consumed);
			return true;
		}else{
			return false;
		}
	}	
}

class Consumer extends Thread{
	
	Custom_Semaphore semaphore;
	Container container;
	
	public Consumer(Custom_Semaphore semaphore,Container container){
		this.semaphore = semaphore;
		this.container = container;
	}
	
	public void run(){
		for(int i = 0;i < 20;i++){
			semaphore.sem_wait();
			//CS start
			boolean flag = container.remove();
			if(!flag){
				i--;
			}
			//CS end
			semaphore.sem_signal();
		}
	}
}

class Producer extends Thread{
	
	Custom_Semaphore semaphore;
	Container container;
	
	public Producer(Custom_Semaphore semaphore,Container container){
		this.semaphore = semaphore;
		this.container = container;
	}
	
	public void run(){
		for(int i = 0;i < 20;i++){
			semaphore.sem_wait();
			//CS start
			boolean flag = container.add(i);
			if(!flag){
				i--;
			}
			//CS end
			semaphore.sem_signal();
		}
	}
}

class Custom_Semaphore{
	//true means occupied, false means not occupied 
	static boolean flag = false;
	
	public void sem_wait(){
		while(flag);
		this.flag = true;
		
	}
	
	public void sem_signal(){
		this.flag = false;
	}
	
}

class binarySemaphore
{
	
	public static void main(String[] args) throws Exception{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int container_size = 10;
		
		Custom_Semaphore semaphore = new Custom_Semaphore();
		Container container = new Container(container_size);
		
		Producer producer = new Producer(semaphore,container);
		Consumer consumer = new Consumer(semaphore,container);
		
		producer.start();
		consumer.start();
		
		
		
	}
}




