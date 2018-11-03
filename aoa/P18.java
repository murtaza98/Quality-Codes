import java.lang.*;
import java.util.*;

class P18{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		
		System.out.println("minimum number of computations required is "+matrix(arr,1,n-1));
		
	}
	
	
	
	private static int matrix(int[] arr,int i,int j){
		
		if(i==j)
			return 0;
		else{
			int min=99999;
			for(int k=i;k<j;k++){
				int d=matrix(arr,i,k)+matrix(arr,k+1,j)+arr[i-1]*arr[k]*arr[j];
				if(d<min)
					min=d;
			}
			return min;
		}
		
		
	}
}