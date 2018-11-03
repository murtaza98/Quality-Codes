import java.util.*;
import java.io.*;

class salesman{

	static int g[][], n;

	static int solve(int s, HashSet<Integer> set){
		if(set.isEmpty() == true) return g[s][0];
		int mincost = Integer.MAX_VALUE, d = 0;
		for(int i : set){
			HashSet<Integer> auxset = new HashSet<Integer>(set);
			auxset.remove(i);
			d = g[s][i] + solve(i, auxset);
			if(d < mincost) mincost = d;
		} 
		return mincost;
	}	

	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		n = s.nextInt();
		g = new int[n][n];
		int e = s.nextInt();
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++) if(i != j) g[i][j] = Integer.MAX_VALUE;
		}
		for(int i = 0;i < e;i++){
			g[s.nextInt()][s.nextInt()] = s.nextInt();
		}
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i = 1;i < n;i++) set.add(i);
		int ans = solve(0, set);
		System.out.println(ans);
	}
}