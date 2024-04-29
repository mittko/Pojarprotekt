package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheighWaterModel extends DefaultComboBoxModel<String> {

	public WheighWaterModel() {
		super();
		String[] w = {"Вместимост", "6 литра", "9 литра", "12 литра"};
		for (String s : w) {
			this.addElement(s);
		}
	}
}
