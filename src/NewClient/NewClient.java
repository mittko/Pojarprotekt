package NewClient;

import run.JustFrame;
import utility.LoadIcon;
import utility.MainPanel;
import utility.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewClient extends MainPanel {
	
	ImageIcon on = null;
	ImageIcon off = null;
	boolean on_off = false;
	AddFirm addFirm = null;
	AddPerson addPerson = null;
	
	public NewClient() {
		/* URL url = classLoader.getResource("Images/on.png");
		 on = new ImageIcon(url);
		 URL url2 = classLoader.getResource("Images/off.png");
		 off = new ImageIcon(url2);*/
	//	setBackground(Color.decode("0xA3BBC1"));
		
		addFirm = new AddFirm();
		addFirm.setOpaque(false);
		
		addPerson = new AddPerson();
		
		addPerson.setOpaque(false);
		
		JPanel panel = new JPanel();//GradientPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setBackground(panel.getBackground());//(Color.decode("0xDACB6D")); // DACB6D,F38B5D,FDC251

	    
		panel.setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(
				(int)(addFirm.getPreferredSize().getWidth()),
				(int)(this.HEIGHT * 0.1)));
		north.setOpaque(false);
		
		final JPanel center = new JPanel();
	    center.setOpaque(false);
	    
		final CardLayout card = new CardLayout();
	
		center.setLayout(card);
	
		
		final TooltipButton button = new TooltipButton("");

		button.setPreferredSize(new Dimension(
				(int)(north.getPreferredSize().getWidth() * 0.09),
				(int)(north.getPreferredSize().getHeight() * 0.82)));
		  button.setAutoSizedIcon(button, new LoadIcon().setIcons("off2.png"));
	
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				  card.next(center);
				  if(on_off) {
				//	  button.setIcon(new LoadIcon().setIcons("off2.png")) ;
					  button.setAutoSizedIcon(button, new LoadIcon().setIcons("off2.png"));
					
				  } else {
			//		  button.setIcon(new LoadIcon().setIcons("on2.png"));
					  button.setAutoSizedIcon(button, new LoadIcon().setIcons("on2.png"));
					
				  }
				  on_off = !on_off;
			}
			
		});
		north.add(button);
		center.add(addFirm);
		center.add(addPerson);
		panel.add(north,BorderLayout.NORTH);
		panel.add(center,BorderLayout.CENTER);
	
		this.add(panel);

		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
           JustFrame f = new JustFrame(new NewClient());
           f.pack();
	}
      public static boolean isOnlyDigits(String text) {
    	if(text.isEmpty()) {
    		text = "0";
    	}
    	String regex = "\\d+"; // [0-9]+
  		return text.matches(regex);
      }
     
}
