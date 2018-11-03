import java.lang.*;
import java.util.*;

class P1{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		int max=arr[0];
		int min=arr[0];
		for(int i=1;i<n;i++){
			if(arr[i]<min){
				min=arr[i];
			}else if(max<arr[i]){
				max=arr[i];
			}
		}
		System.out.println("The max in array is "+max+" and the min is "+min+".");
	}
}