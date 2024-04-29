package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheightCO2Model extends DefaultComboBoxModel<String>{

	public WheightCO2Model() {
		super();
		String[] w = {"����������", "1.4 ��", "2 ��", "3.5 ��",
				"5 ��", "10 ��", "15 ��", "20 ��", "30 ��"};
		for (String s : w) {
			this.addElement(s);
		}
	}
}
