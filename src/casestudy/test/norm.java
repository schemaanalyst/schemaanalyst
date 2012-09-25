package casestudy.test;

public class norm {

	public static double n(double d) {
		return d / (d + 1);
	}
	
	public static void main(String[] args) {
		
		System.out.println(
		
				n(n(n(1 + 1)) + n(1 + 1))
				//n(n(n(1+1)+(2+1))+n(1+1))
		);
	}
	
}
