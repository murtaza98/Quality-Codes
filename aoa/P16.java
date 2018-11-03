import java.util.Scanner;
import java.lang.*;
import java.util.*;
 
public class P16
{

    static int numberofvertices;
    public static final int MAX_VALUE = 999;
 
    public static void BellmanFordEvaluation(int source, int adjacencymatrix[][])
    {
		System.out.println("noafd"+numberofvertices);
        int[] distances=new int[numberofvertices];
		for(int i=0;i<numberofvertices;i++){
			distances[i]=MAX_VALUE;
		}
		System.out.println(""+Arrays.toString(distances));
		
		System.out.println("n"+source);
		distances[source]=0;
		
		for(int node=0;node<numberofvertices-1;node++){
			for(int sourcenode=0;sourcenode<numberofvertices;sourcenode++){
				for(int destinationnode=0;destinationnode<numberofvertices;destinationnode++){
					if(distances[destinationnode]>distances[sourcenode]+adjacencymatrix[sourcenode][destinationnode]){
						distances[destinationnode]=distances[sourcenode]+adjacencymatrix[sourcenode][destinationnode];
					}
				}
			}
		}
		
		
		for(int i=1;i<numberofvertices;i++){
			System.out.println(" "+(i+1)+"  "+distances[i]);
		}
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
		
        System.out.println("Enter the source vertex");
        source = scanner.nextInt();
        
		BellmanFordEvaluation(0,adjacencymatrix);
    }
}