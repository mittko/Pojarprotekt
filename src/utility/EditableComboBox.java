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

public class EditableComboBox extends JComboBox<Object> {

	Vector<String> v = null;
	Object[] data = null;



	public EditableComboBox(String filePath) {// "Local/brand.txt"
		
		 this.setRenderer(new ComboRenderer());

		data = TextReader.getData(filePath);
		v = new Vector<String>();

		//this.setEditable(true);
		this.setSelectedItem("");

		for (int i = 0; i < data.length; i++) {
			v.add(data[i].toString());
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
					for (String s : v) {
						String A = s.toLowerCase();
						String B = prefix.toLowerCase();
						if (A.startsWith(B)) {
							addItem(s);
							c++;
						} else if (A.contains(B)) {
							addItem(s);
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
				list.setToolTipText(MainPanel.getHTML_Text(EditableComboBox.this.getItemAt(index).toString()));
			}

			return super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				EditableComboBox mc = new EditableComboBox("Local/brand.txt");
				JustFrame jf = new JustFrame(mc);
				jf.pack();
			}

		});

	}

}
