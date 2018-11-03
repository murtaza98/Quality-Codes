import java.lang.*;
import java.util.*;

class P12{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of vertices");
		int n=scan.nextInt();
		int[][] arr=new int[n][n];
		System.out.println("Enter the adjacency matrix");
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				arr[i][j]=scan.nextInt();
			}
		}
		prism(arr,n);
	}
	
	private static void printAns(int[] parent){
		for(int i=1;i<parent.length;i++){
			System.out.println(parent[i]+" - "+i);
		}
	}
	
	private static int minfromdist(int[] dist,boolean[] included){
		int min=999;
		int minposition=0;
		for(int i=0;i<dist.length;i++){
			if(included[i]==false&&dist[i]<min){
				min=dist[i];
				minposition=i;
			}
		}
		return minposition;
	}
	
	
	private static void prism(int[][] arr,int n){
		int[] parent=new int[n];
		int[] dist=new int[n];
		boolean[] included=new boolean[n];
		
		for(int i=0;i<n;i++){
			dist[i]=999;
			included[i]=false;
		}
		
		dist[0]=0;
		
		for(int i=0;i<n-1;i++){
			int u=minfromdist(dist,included);
			included[u]=true;
			for(int v=0;v<n;v++){
				if(included[v]==false&&arr[u][v]<dist[v]&&arr[u][v]!=0){
					dist[v]=arr[u][v];
					parent[v]=u;
				}
			}
		}

		printAns(parent);
 	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private static void bell(int[][] arr,int n){
		int[] parent =new int[n];
		int[] key =new int[n];
		Boolean[] included=new Boolean[n];
		
		for(int i=0;i<n;i++){
			key[i]=Integer.MAX_VALUE;
		}
		
		key[0]=0;
		parent[0]=-1;
		
		for(int i=0;i<n-1;i++){
			int u=selectminfromkey(key,included);
			included[u]=true;
			for(int v=0;v<n;v++){
				if(included[v]=false&&v!=u&&arr[u][v]<key[v]){
					parent[v]=u;
					key[v]=arr[u][v];
				}
			}
		}
		
 	}*/
}