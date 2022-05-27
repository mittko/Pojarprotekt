package invoiceservice;

import run.JustFrame;
import utility.MainPanel;

import javax.swing.*;

public class ServiceInvoiceFrame extends MainPanel {


	public ServiceArtikulTab acqTab;
	public ServiceInvoiceFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();

		acqTab = new ServiceArtikulTab();

		tabbedPane.add("”—À”√»", acqTab);
		this.add(tabbedPane);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ServiceInvoiceFrame inv3 = new ServiceInvoiceFrame();
               JustFrame f = new JustFrame();
               f.add(inv3);
               f.setFrameLocationOnTheCenter();
               f.pack();
			}

		});
	}

}
