package utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import run.JDialoger;

public class ChoisePrinterDialog extends MainPanel{

	private JPanel basePanel = new JPanel();
	private final JLabel choiseLabel = new JLabel("Избери Принтер");
	private JComboBox<String> comboPrinters;
	public HashMap<String,PrintService> printServiceMap = 
			new HashMap<String,PrintService>();
	private TooltipButton printerButton = new TooltipButton();
	private static PrintService SERVICE;
	
	
	
	public ChoisePrinterDialog() {
		
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
	        
	        
	      
	  
	        comboPrinters.setRenderer(new ComboRenderer());
	        
	        comboPrinters.setPreferredSize(new Dimension(300,50));
		       
	        comboPrinters.addActionListener(new ComboListener());
	     ///   comboPrinters.setForeground(Color.green.darker().darker());
	        
	        printerButton.setText("Принтирай");
	        printerButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
	        printerButton.setPreferredSize(new Dimension(300,50));
	        printerButton.setEnabled(false);
	        printerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					SERVICE = printServiceMap.get(comboPrinters.getSelectedItem().toString());
					SwingUtilities.getWindowAncestor(ChoisePrinterDialog.this).dispose();
				}
	        	
	        });
	        basePanel.setPreferredSize(new Dimension(this.WIDTH/4, 150));
	        basePanel.setLayout(new BorderLayout());
	        
	        basePanel.add(choiseLabel, BorderLayout.NORTH);
	        basePanel.add(comboPrinters,BorderLayout.CENTER);
	        basePanel.add(printerButton,BorderLayout.SOUTH);
	        
	        this.add(basePanel);
	}
	public static PrintService showPrinters() {
		JDialoger jDialog = new JDialoger();
		jDialog.setContentPane(new ChoisePrinterDialog());
		jDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				SERVICE = null;
			}
		});
		jDialog.Show();
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
		    printerButton.setEnabled(true);
					// printer
		}
	}
	class ComboRenderer extends DefaultListCellRenderer{ 
		
		@Override
		public Component getListCellRendererComponent(JList list,Object value,
				int index,boolean isSelected,boolean cellHasFocus) {
			if(index != -1) {
				list.setToolTipText(MainPanel.getHTML_Text(
						comboPrinters.getItemAt(index).toString()));
			}
		
			Component c = super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
			return c;
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
					