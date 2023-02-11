package Reports;

import Reports.ReportsRenderers.ArtikulsTableRenderer;
import Reports.ReportsRenderers.JTableX;
import Reports.ReportsWorkers.ExportToExcellWorkerNewExtinguishers;
import run.JustFrame;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ReportTableNewExtinguishers extends MainPanel {

	private JPanel northPanel = null;
	private TooltipButton printerButton = null;
	private TooltipButton excellButton = null;
	private JLabel titleLabel = null;

	private JPanel centerPanel = null;
	private DefaultTableModel dftm = null;
	private JTableX table = null;

	private String[] titles = null;
	private int SELECTED_INDEX = -1;

	public ReportTableNewExtinguishers(ArrayList<Object[]> data) {
		this.setLayout(new BorderLayout());

		northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		printerButton = new TooltipButton("Генерирай PDF документ");

		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_INDEX == -1)
					return;

			}

		});
		excellButton = new TooltipButton();
		excellButton.setPreferredSize(new Dimension((int) (printerButton
				.getPreferredSize().getWidth() * 0.3), (int) (printerButton
				.getPreferredSize().getHeight() * 1.0)));
		;
		excellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		excellButton.setAutoSizedIcon(excellButton, new LoadIcon().setIcons(excellImage));
		excellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableNewExtinguishers.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerNewExtinguishers excellWorker = new ExportToExcellWorkerNewExtinguishers(
						table, dftm, "Нови Пожарогасители.xls", jd);
				excellWorker.execute();
			}

		});

		titleLabel = new JLabel();

		// northPanel.add(printerButton);
		northPanel.add(excellButton);

		northPanel.add(titleLabel);

		centerPanel = new JPanel();

		titles = new String[] { "Вид", "Маса", "Категория", "Марка",
				"Количество", "Цена", "Фактура", "Контрагент", "Дата",
				"Оператор", "Процент печалба" };

		dftm = new DefaultTableModel(titles, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// fill data in table
		table = new JTableX(dftm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				SELECTED_INDEX = table.getSelectedRow();

				// call repaint() to set background correctly
				// after select client in table renderer (in reports table)
				table.repaint();

			}
		});

		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.setDefaultRenderer(Object.class, new ArtikulsTableRenderer());

		// RowEditorModel rem = new RowEditorModel();
		// table.setRowEditorModel(rem);
		for (int i = 0; i < data.size(); i++) {
			// Object[] obj = data.get(i);
			Object[] newObj = new Object[titles.length];
			for (int init = 0; init < newObj.length; init++) {
				newObj[init] = data.get(i)[init];// obj[init];

			}

			dftm.addRow(newObj);
			/*
			 * for(int j = 0;j < titles.length;j++) {
			 * if(titles[j].equals("Резервни Части")) { String[] items =
			 * obj[j].toString().split(","); rem.addEditorForRow(i, new
			 * MyComboBoxEditor(items)); } }
			 */
		}

		table.getTableHeader().setReorderingAllowed(false);
		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// JTable rowTable = new RowNumberTable(table); //****

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.getVerticalScrollBar().setUI(new YourUI());
		// scroll.getHorizontalScrollBar().setUI(new YourUI());
		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));

		// scroll.setRowHeaderView(rowTable); //****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,//****
		// rowTable.getTableHeader());//****

		centerPanel.add(scroll);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReportTableNewExtinguishers r = new ReportTableNewExtinguishers(null);
		JustFrame f = new JustFrame(r);
		f.pack();
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50;// 300; // Min width
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
