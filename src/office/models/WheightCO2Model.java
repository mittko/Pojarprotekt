package office.models;

import javax.swing.DefaultComboBoxModel;

public class WheightCO2Model extends DefaultComboBoxModel<String>{

	public WheightCO2Model() {
		super();
		String[] w = {"Вместимост", "1.4 кг", "2 кг", "3.5 кг",
				"5 кг", "10 кг", "15 кг", "20 кг", "30 кг"};
		for (String s : w) {
			this.addElement(s);
		}
	}
}
