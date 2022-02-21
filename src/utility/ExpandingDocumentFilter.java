package utility;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ExpandingDocumentFilter extends DocumentFilter {
	   @Override
       public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
           System.out.println("I" + text);
           super.insertString(fb, offset, text, attr);
       }

       @Override
       public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
           if ("e".equalsIgnoreCase(text)) {
               text = "example";
           }
           super.replace(fb, offset, length, text, attrs); 
       }
}
