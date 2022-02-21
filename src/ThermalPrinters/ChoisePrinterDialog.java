package ThermalPrinters;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;


public class ChoisePrinterDialog extends JPanel{

	private final JComboBox<String> comboPrinters;
	public HashMap<String,PrintService> printServiceMap = 
			new HashMap<String,PrintService>();
	private static PrintService SERVICE;
	public ChoisePrinterDialog() {

		JLabel choiseLabel = new JLabel("Избери принтер");
		choiseLabel.setPreferredSize(new Dimension(300,50));
		
		choiseLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
	
		 PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	     
	 
		  comboPrinters = new JComboBox<>();

	        for (PrintService printer : printServices) {
	        	
	        if(!printServiceMap.containsKey(printer.getName()) 
	        		&& filterPrinters(printer.getName())  )  {
	        	           comboPrinters.addItem(printer.getName());
	        	           printServiceMap.put(printer.getName(), printer);
				        	
	        		}
	        	}
	        

	        comboPrinters.setRenderer(new DefaultListCellRenderer());
	        
	        comboPrinters.setPreferredSize(new Dimension(300,50));
		       
	        comboPrinters.addActionListener(new ComboListener());

		    JPanel basePanel = new JPanel();
	        basePanel.setLayout(new BorderLayout());
	        
	        basePanel.add(choiseLabel, BorderLayout.NORTH);
	        basePanel.add(comboPrinters,BorderLayout.CENTER);
	        
	        this.add(basePanel);
	}
	public String getSelectedPrinter() {
		return comboPrinters.getSelectedItem().toString();
	}

	public static PrintService showPrinters() {
		JDialog jDialog = new JDialog();
		jDialog.setContentPane(new ChoisePrinterDialog());
		jDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				SERVICE = null;
			}
		});
		jDialog.show();
	    return SERVICE;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				PrintService ps = showPrinters();
		   //    System.out.println(ps);
			}
			
		});
  
	}
	class ComboListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			comboPrinters.setToolTipText(comboPrinters.getSelectedItem().toString());
					// printer
		}
	}

	private boolean filterPrinters(String printerName) {
	/*	if(printerName.contains("PROTECT_1-PC\\Samsung M267x 287x Series")
		        || printerName.contains("HP LaserJet")
		        || printerName.contains("PPROTECT-PC\\Samsung SCX-4300 Series")
		        ||  printerName.startsWith("Samsung M267x 287x Series")
				        || printerName.startsWith("NPI858A77")
				        || printerName.startsWith("Samsung SCX-4300 Series") ) {
			return true;
		}*/
		return true;
	}
}
					