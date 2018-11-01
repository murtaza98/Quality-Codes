//direction 1 implies left to right or increasing track no
//direction -1 implies right to left or decreasing track no
import java.util.*;
import java.lang.*;
import java.io.*;

class cscan
{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tape_start = 0;
		int tape_end = 199;
		int[] tape = new int[tape_end-tape_start+1];

		int head = 22;
		int[] tracks = {1,2,52,36,45,199};
		int direction = 1;

		//mark tracks on the tape
		for(int i:tracks){
			tape[i] = 1;
		}

		int no_tracks_left = tracks.length;

		int total_seek_length = 0;

		int prev_head=head;

		int seek_buffer = 0;

		for(int seek_no = 1;no_tracks_left>0;head+=direction){
			if(tape[head] == 1){
				tape[head] = 0;
				System.out.println("Seek "+seek_no+ " from "+prev_head+" upto "+head + "\tSeek length "+seek_buffer);
				prev_head = head;
				seek_buffer = 0;

				seek_no++;
				no_tracks_left--;
			}

			if(direction == 1 && head == tape_end){
				head = tape_start;
			}else if(direction == -1 && head == tape_start){
				head = tape_end;
			}

			seek_buffer++;
		}		
	}
}