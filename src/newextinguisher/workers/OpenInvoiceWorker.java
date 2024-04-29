/*package NewExtinguisher.SwingWorkers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import Invoice.Worker.ProtokolSearcher;
import InvoiceWindow.AcquittanceTab;
import InvoiceWindow.MyInvoice3;
import InvoiceWindow.SearchPanels.searchFromProform;
import InvoiceWindow.SearchPanels.searchFromProtokol;
import Title.JDialoger;
import db.Invoice.InvoiceNumber;

public class OpenInvoiceWorker extends SwingWorker {
//	private String invoiceNumber = null;
//	private String proformNumber = null;
    private String protokolNumber = null;
    
	public OpenInvoiceWorker(String protokolNumber) {
		this.protokolNumber = protokolNumber;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		String invoiceNumber = InvoiceNumber.getInvoiceNumber();
	
		ProtokolSearcher ps = new ProtokolSearcher(protokolNumber,searchFromProtokol.invoiceTableModel); // search in protokol
		ps.execute();		
		if(invoiceNumber != null) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// initialize data for invoice
			
					final MyInvoice3 invoice3 = new MyInvoice3();
					searchFromProtokol.searchField.setText(protokolNumber);
					final JDialoger jDialog = new JDialoger();
					jDialog.setContentPane(invoice3);
					jDialog.setTitle("Фактури / Проформи / Стокови разписки");
					jDialog.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							if(searchFromProtokol.invoiceTableModel.getRowCount() > 0
									|| searchFromProform.proformTableModel.getRowCount() > 0
									|| AcquittanceTab.dftm.getRowCount() > 0) {
								int yes_no = JOptionPane
										.showOptionDialog(null,"Наистина ли искате да затворите прозореца ?","",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null,new String[] {
														"Да", "Не" }, 
												"default");
								if(yes_no == 0) {
									jDialog.dispose();
								} else {
								jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
								}
							} else {
								jDialog.dispose();
							}
						}
					});
					jDialog.Show();
					
					
				}
				
			});
		}
		return null;
	
	}
}
*/