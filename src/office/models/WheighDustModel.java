package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheighDustModel extends DefaultComboBoxModel<String> {

	public WheighDustModel() {
		super();
		String[] w = {"����������", "1 ��", "2 ��", "3 ��", "4 ��",
				"6 ��", "9 ��", "12 ��", "25 ��", "50 ��", "100 ��"};
		for (String s : w) {
			this.addElement(s);
		}
	}

}
