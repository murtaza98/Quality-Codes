import java.util.*;
import java.lang.*;
import java.io.*;


class Food{
	int food_count;
	public Food(int no_of_food){
		this.food_count = no_of_food;
	}
	public boolean isAvailable(){
		return this.food_count > 0 ? true : false;
	}
	public void eat(int philosopher_no){
		Random r = new Random();
		if(this.food_count>0){
			this.food_count --;
		}
		
		System.out.println("Philosoper no " + philosopher_no + " ate food " + this.food_count);

		// try{
		// 	Thread.sleep(r.nextInt(10));
		// }catch(Exception e){
		// 	System.out.println("Exception in Thread");
		// }

	}
}

class dining
{
	public static void main(String[] args) throws Exception{
		
		Food food = new Food(5);

		Fork f1 = new Fork(1);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(3);
		Fork f4 = new Fork(4);
		Fork f5 = new Fork(5);

		//first left and then right
		Philosoper p1 = new Philosoper(food,f1,f5,1);
		Philosoper p2 = new Philosoper(food,f2,f1,2);
		Philosoper p3 = new Philosoper(food,f3,f2,3);
		Philosoper p4 = new Philosoper(food,f4,f3,4);
		Philosoper p5 = new Philosoper(food,f5,f4,5);


		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();


	}
}

class Philosoper extends Thread {

	Food food;
	Fork left_fork;
	Fork right_fork;
	int philosopher_no;

	public Philosoper(Food food,Fork left,Fork right,int philosopher_no){
		this.food = food;
		this.left_fork = left;
		this.right_fork = right;
		this.philosopher_no = philosopher_no;
	}

	public void run(){

		Random r = new Random(); 
		
		while(food.isAvailable()){

			left_fork.sem_wait(this);
			System.out.println("Philosoper "+this.philosopher_no+" acquired left fork");

			try{
				Thread.sleep(r.nextInt(10));
			}catch(Exception e){
				System.out.println("Exception in Thread");
			}
			
			right_fork.sem_wait(this);
			System.out.println("Philosoper "+this.philosopher_no+" acquired right fork");

			food.eat(this.philosopher_no);

			left_fork.sem_signal();
			right_fork.sem_signal();

		}

	}
}

class Fork{

	boolean available = true;
	int fork_no;

	ArrayList<Philosoper> queue;

	public Fork(int fork_no){
		this.fork_no = fork_no;
		this.queue = new ArrayList();
	}

	public boolean isAvailable(){
		return this.available;
	}


	public void sem_wait(Philosoper philosopher){


		if(available){
			available = false;
		}else{
			//put the process in queue
			queue.add(philosopher);
			philosopher.suspend();
		}

		// while(!this.available){
		// 	// System.out.println("check by "+philosopher_no+" result "+this.available+" fork_no "+this.fork_no);
		// }
		// available = false;
		// // System.out.println("wait by " + philosopher_no);
	}

	public void sem_signal(){
		if(queue.isEmpty()){
			this.available = true;
		}else{
			Philosoper p = (Philosoper)this.queue.remove(0);
			p.resume();
		}
		// System.out.println("signal by " + philosopher_no);
	}
}














