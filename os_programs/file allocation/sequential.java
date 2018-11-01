import java.util.*;
import java.lang.*;
import java.io.*;


class Disk{
	int[] disk;
	int disk_size;

	Disk(int size){
		this.disk = new int[size];
		this.disk_size = size;
	}

	public boolean allocateBlocks(int no_blocks,int file_no){
		//prev_free will point to first free block in a series of successive free blocks
		int prev_free = -1;
		for(int i=0;i<this.disk_size;i++){
			if(disk[i] == 0){
				if(prev_free == -1){
					//this is the first free block
					prev_free = i;
				}else{
					//this can be any free block after the first contigous block

				}
				//check if the no of free blocks discovered so far meet the required no
				if(no_blocks == (i-prev_free+1)){
					for(int j = prev_free;j<=i;j++){
						disk[j] = file_no;
					}
					return true;
				}
			}else{
				prev_free = -1;
			}
		}
		return false;
	}

	public boolean removeFile(int file_no){
		boolean found = false;
		for(int i = 0;i<this.disk_size;i++){
			if(file_no==disk[i]){
				disk[i] = 0;
				found = true;
			}
		}
		return found;
	}

	public void printDisk(){
		System.out.println(Arrays.toString(disk));
	}

}


class sequential
{

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int size = 16;
		Disk disk = new Disk(size);
		int file_no = 1;

		disk.printDisk();
		
		while(true){
			System.out.println("Enter Choice\n1:-Insert\n2:-Delete\n3:-Exit");
			int choice = Integer.parseInt(br.readLine());
			switch(choice){
				case 1:
					System.out.println("Enter the no of disk blocks");
					int no_blocks = Integer.parseInt(br.readLine());
					boolean allocationSuccess = disk.allocateBlocks(no_blocks,file_no++);
					if(allocationSuccess){
						System.out.println("Block allocation success");
						disk.printDisk();
					}else{
						System.out.println("Block allocation failed");
						//to reuse the file_no
						file_no--;
					}


					break;
				case 2:
					System.out.println("Enter the file no to be removed");
					int remove_file_no = Integer.parseInt(br.readLine());
					boolean removalStatus = disk.removeFile(remove_file_no);
					disk.printDisk();
					if(removalStatus){
						System.out.println("Block allocation success");
						disk.printDisk();
					}else{
						System.out.println("Block allocation failed");
					}
					break;
				case 3:
					System.exit(0);
					break;
			}
		}		

	}
}