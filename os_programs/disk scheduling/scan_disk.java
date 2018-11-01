//direction 1 implies left to right or increasing track no
//direction -1 implies right to left or decreasing track no
import java.util.*;
import java.lang.*;
import java.io.*;
// import org.apache.commons.lang.ArrayUtils;

class scan_disk
{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tape_start = 0;
		int tape_end = 199;

		int head = 22;
		int[] tracks = {1,2,52,36,45,90};
		int direction = 1;

		int no_tracks = tracks.length;

		int total_seek_length = 0;

		Arrays.sort(tracks);


		int initial_track_index = getInitialTrackIndex(tracks,head,direction);

		//seek from initial head to first track
		// total_seek_length += Math.abs(head-tracks[initial_track_index]);
		// System.out.println("Seek no 1 from "+head+" till "+tracks[initial_track_index]+"   Seek Length "+Math.abs(head-tracks[initial_track_index]));
		// removeTrack(tracks,initial_track_index);

		// int head_index = -1;

		// System.out.println(Arrays.toString(tracks)+"\ninitial index " + get_initial_track);


		int cache_seek = -1;

		int next_seek_index = initial_track_index;

		for(int i = 0; tracks.length>0;i++){
			

			int seek = Math.abs(head-tracks[next_seek_index]);
			total_seek_length += seek;

			// System.out.println("Seek no " + (i+1) + " from "+head+" till "+tracks[next_seek_index]+"   Seek Length "+seek);


			if(cache_seek!=-1){
				total_seek_length += cache_seek;
				System.out.println("Seek no " + (i+1) + " from "+head+" till "+tracks[next_seek_index]+"   Seek Length "+(seek+cache_seek));
				cache_seek = -1;
			}else{
				System.out.println("Seek no " + (i+1) + " from "+head+" till "+tracks[next_seek_index]+"   Seek Length "+seek);
			}

			head = tracks[next_seek_index];

			if(direction == -1){
				//right to left or decreasing
				tracks = removeElementByIndex(tracks,next_seek_index);

				next_seek_index = next_seek_index - 1;

				if(next_seek_index<0){
					//end reached

					direction = 1;

					cache_seek = head;

					head = tape_start;
					next_seek_index = 0;

				}
			}else{
				//left to right
				tracks = removeElementByIndex(tracks,next_seek_index);

				//no need to increament next_seek_index as we are removing an element
				//from array so it will be automatically adjusted 


				if(next_seek_index == tracks.length-1){
					//end reached

					direction = -1;

					cache_seek = tape_end - head;

					head = tape_end;

					next_seek_index = tracks.length-1;

				}
			}

			System.out.println(Arrays.toString(tracks));

		}
	}



	public static int getInitialTrackIndex(int[] tracks,int head,int direction){
		int low = 0;
		int high = tracks.length - 1;
		int mid = (low+high)/2;
		while(low<high){
			mid = (low+high)/2;
			if(head == tracks[mid]){
				return mid;
			}else if(head < tracks[mid]){
				high = mid - 1;
			}else if(head > tracks[mid]){
				low = mid + 1;
			}
		}

		if(direction == 1){
			return mid;
		}else{
			return mid - 1;
		}
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