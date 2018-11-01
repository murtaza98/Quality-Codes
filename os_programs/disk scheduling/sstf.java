import java.util.*;
import java.lang.*;
import java.io.*;

class sstf
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

			int nextTrackIndex = getNearestTrackIndex(tracks,head);

			int seek = Math.abs(head-tracks[nextTrackIndex]);
			total_seek_length += seek;
			System.out.println("Seek no " + (i+1) + " from " + head + " till " + tracks[nextTrackIndex] + "   Seek Length " + seek);
			head = tracks[nextTrackIndex];

			removeTrack(tracks,nextTrackIndex);

		}

		System.out.println("Total Seek Length " + total_seek_length + "\nAverage Seek Length " + (total_seek_length/no_tracks));
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

	public static void removeTrack(int[] tracks,int removeIndex){
		System.arraycopy(tracks,removeIndex+1,tracks,removeIndex,tracks.length-1-removeIndex);
	}
}