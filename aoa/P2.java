import java.lang.*;
import java.util.*;

class P2{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		int n=scan.nextInt();
		int[] arr=new int[n];
		System.out.println("Enter the array");
		for(int i=0;i<n;i++){
			arr[i]=scan.nextInt();
		}
		int[] result=new int[2];
		maxmin(arr,0,arr.length-1,result);

		System.out.println("The max in array is "+result[1]+" and the min is "+result[0]+".");
	}

	public static void maxmin(int[] arr,int p,int q,int[] result){
		if(p==q){
			result[0]=arr[p];
			result[1]=arr[p];
		}else if(p==q-1){
			if(arr[p]<arr[q]){
				result[0]=arr[p];
				result[1]=arr[q];
			}else{
				result[0]=arr[q];
				result[1]=arr[p];
			}
		}else{
			int mid=(p+q)/2;
			maxmin(arr,p,mid,result);
			int[] result1=new int[2];
			maxmin(arr,mid+1,q,result1);

			if(result1[0]<result[0]){
				result[0]=result1[0];
			}
			if(result[1]<result1[1]){
				result[1]=result1[1];
			}
		}
		return;
	}
}