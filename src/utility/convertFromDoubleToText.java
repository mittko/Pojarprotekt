package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class convertFromDoubleToText {

	private final String[][] LEVA = {

			{ "нула", "един", "два", "три", "четири", "пет", "шест", "седем",
					"осем", "девет" },
			{ "Ќула", "десет", "двадесет", "тридесет", "четиридесет",
					"петдесет", "шестдесет", "седемдесет", "осемдесет",
					"деведесет", "единадесет", "дванадесет", "тринадесет",
					"четиринадесет", "петнадесет", "шестнадесет",
					"седемнадесет", "осемнадесет", "деветнадесест" },
			{ "Ќула", "сто", "двеста", "триста", "четиристотин", "петстотин",
					"шестотин", "седемстотин", "осемстотин", "деветстотин" },
			{ "Ќула", "хил€да", "две хил€ди", "три хил€ди", "четири хил€ди",
					"пет хил€ди", "шест хил€ди", "седем хил€ди", "осем хил€ди",
					"девет хил€ди" },

	};
    private final String[][] STOTINKI = {
    	{ "нула", "една", "две", "три", "четири", "пет", "шест", "седем",
			"осем", "девет" },
	{ "Ќула", "десет", "двадесет", "тридесет", "четиридесет",
			"петдесет", "шестдесет", "седемдесет", "осемдесет",
			"деведесет", "единадесет", "дванадесет", "тринадесет",
			"четиринадесет", "петнадесет", "шестнадесет",
			"седемнадесет", "осемнадесет", "деветнадесест" }
    };
	/*public String convertToText(String sum) {
		
		String[] spl = sum.split("[,]");
		String head = spl[0];

		String result = new String();
		result += getHead(head,LEVA) + " лева ";
		// vzemi stotinki
	    String tail = spl[1];
	    if(!tail.equals("00")) {
	    result += " и " + getTail(tail,STOTINKI) + " стотинки";
	    }
		return result;
	}*/
    
	public String convertToText(String sum) {
		
		String[] spl = sum.split("[,]");// раздел€ левове и стотинки
		String head = spl[0]; // взима левове
		String tail = spl[1]; // взима стотинки
		
		String result = new String();
		
        if(head.length() <= 4) {
        	 String leva = getHead(head,LEVA);
        	 if(leva.equals("един")) {
        		 leva += " лев";
        	 } else {
        		 leva += " лева";
        	 }
        	 result += leva;
        } else if(head.length() == 5) {
        	String stotici = new String();
        	String hilqdi = new String();
        	stotici += getHead(head.substring(2,5), LEVA);
        	 if(stotici.equals("един")) {
        		 stotici += " лев";
        	 } else {
        		 stotici += " лева";
        	 }
     
        	hilqdi += getHead(head.substring(0, 2),LEVA);
        	
        	 if(hilqdi.contains("два")) {
    		   hilqdi = hilqdi.replaceAll("\\bдва\\b", "две");
    	      }
        	 if(hilqdi.contains("един")) {
        		 hilqdi = hilqdi.replaceAll("\\bедин\\b", "една");
        	 }
        	 
        	result += (hilqdi +  " хил€ди " + stotici);
        	
        } else if(head.length() == 6) {
        	String stotici = new String();
        	String hilqdi = new String();
        	stotici += getHead(head.substring(3,6), LEVA);
        	 if(stotici.equals("един")) {
        		 stotici += " лев";
        	 } else {
        		 stotici += " лева";
        	 }
        	 
        	hilqdi += getHead(head.substring(0, 3),LEVA);
        	
        	 if(hilqdi.contains("два")) {
      		   hilqdi = hilqdi.replaceAll("\\bдва\\b", "две");
      	      }
          	 if(hilqdi.contains("един")) {
          		 hilqdi = hilqdi.replaceAll("\\bедин\\b", "една");
          	 }
          	 
        	result += (hilqdi +  " хил€ди " + stotici);
        }
		
	    
		// vzemi stotinki
	    if(!tail.equals("00")) {
	    String stotinki = getTail(tail,STOTINKI);
	    result += " и " + stotinki + " стотинки";
	    } else {
	    result += " и нула стотинки";
	    }
		return result;
	}
    private String getTail(String text,String[][] slovom) {
    	return getHead(text,slovom);
    }
	private String getHead(String text,String[][] slovom) {
		// лева
		int digits = 0, pos = 0;
		boolean flag = false;
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < text.length(); i++) {
			digits = text.charAt(i) - 48;
			pos = text.length() - 1 - i;
			if (i == text.length() - 2) {
				if (text.charAt(i) - 48 == 1 && text.charAt(i + 1) - 48 != 0) {
					// get result here
					result.append(slovom[pos][text.charAt(i) - 48 + 9
							+ text.charAt(i + 1) - 48 - 1]+"#");
					flag = true;
				} else if (text.charAt(i + 1) - 48 == 0) {
					flag = true;
					// get result here
					result.append(slovom[pos][digits]+"#");
				} else {
					// get result here
					result.append(slovom[pos][digits]+"#");
				}
			} else {
				if (!flag) // get result here
					result.append(slovom[pos][digits]+"#");
			}
		}
		String[] s = result.toString().split("#");
		ArrayList<String> finalResult = new ArrayList<String>();
		// clear Ќулите
		for(int i = 0;i < s.length;i++) {
			if(!s[i].equals("Ќула")) {
				finalResult.add(s[i]);
			}
		}
		// analyse result
		Object[] fr = finalResult.toArray();
		
		StringBuilder sb = new StringBuilder();
		int index = fr.length-1;
		if(fr.length == 1) {
			sb.append(fr[index]);
		} else
			if(fr.length > 1) {
			sb.append(fr[index-1]);
			sb.append(" и ");
			sb.append(fr[index]);
		}  
		if(fr.length > 2) {
			sb.insert(0, fr[index-2]+" ");
		} 
		if(fr.length > 3) {
			sb.insert(0, fr[index-3]+" ");
		}
		return sb.toString();
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		convertFromDoubleToText convert = new convertFromDoubleToText();
	/*	double d = 10.00;
	    System.out.println(convert.convertToText(String.format("%2f", d)));
	    */
    	int i = 0;
		final Random rnd = new Random();

		  while(i++ < 50) { double ds = ((double)(rnd.nextInt(9999) + 1) /
		  (double)(rnd.nextInt(10) + 1));
		  String result = String.format("%.2f", ds);
		  System.out.println(result);
	  System.out.printf(" %s \n",convert.convertToText(String.format("%.2f",ds)));
		  }
		 
	}

}
