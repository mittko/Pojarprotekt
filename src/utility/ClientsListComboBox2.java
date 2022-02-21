package utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import NewClient.editClient.IncorrectPerson;
import db.Client.ClientTable;

public class ClientsListComboBox2 extends JComboBox<IncorrectPerson> {
	ArrayList<IncorrectPerson> v = null;
	ArrayList<IncorrectPerson> firms = null;

	public ClientsListComboBox2() {
		v = new ArrayList<IncorrectPerson>();
		init();
		/*
		 * v.add("tlakovanje"); v.add("tlakovec"); v.add("tlakovana cesta");
		 * v.add("tla&#269;an"); v.add("tlaka"); v.add("slovenec");
		 * v.add("slovenka"); v.add("slovenija"); v.add("slovar");
		 * v.add("slovenci"); v.add("&#269;ajra"); v.add("&#269;aranje");
		 */

		this.setEditable(true);
		this.setSelectedItem("");

		for (int i = 0; i < firms.size(); i++) {
			v.add(firms.get(i));
			this.addItem(v.get(i));
		}

		this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (getEditor().getItem().toString().length() <= 1) {
					// init();
					v.clear();
					for (int i = 0; i < firms.size(); i++) {
						v.add(firms.get(i));
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// enter=10 up=38 down=40

				// TODO Auto-generated method stub
				if (e.getKeyCode() != 38 && e.getKeyCode() != 40
						&& e.getKeyCode() != 10) {

					// GET CURRENT CURSOR POSITION
					ComboBoxEditor editor = getEditor();
					JTextField textField = (JTextField) editor
							.getEditorComponent();
					int caretPosition = textField.getCaretPosition();

					String a = getEditor().getItem().toString(); // user input
					removeAllItems(); // remove all items ? => because if we
										// don't remove items are double and
										// double enetered
					int st = 0;
					addItem(new IncorrectPerson("", "не"));
					for (int i = 0; i < v.size(); i++) {
						if (v.get(i).getName().isEmpty()) {
							continue;
						}
						String A = v.get(i).getName().toLowerCase();
						String B = a.toLowerCase();
						if (A.startsWith(B)) {
							addItem(v.get(i));
							st++;
						} else if (A.contains(B)) {
							addItem(v.get(i));
							st++;
						}
						// add items from vector to combobox again
					}

					getEditor().setItem(new String(a));
					hidePopup();
					if (st != 0) {
						showPopup();
					}
					textField.setCaretPosition(caretPosition);
				}

			}
		});

		this.setRenderer(new ComboRenderer2());
		this.setPreferredSize(new Dimension(200, 30));

	}

	@Override
	public void setSelectedItem(Object item) {
		super.setSelectedItem(item);
		ComboBoxEditor editor = getEditor();
		JTextField textField = (JTextField) editor.getEditorComponent();
		textField.setCaretPosition(0);
	}

	class ComboRenderer2 extends JPanel implements
			ListCellRenderer<IncorrectPerson> {
		JPanel textPanel;
		JLabel text;

		public ComboRenderer2() {
			textPanel = new JPanel();
			textPanel.add(this);
			text = new JLabel();
			text.setOpaque(true);
			// text.setFont(combo.getFont());
			textPanel.add(text);
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends IncorrectPerson> list, IncorrectPerson value,
				int selectedIndex, boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			if (selectedIndex != -1) {
				list.setToolTipText(MainPanel.getHTML_Text(value.getName()));
			}
			if (isSelected) {
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(Color.WHITE);
			}
			text.setBackground(getBackground());

			text.setText(value.getName());

			String incorrectPerson = value.getIcorrect();
			if (incorrectPerson == null || incorrectPerson.equals("не")) {
				text.setForeground(Color.BLACK);
			} else if (incorrectPerson.equals("да")) {
				text.setForeground(Color.RED);
			}
			return text;
		}

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				UIManager.LookAndFeelInfo[] l = UIManager
						.getInstalledLookAndFeels();
				try {
					UIManager.setLookAndFeel(l[0].getClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JPanel panel = new JPanel();
				ClientsListComboBox2 testing;
				testing = new ClientsListComboBox2();
				panel.add(testing);
				JFrame frame = new JFrame();
				frame.getContentPane().add(panel);
				frame.setLocation(300, 30);
				frame.setSize(300, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}

		});

	}

	private void init() {
		SWorker sw = new SWorker();
		try {
			firms = sw.doInBackground();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	class SWorker extends SwingWorker {

		// List f = null;
		ArrayList<IncorrectPerson> firm = null;

		public SWorker() {
			// f = null;
			firm = new ArrayList<IncorrectPerson>();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected ArrayList<IncorrectPerson> doInBackground() throws Exception {
			// TODO Auto-generated method stub

			firm = ClientTable.getClients2(); // Arrays.asList(Conecting.getClients());

			// Collections.sort(firm);
			/*
			 * for(int i = 0;i < f.size();i++) { String s = (String)f.get(i);
			 * firm.add(s); }
			 */
			return firm;
		}

	}

}
