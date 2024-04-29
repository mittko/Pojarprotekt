package reports;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import run.JustFrame;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;
import reports.renderers.ArtikulsTableRenderer;
import reports.renderers.JTableX;
import reports.workers.ExportToExcellWorkerArtikuls;

public class ReportTableArtikuls extends MainPanel {

	private DefaultTableModel dftm = null;
	private JTableX table = null;

	private String[] titles = null;
	private int SELECTED_INDEX = -1;

	public ReportTableArtikuls(ArrayList<Object[]> data) {
		this.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		TooltipButton printerButton = new TooltipButton("Генерирай PDF документ");

		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_INDEX == -1)
					return;

			}

		});
		TooltipButton excellButton = new TooltipButton();
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
						.getWindowAncestor(ReportTableArtikuls.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerArtikuls excellWorker = new ExportToExcellWorkerArtikuls(
						table, dftm, "Артикули.xls", jd);
				excellWorker.execute();
			}

		});

		JLabel titleLabel = new JLabel();

		northPanel.add(excellButton);

		northPanel.add(titleLabel);

		JPanel centerPanel = new JPanel();

		titles = new String[] { "Артикул", "Количество", "Мер. единица",
				"Цена", "Фактура", "Контрагент", "Дата", "Оператор",
				"Процент печалба" };

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
		for (Object[] datum : data) {
			// Object[] obj = data.get(i);
			Object[] newObj = new Object[titles.length];
			// obj[init];
			System.arraycopy(datum, 0, newObj, 0, newObj.length);

			dftm.addRow(newObj);

		}

		table.getTableHeader().setReorderingAllowed(false);


		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));

		centerPanel.add(scroll);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReportTableArtikuls r = new ReportTableArtikuls(null);
		JustFrame f = new JustFrame(r);
		f.pack();
	}


}
