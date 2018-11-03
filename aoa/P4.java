import java.lang.*;
import java.util.*;

class P4{
	static int[] arr;
	static int element;
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		System.out.println("Enter element to be searched");
		element=scan.nextInt();
		bsearch(0,arr.length);
		
	}
	public static void bsearch(int s,int e){
		if(s<e){
			int mid=(s+e)/2;
			if(arr[mid]==element){
				System.out.println("Element found at position "+(mid+1));
			}else if(arr[mid]<element){
				bsearch(mid+1,e);
			}else{
				bsearch(s,mid-1);
			}
		}else{
			System.out.println("Element not found in array");
		}
	}
}