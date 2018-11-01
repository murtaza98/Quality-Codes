import java.util.*;
import java.lang.*;
import java.io.*;

class fcfs
{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tape_start = 0;
		int tape_end = 199;

		int head = 51;
		int[] tracks = {1,2,52,36,45,90};

		int no_tracks = tracks.length;

		int total_seek_length = 0;

		for(int i = 0; i < no_tracks;i++){
			int seek = Math.abs(head-tracks[i]);
			total_seek_length += seek;
			System.out.println("Seek no " + (i+1) + " from "+head+" till "+tracks[i]+"   Seek Length "+seek);
			head = tracks[i];
		}

		System.out.println("Total Seek Length " + total_seek_length + "\nAverage Seek Length " + (total_seek_length/no_tracks));
 

	}
}