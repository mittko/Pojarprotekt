package workingbook;

import parts.renderers.ScrabRenderer;
import workingbook.workers.PrintProtokolBrackWorker;
import workingbook.workers.SaveInBrackWorker;
import db.Brack.BrackNumber;
import db.Common;
import run.JustFrame;
import utils.BevelLabel;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Brack extends MainPanel {
	public static TooltipButton printServiceButton = null;

	private static final String[] titles =
		{"Клиент","Вид","Маса","Баркод","Монтажен номер","Категория","Марка"};
	
	
	public static DefaultTableModel dtm_Scrab = new DefaultTableModel(titles,0){
		@Override
		public boolean isCellEditable(int row,int column) {
			return false;
		}
	};
	
	
	 private StringBuilder key = new StringBuilder();  // current exinguisher
	
	 private final int SELECTED_ROW = 0;   // current user selected row
	
     public JTable t_Scrab = null;

	public static JList<Object> reasonsList = null;
	 
	 private final boolean write_db = false; // is write to db done or not
	 
	 private String BRACK_NUMBER;

	private final BrackNumber bn = new BrackNumber();

	 //public static BevelLabel brackNumberLabel = null;
	 
	public Brack() {

      
	 JPanel gp = new JPanel();
	 
	 gp.setBorder(BorderFactory.createLineBorder(Color.black));
	 
	 gp.setLayout(new BorderLayout());
	 
	 gp.setPreferredSize(new Dimension(
			 (int)(this.WIDTH * 1.0)-20,
			 (int)(this.HEIGHT * 0.9) ));
	 
	 JPanel northPanel = new JPanel();
	 northPanel.setPreferredSize(new Dimension(
			 (int)(this.WIDTH * 1.0)-20,
			 (int)(this.HEIGHT * 0.50)));
	 
	 northPanel.setLayout(new BorderLayout());
	 
	 JPanel helpPanel = new JPanel();//GradientPanel();
	 helpPanel.setPreferredSize(new Dimension(
			 (int)(northPanel.getPreferredSize().getWidth()),
			(int)( northPanel.getPreferredSize().getHeight() * 0.2)));
	 helpPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,5));
	 
	 float labelHeight =  (int)(helpPanel.getPreferredSize().getHeight() * 0.7);
	 
	 BevelLabel scrabLabel = new BevelLabel(labelHeight);

	 scrabLabel.setTitle("Бракувани");
	 scrabLabel.setName("");
	 
	 scrabLabel.setOpaque(false);
	 

	 
	 printServiceButton = new TooltipButton();
	 
	 printServiceButton.setEnabled(false);

	 
	    printServiceButton.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ ПРОТОКОЛ ЗА БРАК"));
	    printServiceButton.setPreferredSize(new Dimension(
				(int)(helpPanel.getPreferredSize().getWidth() * 0.045), 
				(int)(helpPanel.getPreferredSize().getHeight() * 0.75)));
		 printServiceButton.setAutoSizedIcon(printServiceButton, new LoadIcon().setIcons(printerImage));
		printServiceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(t_Scrab.getRowCount() == 0) {
					return;
				}
		        JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(Brack.this);
		        jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		        
				PrintProtokolBrackWorker pw = new PrintProtokolBrackWorker(
						jd,BRACK_NUMBER,
						0,t_Scrab.getRowCount());
				pw.execute();
         
			}

		});


		TooltipButton dbButton = new TooltipButton();
		
		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension(
				(int)(helpPanel.getPreferredSize().getWidth() * 0.045), 
				(int)(helpPanel.getPreferredSize().getHeight() * 0.75)));
		 dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));

		
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(t_Scrab.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				
				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if(yes_no == 0) {
					JDialog jd = ((JDialog)(SwingUtilities.getWindowAncestor(Brack.this)));
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						

					SaveInBrackWorker sw = new SaveInBrackWorker(jd);
					try {
						BRACK_NUMBER = sw.doInBackground();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
			
		});
		
	 helpPanel.add(scrabLabel);

	 helpPanel.add(dbButton);
	 
	 helpPanel.add(printServiceButton);
	 
//	brackNumberLabel = new BevelLabel(labelHeight);
//	brackNumberLabel.setTitle("Протокол за Брак \u2116 ");
//    brackNumberLabel.setName(br_number);
//	helpPanel.add(brackNumberLabel);
	
	northPanel.add(helpPanel,BorderLayout.NORTH);

		JList<Object> scrabList = new JList<Object>();
	 
	 
	 t_Scrab = new JTable(dtm_Scrab);
	
	 t_Scrab.setDefaultRenderer(Object.class, new ScrabRenderer(t_Scrab));
	 t_Scrab.setRowHeight(Common.getFontSize() + 15);
	 

	 reasonsList = new JList<Object>();
	 
	 reasonsList.setOpaque(false);
	 
	 
	 t_Scrab.addMouseListener(new MouseAdapter() {
		 public void mousePressed(MouseEvent me) {
				key = new StringBuilder();
		
				key.append(t_Scrab.getValueAt(t_Scrab.getSelectedRow(),3)); // barcod
				
				ArrayList<Object> value = WorkingBook.reasons_map.get(key.toString());
				
				if(value != null) {
				
					DefaultListModel<Object> dlm = new DefaultListModel<Object>();
					for(Object obj : value) {
						dlm.addElement(obj);
						
					}
					reasonsList.setModel(dlm);
				} 
		 }
	 });	
	 
	 
	
	 
	 JScrollPane scrabScroll = new JScrollPane(t_Scrab,
			 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	 
	 scrabScroll.setPreferredSize(new Dimension(
			 (int)(this.WIDTH * 1.0)-20,
			 (int)(this.HEIGHT * 0.3)));
	   
	 northPanel.add(scrabScroll,BorderLayout.CENTER);
	 
	 JPanel centerPanel = new JPanel();
	 
	 centerPanel.setOpaque(false);
	
	 centerPanel.setLayout(new BorderLayout());
	 
	 JLabel  reasonsLabel = new JLabel("Причини за брак");
	 
	 reasonsLabel.setOpaque(false);
	 
	
	 JScrollPane reasonsScroll = new JScrollPane(reasonsList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	 reasonsScroll.setPreferredSize(new Dimension(
			 (int)(this.WIDTH * 1.0)-20,
			 (int)(this.HEIGHT * 0.3)));
	 
	 centerPanel.add(reasonsLabel,BorderLayout.NORTH);
	 
	 centerPanel.add(reasonsScroll,BorderLayout.CENTER);
	 
	JPanel southPanel = new JPanel();
	southPanel.setPreferredSize(new Dimension(
			(int)(this.WIDTH * 1.0)-20,
			(int)(this.HEIGHT * 0.05)));
		
	 gp.add(northPanel,BorderLayout.NORTH);
	 
	 gp.add(centerPanel,BorderLayout.CENTER);
	 
	 gp.add(southPanel, BorderLayout.SOUTH);
	 this.add(gp);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       JustFrame f = new JustFrame(new Brack());
       f.pack();
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public String getKey(int row) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(dtm_Scrab.getValueAt(row, 3));// barcod
		return sb.toString();
		
    }
	

}
