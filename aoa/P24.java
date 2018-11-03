import java.lang.*;
import java.util.*;
import java.util.Arrays;

class P24{
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the pattern");
		String str=scan.next();
		int n=str.length();
		Set<String> set = new HashSet<String>(Arrays.asList(str.split("")));
		String[] unchr=set.toArray(new String[0]);
		System.out.println(Arrays.toString(unchr));
		int[][] pattern=new int[n+1][unchr.length()];

		findPattern(str,unchr,pattern);

	}

	public static void findPattern(String str,String[] unchr,int[][] pattern){

		int cachepatternpointer=0;
		char[] cachepattern=new char[str.length()+1);

		for(int i=0;i<unchr.length();i++){
			if(unchr[i]==str.charAt(0)){
				pattern[0][i]=1;
				break;
			}
		}

		
		cachepattern[0]=str.charAt(0);
		cachepatternpointer++;

		for(int i=1;i<str.length();i++){
			for(int j=0;j<unchr.length();j++){
				if(str.charAt(i)==unchr[j]){
					pattern[i,j]=i+1;
				}else{
					
				}
			}
		}
	}

	public static void printArray(int[][] pattern){
		for(int i=0;i<pattern.length();i++){
			for(int j=0;j<pattern[0].length();j++){
				System.out.print(pattern[i][j]+" ");
			}
			System.out.println();
		}
	}

}