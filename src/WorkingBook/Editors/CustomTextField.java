package WorkingBook.Editors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class CustomTextField  extends JTextField {

	public CustomTextField() {
		// TODO Auto-generated constructor stub
		 this.addMouseListener(new MAdapter());
		 this.addKeyListener(new KAdapter());
	}
	class KAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent ke) {
			// TODO Auto-generated method stub
			 if(isNotValidKey(ke.getKeyChar(), KeyEvent.VK_BACK_SPACE)) {
            	    ke.consume();
              }
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			// TODO Auto-generated method stub
			 if(isNotValidKey(ke.getKeyChar(), KeyEvent.VK_BACK_SPACE)) {
            	  ke.consume();
              }
		}

		@Override
		public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
			 if(isNotValidKey(ke.getKeyChar(), KeyEvent.VK_BACK_SPACE)) {
            	  ke.consume();
              }
		}
	}
	private boolean isNotValidKey(char key , int back_space) {
		 if(!Character.isDigit(key) &&
					key != back_space &&
					key != '.' && key != '.' && key != '.') {
     	         return true;
       }
		 return false;
	}
    class MAdapter extends MouseAdapter {
    	 public void mouseDragged(MouseEvent e) {
    		 int caret = 0;
    		 boolean flag = false;
     		for(int i = CustomTextField.this.getText().length()-1;i >= 0;i--) {
     			char ch = CustomTextField.this.getText().charAt(i);
     			if(Character.isDigit(ch)) {
     				caret = i;
     				flag = true;
     				break;
     			}
     		}
     		      if(flag) {
     		    	  caret++;
     		      }
     		 
             	  CustomTextField.this.setCaretPosition(caret);
    	 }
    	 public void mouseReleased(MouseEvent e) {
    		 int caret = 0;
    		 boolean flag = false;
     		for(int i = CustomTextField.this.getText().length()-1;i >= 0;i--) {
     			char ch = CustomTextField.this.getText().charAt(i);
     			if(Character.isDigit(ch)) {
     				caret = i;
     				flag = true;
     				break;
     			}
     		}
     		      if(flag) {
     		    	  caret++;
     		      }
             	  CustomTextField.this.setCaretPosition(caret);
    	 }
    	public void mousePressed(MouseEvent me) {
    		int caret = 0;
    		boolean flag = false;
    		for(int i = CustomTextField.this.getText().length()-1;i >= 0;i--) {
    			char ch = CustomTextField.this.getText().charAt(i);
    			if(Character.isDigit(ch)) {
    				caret = i;
    				flag = true;
    				break;
    			}
    		}
    		      if(flag) {
    		    	  caret++;
    		      }
            	  CustomTextField.this.setCaretPosition(caret);
    	}
    }
}
