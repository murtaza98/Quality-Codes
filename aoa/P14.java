import java.util.*;
import java.lang.*;

class P14
{
    public static void main(String args[])
    {
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the value of n");
		int n=scan.nextInt();
		int[] profit=new int[n];
		int[] weight=new int[n];
		for(int i=0;i<n;i++){
			System.out.println("Enter the profit and weight for "+(i+1)+" item");
			profit[i]=scan.nextInt();
			weight[i]=scan.nextInt();
		}
		System.out.println("Enter the maximum value of Knapsack");
		int W=scan.nextInt();
		int result = computeMaxProfit(W, weight, profit, n);
		
		System.out.println("The maximum profit is "+result);
    }
	
	public static int maxno(int a, int b) { 
		if (a<b)
			return b;
		else
			return a;
	}
	
	
	public static int computeMaxProfit(int W, int wt[], int val[], int n)
    {
		int[][] B =new int[n+1][W+1];
		
		for(int i=0;i<=n;i++){
			for(int w=0;w<=W;w++){
				if(i==0||w==0){
					B[i][w]=0;
				}else if(wt[i-1]<=w){
					B[i][w]=maxno(B[i-1][w],val[i-1]+B[i-1][w-wt[i-1]]);
				}else{
					B[i][w]=B[i-1][w];
				}
			}
		}
		
		return B[n][W];
		
		
    }
	
	
}