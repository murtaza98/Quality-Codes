import java.lang.*;
import java.util.*;

class P13{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of vertices");
		int n=scan.nextInt();
		int[][] arr=new int[n][n];
		System.out.println("Enter the adjacency matrix");
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				arr[i][j]=scan.nextInt();
				if(arr[i][j]==0){
					arr[i][j]=9999;
				}
			}		
		}
		dji(arr,0,n);
	}
	
	private static int minfromdist(int[] dist,Boolean[] included){
		int min=9999;
		int minposition=0;
		for(int i=0;i<dist.length;i++){
			if(included[i]==false&&dist[i]<min){
				min=dist[i];
				minposition=i;
			}
		}
		return minposition;
	}
	
	
	private static void dji(int[][] graph,int s,int n){
		int[] dist=new int[n];
		Boolean[] included=new Boolean[n];
		for(int i=0;i<n;i++){
			included[i]=false;
			dist[i]=9999;
		}
		dist[s]=0;
		for(int i=0;i<n-1;i++){
			int u=minfromdist(dist,included);
			
			included[u]=true;
			
			for(int v=0;v<n;v++){
				if(included[v]==false&&dist[v]>(dist[u]+graph[u][v])){
					dist[v]=dist[u]+graph[u][v];
				}
			}
			System.out.println("u"+u+Arrays.toString(dist));
		}
		for(int i=1;i<n;i++){
			System.out.println("vertex "+(i+1)+" cost "+dist[i]);
		}
		
	}
}
	