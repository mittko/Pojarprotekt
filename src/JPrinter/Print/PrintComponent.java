//package JPrinter.Print;
//
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//
//import utility.MainPanel;
//import Exceptions.PrintException;
//import Log.PrinterErr;
//
//public class PrintComponent extends MainPanel{
//   private JButton button;
//	public PrintComponent() {
//		button = new JButton("I'm a button");
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//                print(PrintComponent.this);
//			}
//			
//		});
//		
//		this.add(button);
//	}
//	public  void print(final JComponent component){
//
//		  PrinterJob pj = PrinterJob.getPrinterJob();
//		  pj.setJobName(" Print Component ");
//          
//		  pj.setPrintable (new Printable() {    
//		    public int print(Graphics pg, PageFormat pf, int pageNum){
//		      if (pageNum > 0){
//		      return Printable.NO_SUCH_PAGE;
//		      }
//
//		      Graphics2D g2 = (Graphics2D) pg;
//		      g2.translate(pf.getImageableX(), pf.getImageableY());
//		      component.paint(g2);
//		      return Printable.PAGE_EXISTS;
//		    }
//		  });
//		  
//		  
//		  if (pj.printDialog() == false)
//		  return;
//
//		  try {
//		        pj.print();
//		  } catch (PrinterException ex) {
//			  PrintException.Print_Exception(ex);
//		      PrinterErr.printerErros(ex.toString());
//		        // handle exception
//		  }
//		}
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				  PrintComponent pc = new PrintComponent();
//				  JFrame jf = new JFrame();
//				  jf.add(pc);
//				  jf.pack();
//				  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				  jf.setVisible(true);
//			}
//			
//		});
//   
//     
//	}
//
// }
