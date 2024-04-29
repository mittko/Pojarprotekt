package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheightWaterFameModel extends DefaultComboBoxModel<String>{

	public WheightWaterFameModel() {
		super();
		String[] w = {"Вместимост", "6 литра", "9 литра", "12 литра"};
		for (String s : w) {
			this.addElement(s);
		}
	}

}
