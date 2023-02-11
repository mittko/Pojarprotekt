//package utility;
//
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//
//import javax.swing.ComboBoxEditor;
//import javax.swing.DefaultListCellRenderer;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//import javax.swing.UIManager;
//
//import db.Client.ClientTable;
//
//public class ClientsListComboBox extends JComboBox<String> {
//	ArrayList<String> v = null;
//	ArrayList<String> firms = null;
//
//	public ClientsListComboBox() {
//		v = new ArrayList<String>();
//		init();
//		/*
//		 * v.add("tlakovanje"); v.add("tlakovec"); v.add("tlakovana cesta");
//		 * v.add("tla&#269;an"); v.add("tlaka"); v.add("slovenec");
//		 * v.add("slovenka"); v.add("slovenija"); v.add("slovar");
//		 * v.add("slovenci"); v.add("&#269;ajra"); v.add("&#269;aranje");
//		 */
//
//		this.setEditable(true);
//		this.setSelectedItem("");
//
//		for (int i = 0; i < firms.size(); i++) {
//			v.add(firms.get(i));
//			this.addItem(v.get(i));
//		}
//
//		this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//
//				if (getEditor().getItem().toString().length() <= 1) {
//					// init();
//					v.clear();
//					for (int i = 0; i < firms.size(); i++) {
//						v.add(firms.get(i));
//					}
//				}
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// enter=10 up=38 down=40
//
//				// TODO Auto-generated method stub
//				if (e.getKeyCode() != 38 && e.getKeyCode() != 40
//						&& e.getKeyCode() != 10) {
//
//					String a = getEditor().getItem().toString(); // user input
//					removeAllItems(); // remove all items ? => because if we
//										// don't remove items are double and
//										// double enetered
//					int st = 0;
//					addItem("");
//					for (int i = 0; i < v.size(); i++) {
//						if (v.get(i).isEmpty()) {
//							continue;
//						}
//						String A = v.get(i).toLowerCase();
//						String B = a.toLowerCase();
//						if (A.startsWith(B)) {
//							addItem(v.get(i));
//							st++;
//						} else if (A.contains(B)) {
//							addItem(v.get(i));
//							st++;
//						}
//						// add items from vector to combobox again
//					}
//
//					getEditor().setItem(new String(a));
//					hidePopup();
//					if (st != 0) {
//						showPopup();
//					}
//				}
//
//			}
//		});
//
//		this.setRenderer(new ComboRenderer());
//		this.setPreferredSize(new Dimension(200, 30));
//
//	}
//
//	@Override
//	public void setSelectedItem(Object item) {
//		super.setSelectedItem(item);
//		ComboBoxEditor editor = getEditor();
//		JTextField textField = (JTextField) editor.getEditorComponent();
//		textField.setCaretPosition(0);
//	}
//
//	class ComboRenderer extends DefaultListCellRenderer {
//
//		public ComboRenderer() {
//
//		}
//
//		@Override
//		public Component getListCellRendererComponent(JList<?> list,
//				Object value, int selectedIndex, boolean b, boolean c) {
//			if (selectedIndex != -1) {
//				list.setToolTipText(MainPanel
//						.getHTML_Text(ClientsListComboBox.this
//								.getItemAt(selectedIndex)));
//			}
//			// System.out.println("value.toString() = " + value.toString());
//			Component comp = super.getListCellRendererComponent(list, value,
//					selectedIndex, b, c);
//
//			return comp;
//		}
//	}
//
//	public static void main(String[] args) {
//
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				UIManager.LookAndFeelInfo[] l = UIManager
//						.getInstalledLookAndFeels();
//				try {
//					UIManager.setLookAndFeel(l[0].getClassName());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				JPanel panel = new JPanel();
//				ClientsListComboBox testing;
//				testing = new ClientsListComboBox();
//				panel.add(testing);
//				JFrame frame = new JFrame();
//				frame.getContentPane().add(panel);
//				frame.setLocation(300, 30);
//				frame.setSize(300, 300);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setVisible(true);
//			}
//
//		});
//
//	}
//
//	private void init() {
//		SWorker sw = new SWorker();
//		try {
//			firms = sw.doInBackground();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//
//	class SWorker extends SwingWorker {
//
//		// List f = null;
//		ArrayList<String> firm = null;
//
//		public SWorker() {
//			// f = null;
//			firm = new ArrayList<String>();
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		protected ArrayList<String> doInBackground() throws Exception {
//			// TODO Auto-generated method stub
//
//			firm = ClientTable.getClients(); // Arrays.asList(Conecting.getClients());
//
//			// Collections.sort(firm);
//			/*
//			 * for(int i = 0;i < f.size();i++) { String s = (String)f.get(i);
//			 * firm.add(s); }
//			 */
//			return firm;
//		}
//
//	}
//
// }
