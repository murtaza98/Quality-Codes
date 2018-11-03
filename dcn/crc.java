import java.util.*;
import java.lang.*;
import java.io.*;

class crc{
	public static void main(String[] args )throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the crc");
		String s1=br.readLine();
		System.out.println("Enter the dividend");
		String s2=br.readLine();
		int[] input=new int[s1.length()+s2.length()-1];
		int[] dividend=new int[s2.length()];
		for(int i=0;i<s1.length();i++){
			input[i]=Integer.parseInt(s1.charAt(i)+"");
		}
		
		
		
		
		for(int i=0;i<s2.length();i++){
			dividend[i]=Integer.parseInt(s2.charAt(i)+"");
		}
		int[] crc=new int[s1.length()+dividend.length-1];
		calculatecrc(input,dividend,s1.length());
		
	}
	
	private static void calculatecrc(int[] input,int[] dividend,int dataLength){
		int[] copy=new int[input.length-dividend.length+1];
		System.arraycopy(input,0,copy,0,copy.length);
		for(int i=0;i<dataLength;i++){
			if(input[i]==1){
				for(int j=0;j<dividend.length;j++){
					input[i+j]^=dividend[j];
				}
			}
		}
		
		System.arraycopy(copy,0,input,0,copy.length);
		
		System.out.println(Arrays.toString(input));
	}
}