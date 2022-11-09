package admin.Parts.Price;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import run.JustFrame;
import utility.EditableField;
import utility.LoadIcon;
import utility.MainPanel;
import utility.TooltipButton;
import admin.AdminTableModel.CO2Model;
import admin.AdminTableModel.DustModel;
import admin.AdminTableModel.DustModel2;
import admin.AdminTableModel.WaterFameModel;
import admin.AdminTableModel.WaterModel;
import admin.Parts.Workers.ChangePriceOfPartWorker;
import admin.Parts.Workers.SeeAllPriceWorker;
import admin.Parts.Workers.seeQuantityAndPriceOfPartWorker;

public class ButtonPanel extends MainPanel {

	private JComboBox<Object> switchType = null;
	private final EditableField searchField;
	private final DustModel dustModel = new DustModel();
	private final DustModel2 dustModel2 = new DustModel2();
	private final WaterModel waterModel = new WaterModel();
	private final WaterFameModel waterFameModel = new WaterFameModel();
	private final CO2Model co2Model = new CO2Model();
	private final ArrayList<Object[]> helpList = new ArrayList<Object[]>();

	GridBagLayout gbLayout = new GridBagLayout();

	public ButtonPanel() {
		JPanel basePanel = new JPanel();// GradientPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanel.setPreferredSize(new Dimension(this.WIDTH - 50, 100));
		basePanel.setLayout(gbLayout);

		searchField = new EditableField("Търсене", 10) {
			private static final long serialVersionUID = 2L;

			@Override
			public Font getFont() {
				return new Font(Font.MONOSPACED, Font.LAYOUT_NO_START_CONTEXT,
						MainPanel.getFontSize() + 10);
			}
		};
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				DefaultTableModel dftm = (DefaultTableModel) TablePanel.jTable
						.getModel();
				dftm.setRowCount(0);
				String b = searchField.getText().toLowerCase();
				for (Object[] o : helpList) {
					String a = o[0].toString().toLowerCase();
					if (a.startsWith(b)) {
						dftm.addRow(o);
					} else if (a.contains(b)) {
						dftm.addRow(o);
					}
				}
			}
		});
		TooltipButton viewButton = new TooltipButton("Виж текуща цена");

		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (TablePanel.index < 0) {
					return;
				}
				String part = TablePanel.jTable.getValueAt(TablePanel.index, 0)
						.toString();
				String type = TablePanel.jTable.getValueAt(TablePanel.index, 1)
						.toString();
				String category = TablePanel.jTable.getValueAt(
						TablePanel.index, 2).toString();
				String wheight = TablePanel.jTable.getValueAt(TablePanel.index,
						3).toString();

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ButtonPanel.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				seeQuantityAndPriceOfPartWorker vw = new seeQuantityAndPriceOfPartWorker(
						part, type, category, wheight, jd);
				vw.execute();

			}

		});
		TooltipButton saveButton = new TooltipButton("Промени текуща цена");

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (TablePanel.index < 0) {
					return;
				}
				if (TablePanel.jTable.getValueAt(TablePanel.index, 4) == null
						|| TablePanel.jTable.getValueAt(TablePanel.index, 4)
								.equals("")) {
					return;
				}
				// ckeck user input
				double d = 0.0;
				try {
					d = Double.parseDouble(TablePanel.jTable.getValueAt(
							TablePanel.index, 4).toString());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Невалидни данни!");
					return;
				}

				if (d < 0) {
					JOptionPane.showMessageDialog(null, "Невалидни данни!");
					return;
				}

				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0) {
					return;
				}
				String part = TablePanel.jTable.getValueAt(TablePanel.index, 0)
						.toString();
				String type = TablePanel.jTable.getValueAt(TablePanel.index, 1)
						.toString();
				String category = TablePanel.jTable.getValueAt(
						TablePanel.index, 2).toString();
				String wheight = TablePanel.jTable.getValueAt(TablePanel.index,
						3).toString();
				double newPrice = d;// TablePanel.jTable.getValueAt(TablePanel.index,
									// 4).toString();

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ButtonPanel.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ChangePriceOfPartWorker spw = new ChangePriceOfPartWorker(part,
						type, category, wheight, newPrice, jd);
				spw.execute();

			}

		});

		switchType = new JComboBox<Object>(new String[] { type_Prah_BC,
				type_Prah_ABC, type_Water, type_Water_Fame, type_CO2 });
		switchType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				switch (switchType.getSelectedItem().toString()) {
				case type_Prah_BC:
					TablePanel.jTable.setModel(dustModel);
					break;
				case type_Prah_ABC:
					TablePanel.jTable.setModel(dustModel2);
					break;
				case type_Water:
					TablePanel.jTable.setModel(waterModel);
					break;
				case type_Water_Fame:
					TablePanel.jTable.setModel(waterFameModel);
					break;
				case type_CO2:
					TablePanel.jTable.setModel(co2Model);
					break;
				default:
					break;
				}
				DefaultTableModel dftm = (DefaultTableModel) TablePanel.jTable
						.getModel();
				helpList.clear();
				// store data to list to pass to search field
				for (int i = 0; i < dftm.getRowCount(); i++) {
					Object[] obj = new Object[dftm.getColumnCount()];
					for (int j = 0; j < dftm.getColumnCount(); j++) {
						obj[j] = dftm.getValueAt(i, j);
					}
					helpList.add(obj);
				}

				// TablePanel.jTable.getColumnModel().getColumn(4).setCellEditor(new
				// MyTableCellEditor());
				TablePanel.jTable.getTableHeader().getColumnModel()
						.getColumn(0)
						.setPreferredWidth(TablePanel.firstColumnWidth);
				TablePanel.jTable.getTableHeader().getColumnModel()
						.getColumn(1).setPreferredWidth(150);
				TablePanel.jTable.getTableHeader().getColumnModel()
						.getColumn(2).setPreferredWidth(50);
				TablePanel.jTable.getTableHeader().getColumnModel()
						.getColumn(3).setPreferredWidth(50);
				TablePanel.jTable.getTableHeader().getColumnModel()
						.getColumn(4).setPreferredWidth(50);
				TablePanel.index = -1;
			}

		});
		TooltipButton excellButton = new TooltipButton();
		excellButton.setPreferredSize(new Dimension((int) (basePanel
				.getPreferredSize().getWidth() * 0.1), (int) (basePanel
				.getPreferredSize().getHeight() * 0.35)));
		;
		excellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		excellButton.setAutoSizedIcon(excellButton, new LoadIcon().setIcons(excellImage));
		excellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// JDialog jd =
				// (JDialog)SwingUtilities.getWindowAncestor(ButtonPanel.this);
				// jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SeeAllPriceWorker seePrice = new SeeAllPriceWorker();
				seePrice.execute();
			}

		});

		int[] x = { 0, 1, 2, 0, 1, 2 };
		int[] y = { 0, 0, 0, 1, 1, 1 };
		GridBagConstraints[] gbc = new GridBagConstraints[x.length];
		for (int i = 0; i < x.length; i++) {
			gbc[i] = new GridBagConstraints();
			// gbc[i].weightx = 1.0;
			gbc[i].fill = GridBagConstraints.HORIZONTAL;
			gbc[i].gridx = x[i];
			gbc[i].gridy = y[i];
			gbc[i].insets = new Insets(5, 5, 5, 5);
		}

		JLabel switchLabel = new JLabel("Цени на резервни части : ");
		basePanel.add(switchLabel, gbc[1]);
		basePanel.add(switchType, gbc[2]);
		basePanel.add(searchField, gbc[0]);
		basePanel.add(viewButton, gbc[3]);
		basePanel.add(saveButton, gbc[4]);
		basePanel.add(excellButton, gbc[5]);

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ButtonPanel bp = new ButtonPanel();
				JustFrame f = new JustFrame(bp);
				f.pack();

			}

		});
	}

}
