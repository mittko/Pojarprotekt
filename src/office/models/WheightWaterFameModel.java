package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheightWaterFameModel extends DefaultComboBoxModel<String>{

	public WheightWaterFameModel() {
		super();
		String[] w = {"����������", "6 �����", "9 �����", "12 �����"};
		for (String s : w) {
			this.addElement(s);
		}
	}

}
