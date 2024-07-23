package reports;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import models.AcquittanceReports;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;
import reports.renderers.AcquittanceTableRender;
import reports.workers.ExportToExcellWorkerAcquittance;
import reports.workers.PrintAcquittanceWorker;

public class ReportTableAcquittance<T> extends MainPanel {

	private final JTable table;
	public DefaultTableModel dftm;
	private int SELECTED_INDEX = -1;
	ArrayList<T> childData;
	private ParentAcquittanceInfo info;
	public static int MOUSE_MOTION_ROW = -1;

	public ReportTableAcquittance(ArrayList<T> childData) {
		MOUSE_MOTION_ROW = -1;
		this.childData = childData;

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		TooltipButton printButton = new TooltipButton("Генерирай PDF документ");
		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_INDEX == -1)
					return;

				Integer[] selectedIndexes = getSelectedIndexes(SELECTED_INDEX,
						0);
				int start = selectedIndexes[0];
				int end = selectedIndexes.length;

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableAcquittance.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				PrintAcquittanceWorker pdf = new PrintAcquittanceWorker(dftm,
						start, end, jd);

				pdf.execute();

			}

		});

		TooltipButton exportToExcellButton = new TooltipButton();
		exportToExcellButton.setPreferredSize(new Dimension((int) (printButton
				.getPreferredSize().getWidth() * 0.3), (int) (printButton
				.getPreferredSize().getHeight())));

		exportToExcellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		exportToExcellButton.setAutoSizedIcon(exportToExcellButton,
				new LoadIcon().setIcons(excellImage));
		exportToExcellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableAcquittance.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerAcquittance export = new ExportToExcellWorkerAcquittance(
						dftm, "Стокова Разписка.xls", jd);
				export.execute();
			}

		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(printButton);
		buttonPanel.add(exportToExcellButton);

		northPanel.add(buttonPanel);

		JPanel centerPanel = new JPanel();

		dftm = new DefaultTableModel(new String[] { "\u2116 на Документ",
				"Стойност", "Клиент", "Оператор", "Дата",
				" \u2116 на Документ", "Артикул", "Мер. ед", "Количество",
				"Ед. Цена", "Крайна цена", "Клиент" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		for (T t : childData) {
			AcquittanceReports acquittanceReports = (AcquittanceReports) t;
//			private String id;
//			private String value;
//			private String client;
//			private String saller;
//			private String date;
//			private String _id;
//			private String artikul;
//			private String med;
//			private String quantity;
//			private String price;
//			private String _value;
//			private String _client;

			Object[] childDatum = new Object[dftm.getColumnCount()];
			childDatum[0] = acquittanceReports.getId();
			childDatum[1] = acquittanceReports.getValue();
			childDatum[2] = acquittanceReports.getClient();
			childDatum[3] = acquittanceReports.getSaller();
			childDatum[4] = acquittanceReports.getDate();
			childDatum[5] = acquittanceReports.get_id();
			childDatum[6] = acquittanceReports.getArtikul();
			childDatum[7] = acquittanceReports.getMed();
			childDatum[8] = acquittanceReports.getQuantity();
			childDatum[9] = acquittanceReports.getPrice();
			childDatum[10] = acquittanceReports.get_value();
			childDatum[11] = acquittanceReports.get_client();

			dftm.addRow(childDatum);
		}
		table = new JTable(dftm);
		table.setDefaultRenderer(Object.class, new AcquittanceTableRender());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		resizeColumnWidth(table);
		// hide dublicate columns
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getColumnModel().getColumn(5).setWidth(0);
		table.getColumnModel().getColumn(11).setMinWidth(0);
		table.getColumnModel().getColumn(11).setMaxWidth(0);
		table.getColumnModel().getColumn(11).setWidth(0);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				// call repaint() to set background correctly
				// after select client in table renderer (in reports table)
				table.repaint();

				SELECTED_INDEX = table.getSelectedRow();

			}
		});

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));


		centerPanel.add(scroll);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Integer[] getSelectedIndexes(int index, int targetColumn) {

		ArrayList<Integer> listIndexes = new ArrayList<Integer>();
		// get same elements above curr element
		for (int i = index - 1; i >= 0; i--) {
			if (dftm.getValueAt(index, targetColumn).equals(
					dftm.getValueAt(i, targetColumn))) {
				listIndexes.add(0, i);
			} else {
				break;
			}
		}

		// add curr element into list
		listIndexes.add(index);

		for (int i = index + 1; i < dftm.getRowCount(); i++) {
			if (dftm.getValueAt(index, targetColumn).equals(
					dftm.getValueAt(i, targetColumn))) {
				listIndexes.add(i);
			} else {
				break;
			}
		}
		return listIndexes.toArray(new Integer[listIndexes.size()]);
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50;// 200; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			columnModel.getColumn(column).setPreferredWidth(
					width + getFontSize());
		}

	}

}
