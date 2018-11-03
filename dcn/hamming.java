import java.util.*;
import java.lang.*;
import java.io.*;


class hamming{
	static int[] code=new int[100];
	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the data to send");
		String s=br.readLine();
		String[] input =s.split("");
		//System.out.println(""+Arrays.toString(input));
		convertToHamming(input);
	}
	
	private static boolean isPowerofTwo(int n){
		return (n & (n-1))==0;		
	}
	
	private static int calculate_redundant_bit(int n){
		for(int r=1;;r++){
			if(Math.pow(2,r)>=n+r+1){
				return r;
			}
		}
	}
	
	private static void convertToHamming(String[] code){
		int n=code.length;
		int r=calculate_redundant_bit(n);
		System.out.println(""+r);
		int[] hcode=new int[n+r];
		int code_pointer=0;
		for(int i=0;i<n+r;i++){
			if(isPowerofTwo(i+1)){
				hcode[i]=-1;
			}else{
				hcode[i]=Integer.parseInt(code[code_pointer++]);
			}
		}
		fill_redundent_bits(hcode);
		
		printHamming(hcode);
	}
	
	private static void fill_redundent_bits(int[] code){
		int shift=1;
		//System.out.println(""+Arrays.toString(code));
		for(int i=0;i<code.length;i++){
			if(code[i]==-1){
				int no_of_ones=0;
				for(int j=i;j<code.length;j=j+shift*2){
					for(int k=j;k<j+shift;k++){
						if(code[k]==1){
							no_of_ones++;
						}
						//System.out.println("j "+j+" k "+k);
					}					
				}
				//System.out.println("i "+i+" no of ones "+no_of_ones);
				if(no_of_ones%2==0){
					code[i]=0;
				}else{
					code[i]=1;
				}
				shift++;
			}
		}
	}
	
	private static void printHamming(int[] code){
		
		System.out.println(""+Arrays.toString(code));
	}
}