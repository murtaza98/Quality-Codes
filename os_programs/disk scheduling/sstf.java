import java.util.*;
import java.lang.*;
import java.io.*;

class sstf
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


		// int tape_start = 0;
		// int tape_end = 199;
		// int head = 51;
		// int[] tracks = {1,2,52,36,45,90};
		// int no_tracks = tracks.length;

		int total_seek_length = 0;

		System.out.println("Seek No\t\tStart\t\tEnd\t\tSeek Length");

		for(int i = 0; i < no_tracks;i++){

			int nextTrackIndex = getNearestTrackIndex(tracks,head);

			int seek = Math.abs(head-tracks[nextTrackIndex]);
			total_seek_length += seek;
			System.out.println((i+1) + "\t\t" + head + "\t\t" + tracks[nextTrackIndex] + "\t\t" + seek);
			
			head = tracks[nextTrackIndex];

			tracks = removeElementByIndex(tracks,nextTrackIndex);

		}

		System.out.println("\nTotal Seek Length " + total_seek_length + "\nAverage Seek Length " + ((float)total_seek_length/no_tracks));
	}


	public static int getNearestTrackIndex(int[] tracks,int head){
		int shorest_seek_index = -1;
		int shorest_seek = 9999;

		for(int i=0;i<tracks.length;i++){
			int seek = Math.abs(head-tracks[i]);
			if(seek<shorest_seek){
				shorest_seek = seek;
				shorest_seek_index = i;
			}
		}

		return shorest_seek_index;
	}

	public static int[] removeElementByIndex(int[] tracks,int removeIndex){
		int[] new_track = new int[tracks.length-1];

		for(int i = 0,new_track_pointer = 0;i<tracks.length;i++){
			if(i!=removeIndex){
				new_track[new_track_pointer++] = tracks[i];
			}
		}

		return new_track;

	}
}