package parts;

import workingbook.renderers.ReasonsRenderer;
import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReasonsBrack extends MainPanel {

	// private ArrayList<Object> list = new ArrayList<Object>();
	private static String[] titles = { "Причини за брак" };
	public static DefaultTableModel dtm = null;
	public static JTable table = null;
	public static boolean[] boolReasons = null;

	public ReasonsBrack() {
		// clear table models content to avoid overflow exception
		dtm = new DefaultTableModel(titles, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return row == 4;
			}
		};
		

		int reasonsLength = 10;
		boolReasons = new boolean[reasonsLength];
	
	    dtm.addRow(new Object[] {"Повреда на съд за гасително вещество" });
	    dtm.addRow(new Object[] {"Корозия на съда" });
	    dtm.addRow(new Object[] {"Механично разрушаване на съд за гасително вещество" });
	    dtm.addRow(new Object[] {"Повреда на глава" });
	    dtm.addRow(new Object[] {"Други" });
	    
	    
		 table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				boolReasons[table.getSelectedRow()] = !boolReasons[table
						.getSelectedRow()];
			}
		});
		table.setOpaque(false);
		table.setDefaultRenderer(Object.class, new ReasonsRenderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(this.WIDTH - 20, this.HEIGHT / 2));
		this.add(scroll);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JustFrame f = new JustFrame(new ReasonsBrack());
		f.pack();
	}

}
