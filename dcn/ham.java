import java.lang.*;
import java.util.*;
import java.io.*;

class ham{
	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the code");
		String str=br.readLine();
		computeHamming(str);
	}
	private static void computeHamming(String code){
		int len=code.length();
		int r=computRedundentBits(len);
		System.out.println(""+r);
		int[] hamming=new int[len+r];
		int code_ptr=0;
		
		
		
		
		
		for(int i=0;i<hamming.length;i++){
			if(isPowerofTwo(i+1)){
				hamming[i]=-1;
			}else{
				hamming[i]=Integer.parseInt(""+code.charAt(code_ptr++));
			}
		}
		int shift=1;
		for(int i=0;i<hamming.length;i++){
			if(hamming[i]==-1){
				int no_of_ones=0;
				for(int j=i;j<hamming.length;j=j+shift*2){
					for(int k=j;k<j+shift;k++){
						if(hamming[k]==1)
							no_of_ones++;
					}
				}
				if((no_of_ones%2)==0)
					hamming[i]=0;
				else
					hamming[i]=1;
			}
			shift++;
		}
		System.out.println(""+Arrays.toString(hamming));
	}
	
	private static boolean isPowerofTwo(int x){
		return (x&(x-1))==0;
	}
	
	
	
	
	private static int computRedundentBits(int m){
		for(int r=1;;r++){
			if(Math.pow(2,r)>=m+r+1)
				return r;
		}
	}
}