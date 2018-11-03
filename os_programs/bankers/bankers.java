import java.util.*;
import java.lang.*;
import java.io.*;

class bankers
{
	public static void main(String[] args) throws Exception{
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter no of process");
		int no_process = scan.nextInt();
		System.out.println("Enter no of resources");
		int no_resources = scan.nextInt();

		System.out.println("Enter the total resources");
		int[] resources = new int[no_resources];
		for(int i=0;i<no_resources;i++){
			resources[i] = scan.nextInt();
		}

		// int no_process = 3;
		// int no_resources = 3;
		// int[] resources = {8,5,4};

		int[][] claim = new int[no_process][no_resources];
		System.out.println("Enter the total claim matrix");
		for(int i=0;i<no_process;i++){
			for(int j=0;j<no_resources;j++){
				claim[i][j] = scan.nextInt();
				if(claim[i][j]>resources[j]){
					System.out.println("Invalid Input, Claim cannot be greater than resources");
					System.exit(0);
				}
			}
		}

		int[][] allocated = new int[no_process][no_resources];
		System.out.println("Enter the allocation claim matrix");
		for(int i=0;i<no_process;i++){
			for(int j=0;j<no_resources;j++){
				allocated[i][j] = scan.nextInt();
				if(allocated[i][j]>claim[i][j]){
					System.out.println("Invalid Input, Allocation cannot be greater than Claim");
				}
			}
		}

		// int[][] allocated = {{0,0,3},
		// 					{3,2,0},
		// 					{2,1,1}};

		// int[][] claim = {{8,4,3},
		// 				{6,2,0},
		// 				{3,3,3}};


		int[][] need = new int[no_process][no_resources];

		for(int i=0;i<no_process;i++){
			for(int j=0;j<no_resources;j++){
				need[i][j]=claim[i][j] - allocated[i][j];
			}
		}

		System.out.println("Need matrix is ");
		printMatrix(need);

		int[] available = new int[no_resources];

		for(int i=0;i<no_resources;i++){
			int no_allocated = 0;
			for(int j=0;j<no_process;j++){
				no_allocated += allocated[j][i];
			}
			available[i] = resources[i] - no_allocated;
		}


		ArrayList<Integer> order = new ArrayList();

		for(int i=0;i<no_process;i++){
			boolean possible = false;
			innerloop:
			for(int j=0;j<no_resources;j++){
				if(available[j]>=need[i][j]){
					if(j==no_resources-1){
						//the need can be satisfied by available resources

						//store this process in result
						order.add(i);

						//make need 0
						for(int k=0;k<no_resources;k++){
							need[i][k] = 999;
						}
						
						//free allocated resource
						for(int k=0;k<no_resources;k++){
							available[k] += allocated[i][k];
						}

						//reset the process
						i = -1;
						break innerloop;
					}
					continue innerloop;
				}else{
					break innerloop;
				}
			}
		}

		if(order.size()==no_process){
			System.out.println("The System is in SAFE STATE\nThe order in which the resources are allocated is as follows");
			for(int i:order){
				System.out.print(i+"\t");
			}

		}else{
			System.out.println("The System is NOT IN SAFE STATE\nThe process in deadlock are");
			for(int i=0;i<no_process;i++){
				if(need[i][0]!=999){
					System.out.print(i+"\t");
				}
			}

		}
	}

	public static void printMatrix(int[][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println("");
		}
		System.out.println();
	}
}