import java.util.*;
import java.lang.*;
import java.io.*;

class fcfs
{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter the tape start");
		int tape_start = Integer.parseInt(br.readLine());
		System.out.println("Enter the tape end");
		int tape_end = Integer.parseInt(br.readLine());
		
		System.out.println("Enter the starting position of head");
		int head = Integer.parseInt(br.readLine());

		System.out.println("Enter the no of read requests");
		int no_tracks = Integer.parseInt(br.readLine());

		int[] tracks = new int[no_tracks];

		System.out.println("Enter the track no's");
		for(int i=0;i<no_tracks;i++){
			tracks[i] = Integer.parseInt(br.readLine());
		}

		// int tape_start = Integer.parseInt(br.readLine());
		// int tape_start = 0;
		// int tape_end = 199;
		// int head = 51;
		// int[] tracks = {1,2,52,36,45,90};
		// int no_tracks = tracks.length;

		int total_seek_length = 0;

		System.out.println("Seek No\t\tStart\t\tEnd\t\tSeek Length");

		for(int i = 0; i < no_tracks;i++){
			int seek = Math.abs(head-tracks[i]);
			total_seek_length += seek;
			System.out.println((i+1) + "\t\t" + head + "\t\t" + tracks[i] + "\t\t" + seek);
			head = tracks[i];
		}

		System.out.println("Total Seek Length " + total_seek_length + "\nAverage Seek Length " + ((float)total_seek_length/no_tracks));
	}
}