import java.util.*;
import java.lang.*;
import java.io.*;
import javax.xml.bind.DatatypeConverter;

class check{
	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the code");
		String code=br.readLine();
		
		int sum=0;
		for(int j=code.length(),i=j-4;i>=0&&j>0;j-=4){
			sum+=Integer.parseInt(code.substring(i,j),16);
			if(i>0&&i<4)
				i=0;
			else
				i-=4;
		}
		
		if(sum>0xffff){
			int base=sum%0x10000;
			int carry=sum/0x10000;
			sum=base+carry;
		}
		sum=~sum;
		sum-=0xffff0000;
		
		String chksum=Integer.toHexString(sum);
		
		System.out.println(""+chksum);
	}
}