import java.lang.*;
import java.util.*;

class P19{
	static int n;
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the number of elements in the array");
		n=scan.nextInt();
		
		int[][] board=new int[n][n];
		if(!solveNQ(board,0)){
			System.out.println("Solution doesnot exist");
		}
		
	}
	
	
	
	
	
	
	
	private static void printSoln(int[][] board){
		int n=board[0].length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	private static boolean checkSafe(int[][] board,int row,int col){
		/*for(int i=0;i<row;i++){
			if(board[i][col]==1)
				return false;
		}
		/*for(int i=row,j=col;i>0&&j>0;i--,j--){
			if(board[i][j]==1)
				return false;
		}
		for(int i=row,j=col;i>0&&col<board[0].length-2;i--,j++){
			if(board[i][j]==1)
				return false;
		}*/
		int n=board[0].length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(board[i][j]==1){
					if(j==col)
						return false;
					if((i-j)==(row-col))
						return false;
					if((i+j)==(row+col))
						return false;
				}
			}
		}
		return true;
	}
	
	
	private static boolean solveNQ(int[][] board,int row){
		if(row==n){
			printSoln(board);
			//System.exit(0);
			//return true;
		}else{
			boolean res=false;
			for(int i=0;i<n;i++){
				if(checkSafe(board,row,i)){
					board[row][i]=1;
					res=solveNQ(board,row+1)||res;
					
					board[row][i]=0;
				}
			}
			return res;
		}
	}
}