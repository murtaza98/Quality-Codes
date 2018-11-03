import java.util.Scanner;
import java.lang.*;
import java.util.*;
 
public class P17
{

    static int numberofvertices;
    public static final int MAX_VALUE = 999;
 
    public static void AllPair(int adjacencymatrix[][])
    {
		for(int i=0;i<numberofvertices;i++){
			for(int j=0;j<numberofvertices;j++){
				for(int k=0;k<numberofvertices;k++){
					adjacencymatrix[i][j]=minno(adjacencymatrix[i][j],adjacencymatrix[i][k]+adjacencymatrix[k][j]);
				}
			}
		}
		
		
		
		
		for(int i=0;i<numberofvertices;i++){
			for(int j=0;j<numberofvertices;j++){
				System.out.print(adjacencymatrix[i][j]+" ");
			}
			System.out.println();
		}
    }
	
 
	private static int minno(int a,int b){
		if(a<b)
			return a;
		else
			return b;
	}
 
    public static void main(String... arg)
    {
        numberofvertices = 0;
        int source;
        Scanner scanner = new Scanner(System.in);
 
        System.out.println("Enter the number of vertices");
        numberofvertices = scanner.nextInt();
 
        int adjacencymatrix[][] = new int[numberofvertices + 1][numberofvertices + 1];
        System.out.println("Enter the adjacency matrix");
        for (int sourcenode = 0; sourcenode < numberofvertices; sourcenode++)
        {
            for (int destinationnode = 0; destinationnode < numberofvertices; destinationnode++)
            {
                adjacencymatrix[sourcenode][destinationnode] = scanner.nextInt();
				if (sourcenode == destinationnode)
                {
                    adjacencymatrix[sourcenode][destinationnode] = 0;
                    continue;
                }
                if (adjacencymatrix[sourcenode][destinationnode] == 0)
                {
                    adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
                }
            }
        }
		
		AllPair(adjacencymatrix);
    }
}