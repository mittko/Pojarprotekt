package workingbook;

import newextinguisher.workers.StickerPrinterWorker;
import utils.LoadIcon;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StickerJDialog extends MainPanel {

	private StickerButton TO_Button = null;
	private final StickerButton P_Button = null;
	private final StickerButton HI_Button = null;

	public StickerJDialog(JDialog parentDialog,boolean isTO,boolean isP,boolean isHI,
						  String nextDateTO, String nextDateP, String nextDateHI,
						  String barcod) {
		super();
		JPanel buttonPanel = new JPanel();

		if(isTO || isP || isHI) {
	    JPanel helpPanel1 = new JPanel();

	    helpPanel1.setLayout(new BorderLayout());
	    
	    JPanel labelPanel = new JPanel();
	    
	    JLabel TO_Label = new JLabel("");//("реумхвеяйн наяксфбюме"); //(MainPanel.рн);
	    
	    TO_Button = new StickerButton(parentDialog,nextDateTO, nextDateP, nextDateHI,
				isTO, isP, isHI, barcod,MainPanel.TO);
	    TO_Button.setActionCommand("button1");
	    TO_Button.setIcon(new LoadIcon().setIcons("Stiker2.jpg"));
	    int w = TO_Button.getIcon().getIconWidth();
	    int h = TO_Button.getIcon().getIconHeight();
	    TO_Button.setPreferredSize(new Dimension(w,h));
	    
	    labelPanel.add(TO_Label);
	    
	    JPanel buttonPanel1 = new JPanel();
	    buttonPanel1.add(TO_Button);
	    
	    helpPanel1.add(labelPanel,BorderLayout.NORTH);
	    helpPanel1.add(buttonPanel1,BorderLayout.CENTER);
	    
	    buttonPanel.add(helpPanel1);
	    }
	 
	    this.add(buttonPanel);
	    this.setLayout(new FlowLayout());

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     
     SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			JFrame jf = new JFrame();
			
			jf.setSize(800, 700);
			jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			jf.setVisible(true);
		}
    	  
      });
	}
	private boolean isButtonsEnabled() {
		// (TO_Button.isEnabled() || P_Button.isEnabled() || HI_Button.isEnabled()) {
		return TO_Button != null && TO_Button.isEnabled();
	}
	class StickerButton extends JButton implements ActionListener {
		Border defaultBorder = null;
		Border redBorder = null;
		JDialog parent;
		String barcod;
		String doing;
		private final String dateTO;
		private final String dateP;
		private final String dateHI;
		private final boolean isTo;
		private final boolean isP;
		private final boolean isHi;

		public StickerButton(JDialog parent,String dateTO, String dateP, String dateHI,
							 boolean isTo, boolean isP, boolean isHi,
							 String barcod,String doing) {
			this.parent = parent;
			this.dateTO = dateTO;
			this.dateP = dateP;
			this.dateHI = dateHI;
			this.isTo = isTo;
			this.isP = isP;
			this.isHi = isHi;
			this.barcod = barcod;
			this.doing = doing;
			defaultBorder = this.getBorder();
			redBorder = BorderFactory.createLineBorder(Color.red,5);
			this.addActionListener(this);
			this.setEnabled(true); // (isPrintable);
			this.setBorder(redBorder);
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setEnabled(false);
			setBorder(defaultBorder);
/*			String nextDate = null;
			if(!doing.equals(MainPanel.ух)) {
				nextDate = MyGetDate.getDateAfterToday(365);
			} else {
				nextDate = MyGetDate.getDateAfterToday(5 * 365);
			}*/
			StickerPrinterWorker sp = new StickerPrinterWorker(barcod, dateTO, dateP, dateHI, isTo,isP,isHi);
			sp.execute();
			if(!isButtonsEnabled()) {
				parent.dispose();
			}
		
		}
		
	}

}
