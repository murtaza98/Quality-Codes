import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

class gbnC{
	public static void main(String[] args){
		try{
			Socket s=new Socket("localhost",5000);
			System.out.println("Client(Sender)\n\n");
			
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			DataInputStream din=new DataInputStream(s.getInputStream());
			
			String str=(String)din.readUTF();
			System.out.println(str);
			
			
			
			Thread.currentThread().sleep(2000);

			dout.writeUTF("Hello Server\n");
			dout.flush();
			
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 0 received\nAck 1 sent");
			dout.flush();
			
			str=(String)din.readUTF();
			System.out.println(str);
			
			System.out.println();
			
			//timout
			str=(String)din.readUTF();
			System.out.println(str);
			//resent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 1 received\nAck 0 sent");
			dout.flush();
			//ack 0 received
			str=(String)din.readUTF();
			System.out.println(str);
			
			System.out.println();
			
			//frame 0 sent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 0 received\nAck 1 sent");
			dout.flush();
			//frame 0 resent
			str=(String)din.readUTF();
			System.out.println(str);
			
			Thread.sleep(2000);
			dout.writeUTF("Frame 0 received and frame is discarded\nAck 1 sent");
			dout.flush();
			//ack 1 received
			str=(String)din.readUTF();
			System.out.println(str);
			
			
			
			
		}catch(Exception e){
			System.exit(0);			
		}
	}
}