package PDF;
import java.awt.Desktop;
import java.io.File;

import javax.swing.JOptionPane;

import Log.OtherErr;

public class OpenPDFDocument {
//	public static String dest = "C:/Program1/";//System.getProperty("user.home") + "/Desktop/";

	public static void pdfRunner(String target) {
		try {

			File pdfFile = new File(target);
			if (pdfFile.exists()) {

				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
                     JOptionPane.showMessageDialog(null, "Awt Desktop is not supported!");
				     Log.OtherErr.otherErros("Awt Desktop is not supported");
				}

			} else {
				 JOptionPane.showMessageDialog(null, "This pdf file is not exist!");
                 Log.OtherErr.otherErros("This pdf file is not exist!");
				}

			// System.out.println("Done");

		} catch (Exception ex) {
            OtherErr.otherErros(ex.toString());
		//	ex.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
