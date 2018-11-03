import java.lang.*;
import java.util.*;

class P6{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		
		Random r=new Random();
		int[] arr =new int[n];
		for (int i=0;i<n;i++){
			arr[i]=r.nextInt(i+1);
		}
		/*int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}*/
		
		long start=System.nanoTime();
		mergesort(arr,0,arr.length-1);
		long end=System.nanoTime();
		long elapsed=end-start;
		double seconds=(double)elapsed/1000000000.0;
		System.out.println("Time elapsed is"+seconds);
		//printarray(arr);
		
		
		
	}
	private static void printarray(int[] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	
	
	private static void merge(int[] arr,int low,int mid,int high){
		int lsize=mid-low+1;
		int hsize=high-mid;
		
		int[] L=new int[lsize];
		int[] H=new int[hsize];
		
		System.arraycopy(arr,low,L,0,lsize);
		System.arraycopy(arr,mid+1,H,0,hsize);
		
		int k=low,i=0,j=0;
		while(i<lsize&&j<hsize){
			if(L[i]<=H[j]){
				arr[k++]=L[i++];
			}else{
				arr[k++]=H[j++];
			}
		}
		if(i==lsize){
			while(j<hsize){
				arr[k++]=H[j++];
			}
		}
		
		if(j==hsize){
			while(i<lsize){
				arr[k++]=L[i++];
			}
		}
	}
	
	
	
	
	private static void mergesort(int[] arr,int low,int high){
		if(low<high){
			int mid=(low+high)/2;
			mergesort(arr,low,mid);
			mergesort(arr,mid+1,high);
			
			merge(arr,low,mid,high);
		}
	}
}