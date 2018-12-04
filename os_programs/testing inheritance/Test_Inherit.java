import java.util.*;
import java.lang.*;
import java.io.*;

class Elements{
	int parent_id;

	Elements(int parent_id){
		this.parent_id = parent_id;
	}

	public int getParentId(){
		return this.parent_id;
	}
}

class Producer extends Elements{
	int child_id;
	
	Producer(int parent_id,int child_id){
		super(parent_id);
		this.child_id = child_id;
	}

	public int getChildId(){
		return this.child_id;
	}
}

class Consumer extends Elements{
	int child_id;
	int consumer_unique;
	
	Consumer(int parent_id,int child_id){
		super(parent_id);
		this.child_id = child_id;
		this.consumer_unique = child_id + 1;
	}

	public int getChildId(){
		return this.child_id;
	}
}


class Test_Inherit
{
	public static void main(String[] args) throws Exception{
		Scanner scan = new Scanner(System.in);

		List<Elements> list = new ArrayList();

		Elements parent = new Elements(1);

		Producer child1 = new Producer(2,3);
		Consumer child2 = new Consumer(4,5);

		list.add(parent);
		list.add(child1);
		list.add(child2);

		for(Elements e : list){
			if(e instanceof Producer){
				Producer p = (Producer)e;
				System.out.println("Producer "+p.getChildId()+" "+p.getParentId());
			}else if(e instanceof Consumer){
				Consumer c = (Consumer)e;
				System.out.println("Consumer "+c.getChildId()+" "+c.getParentId()+" "+c.consumer_unique);
			}else{
				System.out.println("Parent "+e.getParentId());
			}

		}


	}
}