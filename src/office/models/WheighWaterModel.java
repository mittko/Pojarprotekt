package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheighWaterModel extends DefaultComboBoxModel<String> {

	public WheighWaterModel() {
		super();
		String[] w = {"����������", "6 �����", "9 �����", "12 �����"};
		for (String s : w) {
			this.addElement(s);
		}
	}
}
