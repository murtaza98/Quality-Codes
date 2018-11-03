import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;

class gbnS{
	public static void main(String[] args){
		try{
			ServerSocket ss=new ServerSocket(5000);
			Socket s=ss.accept();
			System.out.println("Server(Reciever)\n\n");
			
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			DataInputStream din=new DataInputStream(s.getInputStream());
			
			Thread.sleep(2000);
			dout.writeUTF("Hello Client\n");
			dout.flush();
			
			
			String str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 0 sent (Timer Started)");
			dout.flush();
			
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Ack 1 received");
			dout.flush();
			
			System.out.println();
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 1 sent (Timer Started)");
			dout.flush();
			
			Thread.sleep(2000);
			dout.writeUTF("Timeout Occurred\nFrame 1 resent (Timer Started)");
			dout.flush();
			//ack 0 sent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Ack 0 received");
			dout.flush();
			
			System.out.println();
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 0 sent (Timer Started)");
			dout.flush();
			//frame 0 received ack 1 sent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Timeout Occurred Ack not received\nFrame 0 resent (Timer Started)");
			dout.flush();
			
			//frame 0 discarded ack 1 sent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Ack 1 received");
			dout.flush();
			
		}catch(Exception e){
			System.exit(0);			
		}
	}
}
