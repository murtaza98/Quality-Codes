//direction 1 implies left to right or increasing track no
//direction -1 implies right to left or decreasing track no
import java.util.*;
import java.lang.*;
import java.io.*;

class cscan
{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		

		System.out.println("Enter the tape start");
		int tape_start = Integer.parseInt(br.readLine());
		System.out.println("Enter the tape end");
		int tape_end = Integer.parseInt(br.readLine());

		int[] tape = new int[tape_end-tape_start+1];
		
		System.out.println("Enter the starting position of head");
		int head = Integer.parseInt(br.readLine());

		System.out.println("Enter the no of read requests");
		int no_tracks = Integer.parseInt(br.readLine());

		int[] tracks = new int[no_tracks];

		System.out.println("Enter the track no's");
		for(int i=0;i<no_tracks;i++){
			tracks[i] = Integer.parseInt(br.readLine());
		}

		System.out.println("Enter the initial direction\n1 --- left to right\n-1 --- right to left");
		int direction = Integer.parseInt(br.readLine());

		// int tape_start = 0;
		// int tape_end = 199;
		// int[] tape = new int[tape_end-tape_start+1];
		// int head = 22;
		// int[] tracks = {1,2,52,36,45,199};
		// int direction = 1;

		//mark tracks on the tape
		for(int i:tracks){
			tape[i] = 1;
		}
		int no_tracks_left = tracks.length;
		int total_seek_length = 0;
		int prev_head=head;
		int seek_buffer = 0;

		System.out.println("Seek No\t\tStart\t\tEnd\t\tSeek Length");

		for(int seek_no = 1;no_tracks_left>0;head+=direction){
			if(tape[head] == 1){
				tape[head] = 0;
				System.out.println((seek_no) + "\t\t" + prev_head + "\t\t" + head + "\t\t" + seek_buffer);
				total_seek_length+=seek_buffer;
				prev_head = head;
				seek_buffer = 0;

				seek_no++;
				no_tracks_left--;
			}

			if(direction == 1 && head == tape_end){
				seek_buffer+=(tape_end-tape_start+1);
				head = tape_start;
			}else if(direction == -1 && head == tape_start){
				seek_buffer+=(tape_end-tape_start+1);
				head = tape_end;
			}

			seek_buffer++;
		}
		System.out.println("Total Seek Length " + total_seek_length + "\nAverage Seek Length " + ((float)total_seek_length/no_tracks));	
	}
}