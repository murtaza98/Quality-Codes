import java.util.*;
import java.lang.*;
import java.net.*;

class socket{
	public static void main(String[] args) throws Exception{
		DatagramSocket ds =new DatagramSocket();
		String str="Hello World!";
		InetAddress ip=InetAddress.getByName("127.0.0.1");
		DatagramPacket dp = new DatagramPacket(str.getBytes(),str.length(),ip,5000);
		ds.send(dp);
		ds.close();
	}
}