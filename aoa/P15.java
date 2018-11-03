import java.util.*;
import java.io.*;

class P15{
	static int V;
	static int[][] g;
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter no of vertices");
		int n = scan.nextInt();
		V=n;
		g = new int[n][n];
		System.out.println("Enter the adjacency matrix");
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++){
				g[i][j]=scan.nextInt();
			}	
		}
		HashSet<Integer> hs=new HashSet();
		
		
		
		for(int i=1;i<n;i++){
			hs.add(i);
		}
		System.out.println("The mimimum cost is"+solve(hs,0));
	}
	private static int solve(HashSet<Integer> hset,int v){
		if(hset.isEmpty())
			return g[v][0];
		int min=9999,d;
		for(int i : hset){
			HashSet<Integer> auxset=new HashSet(hset);
			
			
			
			auxset.remove(i);
			d=g[v][i]+solve(auxset,i);
			if(d<min)
				min=d;
		}
		return min;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static int solve(HashSet<Integer> hset,int s){
		if(hset.isEmpty()){
			return g[s][0];
		}
		int mincost=9999,d;
		
		for(int i:hset){
			HashSet<Integer> auxset=new HashSet(hset);
			auxset.remove(i);
			d=g[s][i]+solve(auxset,i);
			if(d<mincost)
				mincost=d;
		}
		return mincost;
	}

}