package invoice.invoicewindow;

import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;

public class InvoiceFrame extends MainPanel {

	public ProtokolAndProformSearchTabs invTab;
	public ArtikulTab acqTab;
	public InvoiceFrame(String protokolNumber) {
		JTabbedPane tabbedPane = new JTabbedPane();
        
		invTab = new ProtokolAndProformSearchTabs(false,protokolNumber);
		acqTab = new ArtikulTab();

		tabbedPane.add("œŒ∆¿–Œ√¿—»“≈À» » ¿–“» ”À»", invTab);
		tabbedPane.add("—¿ÃŒ ¿–“» ”À»", acqTab);
		this.add(tabbedPane);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				InvoiceFrame inv3 = new InvoiceFrame(null);
               JustFrame f = new JustFrame();
               f.add(inv3);
               f.setFrameLocationOnTheCenter();
               f.pack();
			}

		});
	}

}
