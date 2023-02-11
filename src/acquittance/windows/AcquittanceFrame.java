package acquittance.windows;

import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;

public class AcquittanceFrame extends MainPanel {


	public ArtikulTab2 acqTab;

	public AcquittanceFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();

		acqTab = new ArtikulTab2();

		tabbedPane.add("¿–“» ”À»", acqTab);
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
