package WorkingBook;

import NewExtinguisher.SwingWorkers.getProtokolNumberWorker;
import WorkingBook.Renderers.DoingRenderer;
import WorkingBookWorkers.PrintProtokolWorker;
import WorkingBookWorkers.SaveInProtokolWorker;
import db.Common;
import run.JustFrame;
import utils.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static WorkingBook.WorkingBook.isBarcodAndSerialEntered;

public class View extends MainPanel {

	private static final String[] titles = { "Клиент", "Вид", "Маса", "Баркод",
			"Монтажен номер", "Категория", "Марка", "ТО", "П", "ХИ",
			"Реална маса", "Допълнителни данни" };

	public static DefaultTableModel dtm_Extinguisher = new DefaultTableModel(
			titles, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}; // store saved exinguisher

	public MyTable t_Extinguisher = null;

	private StringBuilder key = new StringBuilder(); // current extinguisher

	public static JList<Object> partsList = new JList<Object>();

	public static TooltipButton printProtokolButton = null;

	private int SELECTED_ROW = -1; // current user selected row

	public String DB_PROTOKOL_NUMBER = null;

	private String PDF_PROTOKOL_NUMBER = null;

	public static BevelLabel protokolNumberLabel = null;

	public View(String protokolNumber) {

		this.DB_PROTOKOL_NUMBER = protokolNumber;

		PDF_PROTOKOL_NUMBER = DB_PROTOKOL_NUMBER;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));

		panel.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 1.0) - 50));

		panel.setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.5)));
		north.setLayout(new BorderLayout());

		JPanel northNorth = new JPanel();// GradientPanel();
		northNorth.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 1) - 20, (int) (north
				.getPreferredSize().getHeight() * 0.2)));
		northNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

		float labelHeight = (int) (northNorth.getPreferredSize().getHeight() * 0.7);
		final BevelLabel label = new BevelLabel(labelHeight);

		label.setTitle("Обработени пожарогасители: ");


		TooltipButton dbButton = new TooltipButton();

		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension((int) (northNorth
				.getPreferredSize().getWidth() * 0.045), (int) (northNorth
				.getPreferredSize().getHeight() * 0.75)));
		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (t_Extinguisher.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					JDialog jd = ((JDialog) (SwingUtilities
							.getWindowAncestor(View.this)));
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					getProtokolNumberWorker gpn = new getProtokolNumberWorker();
					try {
						DB_PROTOKOL_NUMBER = gpn.doInBackground();

					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					SaveInProtokolWorker sw = new SaveInProtokolWorker(jd,
							DB_PROTOKOL_NUMBER, getPartsMap());
					try {
						int result = sw.doInBackground();
						if (result > 0) {
							PDF_PROTOKOL_NUMBER = DB_PROTOKOL_NUMBER;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

		printProtokolButton = new TooltipButton();

		printProtokolButton.setEnabled(false);

		printProtokolButton
				.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ ПРОТОКОЛ 2815"));

		printProtokolButton.setPreferredSize(new Dimension((int) (northNorth
				.getPreferredSize().getWidth() * 0.045), (int) (northNorth
				.getPreferredSize().getHeight() * 0.75)));
		printProtokolButton.setAutoSizedIcon(printProtokolButton,
				new LoadIcon().setIcons(printerImage));

		printProtokolButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (t_Extinguisher.getRowCount() == 0) {
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(View.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));


				PrintProtokolWorker pw = new PrintProtokolWorker(
						dtm_Extinguisher, jd, PDF_PROTOKOL_NUMBER,
						getPartsMap());
				pw.execute();

			}

		});

		TooltipButton removeRowButton = new TooltipButton();
		removeRowButton.setPreferredSize(new Dimension((int) (northNorth
				.getPreferredSize().getWidth() * 0.045), (int) (northNorth
				.getPreferredSize().getHeight() * 0.75)));
		removeRowButton.setAutoSizedIcon(removeRowButton,
				new LoadIcon().setIcons(eraserImage));
		removeRowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int yes_no = JOptionPane.showOptionDialog(null,
						"Сигурни ли сте че искате да изтриете въведените данни ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if(yes_no == 0) {
					isBarcodAndSerialEntered.clear();
					dtm_Extinguisher.setRowCount(0);
				}
			}
		});

		protokolNumberLabel = new BevelLabel(labelHeight);
		protokolNumberLabel.setTitle(" Протокол № ");
		protokolNumberLabel.setName(protokolNumber);

		partsList = new JList<>(new DefaultListModel<>());

		partsList.setOpaque(false);

		t_Extinguisher = new MyTable(dtm_Extinguisher) {
			@Override
			public void removeAt(int row) {
				isBarcodAndSerialEntered.remove(dtm_Extinguisher.getValueAt(row, 3).toString());
				isBarcodAndSerialEntered.remove(dtm_Extinguisher.getValueAt(row, 4).toString());

				dtm_Extinguisher.removeRow(row);

			}

			@Override
			public void onTableChanged(TableModelEvent tableModelEvent) {
				super.onTableChanged(tableModelEvent);
				if(dtm_Extinguisher.getRowCount() == 0) {
					WorkingBook.CURRENT_CLIENT = "";
					((DefaultListModel<Object>) partsList.getModel()).clear();
				}

				label.setName(String.valueOf(dtm_Extinguisher.getRowCount()));

				StringBuilder sb = new StringBuilder();
				HashMap<String,Integer> obraboteni = new HashMap<>();
				for(int i = 0;i < dtm_Extinguisher.getRowCount();i++) {
					String key = String.format("%s %s\n",dtm_Extinguisher.getValueAt(i,1),dtm_Extinguisher.getValueAt(i,2));
					Integer value = obraboteni.get(key);
					if(value == null) {
						value = 0;
					}
					obraboteni.put(key,value+1);
				}
				sb.append("<div><font size=6>Обработени по вид</font></div>");
				for(Map.Entry<String,Integer> entry : obraboteni.entrySet()) {
					sb.append(String.format("<div><font size=15>%s     ( %d )</font></div>",entry.getKey(),entry.getValue()));
				}
				label.setToolTipText(getHTML_Text(sb.toString(),9));
			}
		};

		t_Extinguisher.removeColumn(t_Extinguisher.getColumn("Реална маса"));
		t_Extinguisher.removeColumn(t_Extinguisher
				.getColumn("Допълнителни данни"));

		t_Extinguisher.setDefaultRenderer(Object.class, new DoingRenderer(
				t_Extinguisher));
		t_Extinguisher.setRowHeight(Common.getFontSize() + 15);

		t_Extinguisher.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				SELECTED_ROW = t_Extinguisher.getSelectedRow();
				if(SELECTED_ROW == -1) {
					return;
				}
				key = new StringBuilder();

				// create key to get parts from hashmap
				key.append(t_Extinguisher.getValueAt(SELECTED_ROW, 3)
						.toString());

				DefaultListModel<Object> dlm_Parts = new DefaultListModel<Object>();
				ArrayList<Object> value = WorkingBook.ext_parts.get(key.toString());
				if (value != null) {
					for (Object obj : value) {
						String partName = obj.toString();
						dlm_Parts.addElement(partName);
					}
				}

				partsList.setModel(dlm_Parts);
			}
		});


		JScrollPane scroll = new JScrollPane(t_Extinguisher,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.25)));

		JScrollPane scroll2 = new JScrollPane(partsList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll2.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.2)));

		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());

		JLabel labelParts = new JLabel("Вложени резервни части ");

		labelParts.setOpaque(false);

		northNorth.add(label);

		northNorth.add(dbButton);

		northNorth.add(printProtokolButton);

		northNorth.add(protokolNumberLabel);

		northNorth.add(removeRowButton);

		north.add(northNorth, BorderLayout.NORTH);

		north.add(scroll, BorderLayout.CENTER);

		panel.add(north, BorderLayout.NORTH);

		center.add(labelParts, BorderLayout.NORTH);
		center.add(scroll2, BorderLayout.CENTER);

		panel.add(center, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.08)));

		panel.add(southPanel, BorderLayout.SOUTH);

		this.add(panel);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JustFrame f = new JustFrame(new View(""));
		f.pack();
	}

	public String getKey(int row) {

		StringBuilder sb = new StringBuilder();
		sb.append(t_Extinguisher.getValueAt(row, 3).toString());
		return sb.toString();

	}

	public TreeMap<Object, Integer> getPartsMap() {
		TreeMap<Object, Integer> wMap = new TreeMap<Object, Integer>();
		for (int row = 0; row < t_Extinguisher.getRowCount(); row++) {
			ArrayList<Object> parts = WorkingBook.ext_parts.get(getKey(row));
			for (Object key : parts) {
				Integer broi = wMap.get(key);
				if (broi == null) {
					broi = 0;
				}
				wMap.put(key, broi + 1);
			}
		}
		return wMap;
	}

}
