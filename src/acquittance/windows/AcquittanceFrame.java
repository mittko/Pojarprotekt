package acquittance.windows;

import invoicewindow.ProtokolAndProformSearchTabs;
import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;

public class AcquittanceFrame extends MainPanel {


	public GreyArtikulTab greyArtikulTab;

	public ProtokolAndProformSearchTabs protokolAndProformSearchTabs;

	public AcquittanceFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();

		greyArtikulTab = new GreyArtikulTab();
		protokolAndProformSearchTabs = new ProtokolAndProformSearchTabs(true,null);
		tabbedPane.add("œŒ∆¿–Œ√¿—»“≈À» » ¿–“» ”À»", protokolAndProformSearchTabs);
		tabbedPane.add("¿–“» ”À»", greyArtikulTab);
		this.add(tabbedPane);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AcquittanceFrame inv3 = new AcquittanceFrame();
				JustFrame f = new JustFrame();
				f.add(inv3);
				f.setFrameLocationOnTheCenter();
				f.pack();
			}

		});
	}

}
