package generators;

import java.math.BigInteger;

public class NumGenerator {

	private static String completeNum(String num,int len) {
		StringBuilder sb = new StringBuilder();
		BigInteger n = null;
		try {
		n = new BigInteger(num);
		n = n.add(new BigInteger("1"));
		} catch(Exception ex) {
			System.out.println("very big integer exception....");
			
		}
		sb.append(n);
		while(sb.length() < len) {
			sb.insert(0, "0");
		}
		return sb.toString();
		
	}
	public static String updateNum(String num) {
		StringBuilder sb = new StringBuilder();
	    for(int i = 0;i < num.length();i++) {
	    	if(Character.isDigit(num.charAt(i)) && num.charAt(i) != '0') {
	    		while(i < num.length()) {
	    			sb.append(num.charAt(i));
	    			i++;
	    		}
	    	}
	    }
	    if(sb.length() == 0) {
	    	sb.append("0");
	    }
	    String complete = completeNum(sb.toString(),10);
		return complete;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  long start = System.currentTimeMillis();
      String num = "0000000033";
  
      num = updateNum(num);
      System.out.println(num);
     
      long end = System.currentTimeMillis();
      System.out.println((end - start) / 1000.0);
	}

}
