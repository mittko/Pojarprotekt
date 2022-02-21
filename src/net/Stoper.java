package net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Stoper extends JPanel {

	// private JButton stoper = null;
	protected static boolean isStoped = false;

	public Stoper(String ipAddress) {

		if (!isStoped) {
			batCreator(ipAddress);
			try {
				Runtime.getRuntime().exec("cmd /c start stop.bat");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isStoped = true;
			Starter.isRunning = false;
		} else {
			JOptionPane.showMessageDialog(null, "server already stoped!",
					"error", JOptionPane.ERROR_MESSAGE);
		//	System.out.println("server already stopped!");
		}
	}

	public static void batCreator(String ipAddress) {
		try {
			PrintStream ps = new PrintStream("stop.bat");
			ps.println("stopNetworkServer.bat -h " + ipAddress);
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-gene   
		new Stoper(GetCurrentIP.getIP());
	}

}
