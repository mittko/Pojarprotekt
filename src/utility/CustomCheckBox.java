package utility;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomCheckBox extends JCheckBox {
	ImageIcon pressedIcon;// Icon pressedIcon
    ImageIcon noIcon; // Icon noIcon
    ImageIcon yesIcon; // Icon yesIcon
    boolean pressed;
    
	public CustomCheckBox() {
		
	   noIcon = getAutoSizedIcon(this,MainPanel.noImage);
		
		yesIcon = getAutoSizedIcon(this,MainPanel.yesImage);
	
		this.setIcon(noIcon);
		this.addActionListener(new ActionListener() {
          
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			   tictac();
			}
			
		});
	}
	void tictac() {
		if(!pressed) {
			pressedIcon = yesIcon;
			pressed = true;
		} else {
			pressedIcon = noIcon;
			pressed = false;
		}
		this.setIcon(pressedIcon);
	}
	public boolean getPressed() {
		return this.pressed;
	}
	
	public ImageIcon getAutoSizedIcon(CustomCheckBox checkBox, String pathToImage)  {
		 URL url = null;
		 try {
		 url = getClass().getClassLoader().getResource("Images/"+pathToImage);
		 } finally {
			// setJavaIcon(image.substring(0,image.indexOf(".")));
			 if(url == null)
			 JOptionPane.showMessageDialog(null, "Грешка при зареждане на изображенията!","Error",JOptionPane.ERROR_MESSAGE);
		     
		 }
        ImageIcon testIcon = new ImageIcon(url);
   		Image img = testIcon.getImage() ;  
   		   Image newimg = img.getScaledInstance( 
   				   checkBox.getPreferredSize().width, 
   				   checkBox.getPreferredSize().height,  java.awt.Image.SCALE_SMOOTH ) ;
		return new ImageIcon( newimg );
   	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         JFrame jf = new JFrame();
         jf.add(new CustomCheckBox());
         jf.pack();
         jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jf.setVisible(true);
	}

}
