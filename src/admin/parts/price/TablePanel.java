package admin.parts.price;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import run.JustFrame;
import admin.renderers.MyTableCellRenderer;
import utils.MainPanel;

public class TablePanel extends MainPanel {


	public static JTable jTable = null;
	public static int index = -1;
	public static int firstColumnWidth = 0;

	// private JTextField textField = null;

	public TablePanel() {

		firstColumnWidth = this.WIDTH / 4;
		jTable = new JTable(new DefaultTableModel(new Object[] {
				"Резервни части", "Вид", "Кат", "Маса", "Цена" }, 0));

		jTable.getTableHeader().getColumnModel().getColumn(0)
				.setPreferredWidth(this.WIDTH / 5);
		jTable.getTableHeader().getColumnModel().getColumn(1)
				.setPreferredWidth(150);
		jTable.getTableHeader().getColumnModel().getColumn(2)
				.setPreferredWidth(50);
		jTable.getTableHeader().getColumnModel().getColumn(3)
				.setPreferredWidth(50);
		jTable.getTableHeader().getColumnModel().getColumn(4)
				.setPreferredWidth(50);

		jTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		jTable.setRowHeight(MainPanel.getFontSize() + 15);
		jTable.getTableHeader().setReorderingAllowed(false);

		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				index = jTable.getSelectedRow();
				// System.out.println(index);
			}
		});


		JScrollPane scroll = new JScrollPane(jTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 170));

		this.add(scroll);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TablePanel dp = new TablePanel();
				JustFrame f = new JustFrame(dp);
				f.pack();

			}

		});
	}

}
