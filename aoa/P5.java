import java.lang.*;
import java.util.*;

class P5{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		quicksort(arr,0,arr.length-1);
		
		printarray(arr);
		
		
	}
	
	
	private static void printarray(int[] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	
	
	
	
	private static int partition(int[] arr,int low, int high){
		int i=low,j=high,pi=arr[low];
		while(i<j){
			while(arr[i]<=pi&&i<arr.length-2)
				i++;
			while(pi<arr[j]&&j>1)
				j--;
			if(i<j){
				int temp=arr[i];
				arr[i]=arr[j];
				arr[j]=temp;
			}
		}
		arr[low]=arr[j];arr[j]=pi;
		return j;
	}
	
	
	private static void quicksort(int[] arr,int p,int q){
		if(p<q){
			int m=partition(arr,p,q);
			quicksort(arr,p,m-1);
			quicksort(arr,m+1,q);
		}
	}
}