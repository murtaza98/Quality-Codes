import java.lang.*;
import java.net.*;

class socketR{
	public static void main(String[] args) throws Exception{
		DatagramSocket ds=new DatagramSocket(5000);
		byte[] b =new byte[1024];
		DatagramPacket dp=new DatagramPacket(b,1024);
		ds.receive(dp);
		String str="";
		for(int i=0;i<b.length-2;i++){
			if(b[i]==0&&b[i+1]==0&&b[i+2]==0){
				break;
			}else{
				str=str+(char)b[i];
			}
		}
		System.out.println(str);
	}
}