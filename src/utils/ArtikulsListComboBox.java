package utils;

import http.RequestCallback;
import http.reports.GetReportsService;
import models.ArtikulsReports;
import models.NewExtinguishersReports;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static utils.MainPanel.AVAILABLE_ARTIKULS;

public class ArtikulsListComboBox extends JComboBox<String> implements ActionListener {
	ArrayList<String> v;
	public ArrayList<String> artikuls = new ArrayList<String>();

	private JPopupMenu popupMenu = null;
	private final String Copy = "�������";
	private final String Cut = "������";
	private final String Paste = "�������";
	private final Font CURRENT_FONT =
			new Font(Font.MONOSPACED, Font.PLAIN,MainPanel.getFontSize());
    private final String dbTable;
	public ArtikulsListComboBox(String dbTable) {
		this.dbTable = dbTable;

		init();

		this.setEditable(true);
		this.setSelectedItem("");
		/*
		 * BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true,
		 * false); this.addPopupMenuListener( listener );
		 * this.setPrototypeDisplayValue("ItemWWW");
		 */

		this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (getEditor().getItem().toString().length() <= 1) {
					// init();
					v.clear();
					v.addAll(artikuls);
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
					addItem("");
					for (String s : v) {
						if (s.isEmpty()) {
							continue;
						}
						String A = s.toLowerCase();
						String B = a.toLowerCase();

						// STARTSWITH() OR CONTAINS() MAY FAILS SOMETIMES
						// IF WORDS IN LISTS ARE WRITTEN WITH MIX FROM DIFFERENT
						// LANGUAGE CHARACTERS
						// FOR EXAMPLE CHYRYLIC AND LATINIC !!!!
						if (A.startsWith(B) || A.contains(B)) {
							// System.out.printf("fak you (%s) (%s)\n", A, B);
							addItem(s);
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

		createPopupMenu();
		this.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger()) {
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		});

		this.setRenderer(new ComboRenderer());
		this.setPreferredSize(new Dimension(200, 30));
	}

	@Override
	public void setSelectedItem(Object item) {
		super.setSelectedItem(item);
		ComboBoxEditor editor = getEditor();
		JTextField textField = (JTextField) editor.getEditorComponent();
		textField.setCaretPosition(0);
	}

	class ComboRenderer extends DefaultListCellRenderer {

		private JPanel textPanel;
		private JLabel text;
		public ComboRenderer() {
			textPanel = new JPanel();
			textPanel.add(this);
			text = new JLabel();
			text.setOpaque(true);
			textPanel.add(text);
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value, int selectedIndex, boolean isSelected, boolean cellHasFocus) {
			if (selectedIndex != -1) {
				list.setToolTipText(MainPanel
						.getHTML_Text(ArtikulsListComboBox.this
								.getItemAt(selectedIndex)));
			}
			if(isSelected) {
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(Color.WHITE);
			}
			text.setBackground(getBackground());
			text.setText(value.toString());

			return text;//super.getListCellRendererComponent(list, value, selectedIndex, isSelected, cellHasFocus);
		}
	}

	private void createPopupMenu() {
		JMenuItem copyItem = new JMenuItem(Copy);
		JMenuItem cutItem = new JMenuItem(Cut);
		JMenuItem pasteItem = new JMenuItem(Paste);

		popupMenu = new JPopupMenu();

		popupMenu.add(copyItem);
		popupMenu.add(cutItem);
		popupMenu.add(pasteItem);

		Component[] components = popupMenu.getComponents();
		for (Component component : components) {
			component.setFont(CURRENT_FONT);
		}
		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		pasteItem.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		switch (cmd) {
			case Copy:
				((JTextField) this.getEditor().getEditorComponent()).copy();
				break;
			case Cut:
				((JTextField) this.getEditor().getEditorComponent()).cut();
				break;
			case Paste:
				((JTextField) this.getEditor().getEditorComponent()).paste();
				break;
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
				ArtikulsListComboBox testing;
				testing = new ArtikulsListComboBox(AVAILABLE_ARTIKULS);
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

		GetReportsService service = new GetReportsService();

		if(dbTable.equals(AVAILABLE_ARTIKULS)) {
			service.getArtikulsNames(new RequestCallback() {
				@Override
				public <T> void callback(List<T> objects) {
					for(T t : objects) {
						ArtikulsReports artikulsReports = (ArtikulsReports)t;
						artikuls.add(artikulsReports.getArtikul());
					}
					v = new ArrayList<>();
					for (int i = 0; i < artikuls.size(); i++) {
						v.add(artikuls.get(i));
						addItem(v.get(i));
					}
				}
			});
		} else {

			service.getNewExtinguishers(new RequestCallback() {
				@Override
				public <T> void callback(List<T> objects) {
					for(T t : objects) {
						NewExtinguishersReports newExtinguishersReports = (NewExtinguishersReports) t;
						artikuls.add(newExtinguishersReports.getType());
					}
					v = new ArrayList<>();
					for (int i = 0; i < artikuls.size(); i++) {
						v.add(artikuls.get(i));
						addItem(v.get(i));
					}
				}
			});
		}

	}

	public void refreshData() {
		artikuls.clear();
		v.clear();
		this.removeAllItems();
		init();
	}


}
