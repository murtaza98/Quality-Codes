import java.lang.*;
import java.util.*;
import java.io.*;
import javax.xml.bind.DatatypeConverter;


class checksum{
	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the data to be transmitted");
		String data=br.readLine();
		//int[] check=new int[data.length()];
		
		String hexString=convert_to_Hex(data);
		
		System.out.println(hexString);
		
		String chksum=computeChecksum(hexString);
		
		System.out.println("checksum is"+chksum);
		
		String transmitted=hexString+chksum;
		
		
		System.out.println("Enter data at reciever side");
		String reciever=br.readLine();
		
		chksum=computeChecksum(reciever);

		
		try{
			if(Integer.parseInt(chksum)==0){
				System.out.println("No error detected");
			}else{
				System.out.println("Error detected");
			}
		}catch(Exception e){
			System.out.println("Error detected");
		}	
	}

	private static String computeChecksum(String data){
		int sum=0;
		for(int j=data.length(),i=j-4;i>=0&&j>0;j-=4){
			sum+=Integer.parseInt(data.substring(i,j),16);
			if(i>0&&i<4)
				i=0;
			else
				i-=4;
		}
		if(sum>0xffff){
			int temp=sum%0x10000; //sum without carry
			int carry=sum/0x10000;
			sum=temp+carry;
		}
		sum=~sum;
		sum-=0xffff0000;	//we want only last digit in the compliment
		
		String chksum=Integer.toHexString(sum);
		
		return chksum;
	}
	
	private static String convert_to_Hex(String msg){
		byte[] b=msg.getBytes("UTF-8");
		return DatatypeConverter.printHexBinary(b);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String convert_to_Hex(String msg) throws Exception{
		byte[] b=msg.getBytes("UTF-8");
		/*System.out.println("asd"+Arrays.toString(b));
		String str="";
		for(int i=0;i<b.length;i++){
			str=str+(char)b[i];
		}
		System.out.println("sss"+str);*/
		return DatatypeConverter.printHexBinary(b);
	}
	
	
}