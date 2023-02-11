package invoicewindow;

import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlyTabs extends MainPanel {

	private final JPanel centerPanel;
	public SearchFromProtokolTab searchProtokol;
	public SearchFromProformTab searchProform;
	
	public OnlyTabs() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1,1));

		JPanel choicePanel = new JPanel();
		choicePanel.setPreferredSize(new Dimension(this.WIDTH-20,
				(int)(this.HEIGHT * 0.07)));
		choicePanel.setLayout(new GridLayout(1,2));
		
		JButton protokolButton = new JButton("рзпяеме он опнрнйнк");
		protokolButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		protokolButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				CardLayout cl = (CardLayout)centerPanel.getLayout();
				JButton button = (JButton)arg0.getSource();
				cl.show(centerPanel,button.getText());
			}
			
		});
		
		
		JButton proformButton = new JButton("рзпяеме он опнтнплю");
		proformButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		proformButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				CardLayout cl = (CardLayout)centerPanel.getLayout();
				JButton button = (JButton)arg0.getSource();
				cl.show(centerPanel,button.getText());
			}
			
		});
		choicePanel.add(protokolButton);
		choicePanel.add(proformButton);
		
		
		
		northPanel.add(choicePanel);
		
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new CardLayout());
		centerPanel.setPreferredSize(new Dimension(this.WIDTH-20,this.HEIGHT-200));
		
	    searchProtokol = new SearchFromProtokolTab();
		
	    searchProform = new SearchFromProformTab();
		
		centerPanel.add(searchProtokol,protokolButton.getText());
		centerPanel.add(searchProform,proformButton.getText());
		
		this.setLayout(new BorderLayout());
		this.add(northPanel,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
		
	//	this.setBackground(Color.green);
		this.setPreferredSize(new Dimension(this.WIDTH-20,this.HEIGHT-150));
	}
}
