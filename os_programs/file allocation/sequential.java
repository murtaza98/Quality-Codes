import java.util.*;
import java.lang.*;
import java.io.*;



class FileAllocTable{
	ArrayList<FileTableEntry> tableEntries;

	FileAllocTable(){
		tableEntries = new ArrayList();
	}

	public void addEntry(int file_no,int start){
		FileTableEntry entry = new FileTableEntry(file_no,start);
		tableEntries.add(entry);
	}

	public void removeEntry(int file_no){
		for(FileTableEntry entry : tableEntries){
			if(entry.getFileNo() == file_no){
				tableEntries.remove(entry);
				break;
			}
		}
	}

	public void printAllocTable(){
		System.out.println("File no\t\tStart Entry block");
		for(FileTableEntry entry:tableEntries){
			System.out.println(entry);
		}
	}
}

class FileTableEntry{
	int file_no;
	int start;
	
	FileTableEntry(int file_no,int start){
		this.file_no = file_no;
		this.start = start;
	}

	public int getFileNo(){
		return this.file_no;
	}

	public String toString(){
		return this.file_no + "\t\t" + this.start;
	}

}


class Disk{
	int[] disk;
	int disk_size;
	FileAllocTable allocationTable;


	Disk(int size){
		this.disk = new int[size];
		this.disk_size = size;
		allocationTable = new FileAllocTable();
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
					allocationTable.addEntry(file_no,prev_free);
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
		if(found){
			allocationTable.removeEntry(file_no);
		}
		return found;
	}

	public void printDisk(){
		System.out.println(Arrays.toString(disk));
	}

	public void printAllocTable(){
		allocationTable.printAllocTable();
	}

}


class sequential
{

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter disk size");
		int size = Integer.parseInt(br.readLine());
		Disk disk = new Disk(size);
		int file_no = 1;
		System.out.println("Disk structure");
		disk.printDisk();
		
		while(true){
			System.out.println("\nEnter Choice\n1:-Insert\n2:-Delete\n3:-Exit");
			int choice = Integer.parseInt(br.readLine());
			switch(choice){
				case 1:
					System.out.println("Enter the no of Bytes to store");
					int no_blocks = Integer.parseInt(br.readLine());
					boolean allocationSuccess = disk.allocateBlocks(no_blocks,file_no++);
					if(allocationSuccess){
						System.out.println("\nBlock allocation success");
						System.out.println("\nDisk structure");
						disk.printDisk();
						System.out.println("\nFile allocation table");
						disk.printAllocTable();
					}else{
						System.out.println("\nBlock allocation failed");
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
						System.out.println("\nFile Deleted");
						System.out.println("\nDisk structure");
						disk.printDisk();
						System.out.println("\nFile allocation table");
						disk.printAllocTable();
						disk.printDisk();
					}else{
						System.out.println("\nBlock allocation failed");
					}
					break;
				case 3:
					System.exit(0);
					break;
			}
		}		

	}
}