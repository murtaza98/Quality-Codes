import java.lang.*;
import java.util.*;

class P7{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		sort(arr,arr.length-1);
		printarray(arr);
		
		
	}
	
	private static void printarray(int[] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	
	
	
	private static void sort(int[] arr,int n){
		
		for(int i=1;i<n;i++){
			int k=arr[i];
			int j=i-1;
			while(j>=0&&arr[j]>k){
				arr[j+1]=arr[j];
				j--;
			}
			arr[j+1]=k;
		}
	}
}