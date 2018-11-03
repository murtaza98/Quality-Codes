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


class Block{
	int file_no;
	Block next_block_reference;

	Block(){
		file_no = 0;
		next_block_reference = null;
	}

	public boolean isOccupied(){
		return file_no != 0 ? true : false;
	}

	public void setFileNo(int file_no){
		this.file_no = file_no;
	}

	public void setNextBlockReference(Block reference){
		this.next_block_reference = reference;
	}

	public int getFileNo(){
		return this.file_no;
	}
}


class Disk{

	Block[] disk;
	int disk_size;
	int disk_free_space;
	FileAllocTable allocationTable;

	Disk(int size){
		this.disk = new Block[size];
		for(int i = 0;i<size;i++){
			this.disk[i] = new Block();
		}
		this.disk_size = size;
		this.disk_free_space = size;
		allocationTable = new FileAllocTable();
	}



	public boolean allocateBlocks(int no_blocks,int file_no){
		Block prev_block = null;
		if(this.disk_free_space < no_blocks){
			return false;
		}
		for(int i = 0;i < disk_size && no_blocks > 0;i++){
			if(!disk[i].isOccupied()){
				disk[i].setFileNo(file_no);
				if(prev_block!=null){
					prev_block.setNextBlockReference(disk[i]);
				}else{
					//this is the first block so update the allocation table
					allocationTable.addEntry(file_no,i);
				}
				prev_block = disk[i];
				no_blocks--;
				disk_free_space--;
			}
		}
		return true;
	}

	public boolean removeFile(int file_no){
		boolean found = false;
		for(int i = 0;i<this.disk_size;i++){
			if(file_no==disk[i].getFileNo()){
				disk[i].setNextBlockReference(null);
				disk[i].setFileNo(0);
				found = true;
				disk_free_space++;
			}
		}
		if(found){
			//remove entry of the file from allocation table
			allocationTable.removeEntry(file_no);
		}
		return found;
	}

	public void printDisk(){
		for(int i = 0;i<this.disk_size;i++){
			System.out.print(disk[i].getFileNo()+"  ");
		}
		System.out.println();
	}

	public void printAllocTable(){
		allocationTable.printAllocTable();
	}

}


class linkedList
{

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int size = 16;
		Disk disk = new Disk(size);
		int file_no = 1;
		System.out.println("Disk structure");
		disk.printDisk();
		
		while(true){
			System.out.println("\nEnter Choice\n1:-Insert\n2:-Delete\n3:-Exit");
			int choice = Integer.parseInt(br.readLine());
			switch(choice){
				case 1:
					System.out.println("Enter the no of disk blocks");
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