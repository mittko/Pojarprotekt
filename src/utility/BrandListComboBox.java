package utility;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import run.JustFrame;
import Local.TextReader;

public class BrandListComboBox extends JComboBox<Object> {

	Vector<String> v = null;
	Object[] firms = null;



	public BrandListComboBox() {
		
		 this.setRenderer(new ComboRenderer());

		firms = TextReader.getData("Local/brand.txt");
		v = new Vector<String>();

		//this.setEditable(true);
		this.setSelectedItem("");

		for (int i = 0; i < firms.length; i++) {
			v.add(firms[i].toString());
			this.addItem(v.get(i));
		}

		this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() != 38 && ke.getKeyCode() != 40
						&& ke.getKeyCode() != 10) {
					String prefix = getEditor().getItem().toString();
					removeAllItems();
					int c = 0;
					addItem("");
					for (int i = 0; i < v.size(); i++) {
						String A = v.get(i).toLowerCase();
						String B = prefix.toLowerCase();
						if (A.startsWith(B)) {
							addItem(v.get(i));
							c++;
						} else if(A.contains(B)) {
							addItem(v.get(i));
							c++;
						}
					}
					getEditor().setItem(prefix);

					hidePopup();
					if (c > 0) {
						showPopup();
					}
				}
			}
		});

	}

	class ComboRenderer extends DefaultListCellRenderer {

		public ComboRenderer() {

		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			if (index != -1) {
				list.setToolTipText(MainPanel.getHTML_Text(BrandListComboBox.this.getItemAt(index).toString()));
			}
			Component c = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);

			return c;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BrandListComboBox mc = new BrandListComboBox();
				JustFrame jf = new JustFrame(mc);
				jf.pack();
			}

		});

	}

}
