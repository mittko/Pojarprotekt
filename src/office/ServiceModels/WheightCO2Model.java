package office.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheightCO2Model extends DefaultComboBoxModel<String>{

	private final String[] w = {"Вместимост","1.4 кг","2 кг","3.5 кг",
			"5 кг","10 кг","15 кг","20 кг","30 кг"};
	public WheightCO2Model() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}
}
