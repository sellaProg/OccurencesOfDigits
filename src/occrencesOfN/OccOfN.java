package occrencesOfN;
import java.util.Scanner;;
public class OccOfN {

	public static void main(String[] args) {
		
		System.out.println("please write down a and b for the interval [a..b]");
		// First lets capture the interval that we want to look for occurrences of N in 
		
		Scanner scan =new Scanner(System.in);
	
		String input1=scan.next();
		String input2=scan.next();
		scan.close();
		//lets turn the inputs into integers 
		try {
			int a=Integer.valueOf(input1);
			int b=Integer.valueOf(input2);
			// lets create a list of the numbers we want to count their occurrences 
		
			int N[]= {3,6,9};
				
			//now  we need to figure out which 9999... representation fits a and b or in other words where we need to start counting 
				
			String repA=StartCountingFrom(a);
			String repB=StartCountingFrom(b);
				
			// Now we need to count how many occurrences we should start with
				
			int occurrencesAtRepA=CountOccurancesFrom(repA, N.length);
			int occurrencesAtRepB=CountOccurancesFrom(repB, N.length);
				
			//now we count occurrences of our given digits from 0 to a and from 0 to b 
				
			int a_occurrences=getOccurrences(a,repA,occurrencesAtRepA,N);
			int b_occurrences=getOccurrences(b,repB,occurrencesAtRepB,N);
			System.out.println(b_occurrences-a_occurrences+getOccurrencesAt(a,N));
		}
		catch(java.lang.NumberFormatException ex)
		{
			System.out.println("one of the entered values or both of them are not integers");
		}
		

	}

	private static int getOccurrences(int a, String repA, int occurrencesAtRepA, int[] N) {
		//Now we know exactly where to start from ! but we need to know if it is better to count from up or from bottom
		//lets check which distance is shorter (the shorter the better) 
		//this boolean returns true if counting from top to bottom is better and false otherwise
		boolean topToBottom=true;
		if(a>10)
			topToBottom=tTb_isbetter(a,repA);
		
		if(topToBottom)
		{
			int startFrom=Integer.valueOf(repA);
			
			for(int i=startFrom;i>a;i--)
			{
				occurrencesAtRepA-=getOccurrencesAt(i,N);
				
			}
			
		}
		else
		{
			occurrencesAtRepA=CountOccurancesFrom(repA.substring(0,1), N.length);
			//occurrencesAtRepA+=repA.substring(0,1).length();
			int startFrom=Integer.valueOf(repA.substring(0,1));
			for(int i=startFrom+1;i<=a;i++)
			{
				occurrencesAtRepA+=getOccurrencesAt(i,N);
				//System.out.println(i+", " +occurrencesAtRepA);
				
			}
		}
		return occurrencesAtRepA;
	}

	private static int getOccurrencesAt(int i, int[] N) {
		// get occurrences at a specific given number
		String nbr=Integer.toString(i);
		int occs=0;
		for(int j=0;j<nbr.length();j++)
		{
			if(checkExistance(nbr.substring(j, j+1),N))
				occs+=1;
		}
		
		return occs;
	}

	private static boolean checkExistance(String substring, int[] n) {
		// check existence of a specific digit in the list of digits that we are looking for 
		for(int i =0;i<n.length;i++)
			if(substring.equals(String.valueOf(n[i])))
				return true;
		return false;
	}

	private static boolean tTb_isbetter(int a, String repA) {
		// lets get the integer value of the 999... number first
		int rep1=Integer.valueOf(repA);
		int rep2=Integer.valueOf(repA.substring(0,repA.length()-1));
		
		//if rep1-a > a-rep2 then we should start counting occurrences from rep2 to a (bottom-up)
		//else from rep1 to a (up to bottom)
		if((rep1-a)>(a-rep2))
			return false;
		else
			return true;
	}

	private static int CountOccurancesFrom(String repA ,int N) {
		// this method is here to help us from what number of occurrences we should start
		// All what we do is to calculate the occurrences of this 9999... number using the equation:
		//(number of digit(s) we are searching for)*(length of this 9999... number)*10^(length of this 9999... number - 1) 
		return (int) (N*repA.length()*Math.pow(10,repA.length()-1));
	}

	private static String StartCountingFrom(int a) {
		// this method will tell us where to start counting from, it returns string to help us know how many occurrences later
		String startFrom="";
		//this variable to know how many digits in our number a
		int digits=Integer.toString(a).length();
		
		for(int i=0;i<digits;i++)
		{
			startFrom+="9";
		}
		
		return startFrom;
	}

}
