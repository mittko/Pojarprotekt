package admin.Team.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.TeamDB.Member;

public class AddUserWorker  extends SwingWorker {

	String usser;
	String password;
	String accessService_Order;
	String accessWorking_Book;
	String accessInvoice;
	String accessReports;
	String accessNew_Ext;
	String accessHidden_Menu;
	String acquittance;
	JDialog jd;
	int update  = 0;
	
	public AddUserWorker(String usser,String password,String accessService_Order,
			String accessWorking_Book,String accessInvoice,String accessReports,
			String accessNew_Ext,String accessHidden_Menu,String acquittance, JDialog jd) {
		this.usser = usser;
		this.password = password;
		this.accessService_Order = accessService_Order;
		this.accessWorking_Book = accessWorking_Book;
		this.accessInvoice = accessInvoice;
		this.accessReports = accessReports;
		this.accessNew_Ext = accessNew_Ext;
		this.accessHidden_Menu = accessHidden_Menu;
		this.acquittance = acquittance;
		this.jd = jd;

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    
	}

	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		try {
	 
		update = Member.addMemeber(usser, password, accessService_Order, accessWorking_Book, 
				accessInvoice, accessReports, accessNew_Ext, accessHidden_Menu, acquittance);
	
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(update > 0) {
						JOptionPane.showMessageDialog(null, "Данните са записани успешно!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}
				
			});
		}
		return update;
	}
       
}
