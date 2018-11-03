import java.lang.*;
import java.util.*;

class P3{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		System.out.println("Enter the element to be searched");
		int search=scan.nextInt();
		int found=-1;
		for(int i=0;i<arr.length;i++){
			if(arr[i]==search){
				found=i;
			}
		}
		if(found!=-1){
			System.out.println("Element found at position "+(found+1));
		}else{
			System.out.println("Element not found in array");
		}
	}
}