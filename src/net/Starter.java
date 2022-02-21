package net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Starter extends JPanel {

	// private JButton starter = null;
	protected static boolean isRunning = false;

	public Starter(String ipAddress) {

		if (!isRunning) {
			batCreator(ipAddress);
			try {
				Runtime.getRuntime().exec("cmd /c start.bat"); // without start cmd /c start start.bat
																// -> in
																// background
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isRunning = true;
			Stoper.isStoped = false;
		} else {
			JOptionPane.showMessageDialog(null, "server already started!",
					"error", JOptionPane.ERROR_MESSAGE);
		//	System.out.println("server already startet!");
		}
	}

	public static void batCreator(String ipAddress) {
		try {
			PrintStream ps = new PrintStream("start.bat");
			ps.println("startNetworkServer.bat -h " + ipAddress);
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
		// TODO Auto-generated method stub
      new Starter(GetCurrentIP.getIP());
	}

}
