import java.util.*;
import java.lang.*;
import java.io.*;


class Block{
	int file_no;
	ArrayList<Integer> reference;

	Block(){
		file_no = 0;
		this.reference = new ArrayList();
	}

	public boolean isOccupied(){
		return file_no != 0 ? true : false;
	}

	public void setFileNo(int file_no){
		this.file_no = file_no;
	}

	public void resetReference(){
		this.reference.clear();
		this.reference = new ArrayList();
	}

	public void addReference(int reference_block_no){
		this.reference.add(reference_block_no);
	}

	public int getFileNo(){
		return this.file_no;
	}
}


class Disk{

	Block[] disk;
	int disk_size;
	int disk_free_space;

	Disk(int size){
		this.disk = new Block[size];
		for(int i = 0;i<size;i++){
			this.disk[i] = new Block();
		}
		this.disk_size = size;
		this.disk_free_space = size;
	}



	public boolean allocateBlocks(int no_blocks,int file_no){
		Block prev_block = null;
		if(this.disk_free_space < no_blocks){
			return false;
		}

		Block reference_block = null;

		for(int i = 0;i < disk_size && no_blocks > 0;i++){
			if(!disk[i].isOccupied()){
				disk[i].setFileNo(file_no);
				//make this as the reference block
				if(reference_block==null){
					reference_block = disk[i];
				}else{
					//reference block already defined, so put the current block reference in it
					reference_block.addReference(i);
				}
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
				disk[i].resetReference();
				disk[i].setFileNo(0);
				found = true;
				disk_free_space++;
			}
		}
		return found;
	}

	public void printDisk(){
		for(int i = 0;i<this.disk_size;i++){
			System.out.print(disk[i].getFileNo()+"  ");
		}
		System.out.println();
	}

}


class indexed
{

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int size = 16;
		Disk disk = new Disk(size);
		int file_no = 1;

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