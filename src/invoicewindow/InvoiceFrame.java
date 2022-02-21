package invoicewindow;

import run.JustFrame;
import utility.MainPanel;

import javax.swing.*;

public class InvoiceFrame extends MainPanel {

	public OnlyTabs invTab;
	public ArtikulTab acqTab;
	public InvoiceFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();
        
		invTab = new OnlyTabs();
		acqTab = new ArtikulTab();

		tabbedPane.add("�������������� � ��������", invTab);
		tabbedPane.add("���� ��������", acqTab);
		this.add(tabbedPane);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				InvoiceFrame inv3 = new InvoiceFrame();
               JustFrame f = new JustFrame();
               f.add(inv3);
               f.setFrameLocationOnTheCenter();
               f.pack();
			}

		});
	}

}
