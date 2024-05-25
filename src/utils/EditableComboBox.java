package utils;

import files.TextReader;
import run.JustFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class EditableComboBox extends JComboBox<Object> {

	Vector<String> v = null;
	Object[] firms = null;



	public EditableComboBox() {
		
		 this.setRenderer(new ComboRenderer());

		firms = TextReader.getData(getDataPath());
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
				EditableComboBox mc = new EditableComboBox();
				JustFrame jf = new JustFrame(mc);
				jf.pack();
			}

		});

	}

	public String getDataPath() {
		return null;
	}

}
