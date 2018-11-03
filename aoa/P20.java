import java.lang.*;
import java.util.*;

class P20{
	static int[][] graph;
	static int n;
	static int noc;
	static int[] color;
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the graph");
		n=scan.nextInt();
		graph=new int[n][n];
		System.out.println("Enter the graph");
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				graph[i][j]=scan.nextInt();
			}
		}
		
		//System.out.println("Enter no if colors");
		//noc=scan.nextInt();
		
		for(int i=1;i<n;i++){
			noc=i;
			if(graphColor()==-1){
				continue;
			}else{
				System.out.println("Minimum chromatic number is "+i);
				break;
			}
		}
		
		graphColor();
	}
	
	private static int graphColor(){
		color=new int[n];
		try{
			solve(0);
			System.out.println("Not Possible");
			return -1;
		}catch(Exception e){
			System.out.println("Solution Exists");
			for(int i=0;i<n;i++){
				System.out.print(color[i]+" ");
			}
			System.out.println();
			return 1;
		}
	}
	
	private static void solve(int v) throws Exception{
		if(v==n){
			throw new Exception("Solution found");
		}
		for(int i=1;i<=noc;i++){
			if(isPossible(i,v)){
				color[v]=i;
				solve(v+1);
				color[v]=0;
			}
		}
	}
	
	private static boolean isPossible(int col,int vertex){
		for(int i=0;i<n;i++){
			if(graph[vertex][i]==1&&col==color[i]){
				return false;
			}
		}
		return true;
	}
	
	
}