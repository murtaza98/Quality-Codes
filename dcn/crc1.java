import java.lang.*;
import java.util.*;
import java.io.*;

class crc1{
	public static void main(String args[]) throws Exception{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the code");
		String s1=br.readLine();
		System.out.println("Enter the divisor");
		String s2=br.readLine();
		int[] crc=new int[s1.length()+s2.length()-1];
		int[] divisor=new int[s2.length()];
		
		for(int i=0;i<s1.length();i++){
			crc[i]=Integer.parseInt(""+s1.charAt(i));
		}
		
		
		
		for(int i=0;i<divisor.length;i++){
			divisor[i]=Integer.parseInt(""+s2.charAt(i));
		}
		
		for(int i=0;i<s1.length();i++){
			if(crc[i]==1){
				for(int j=0;j<divisor.length;j++){
					crc[i+j]^=divisor[j];
				}
				//System.out.println(""+Arrays.toString(crc));
			}
		}
		
		for(int i=0;i<s1.length();i++){
			crc[i]=Integer.parseInt(""+s1.charAt(i));
		}
		
		System.out.println(""+Arrays.toString(crc));
		
		
		System.out.println("Enter the msg received at receiver end");
		
		String str=br.readLine();
		
		if(str.length()==crc.length){
			int[] rcrc=new int[str.length()];
		
			for(int i=0;i<str.length();i++){
				rcrc[i]=Integer.parseInt(""+str.charAt(i));
			}
			
			for(int i=0;i<s1.length();i++){
				if(rcrc[i]==1){
					for(int j=0;j<divisor.length;j++){
						rcrc[i+j]^=divisor[j];
					}
				}
				//System.out.println(""+Arrays.toString(rcrc));
			}
			boolean check=true;
			for(int i=0;i<rcrc.length;i++){
				if(rcrc[i]==1){
					check=false;
					break;
				}
			}
			
			if(check)
				System.out.println("No error detected");
			else
				System.out.println("Error detected");
		}else
			System.out.println("Error Detected");
		
		
		
	}
}