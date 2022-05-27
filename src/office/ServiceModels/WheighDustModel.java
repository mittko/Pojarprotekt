package office.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheighDustModel extends DefaultComboBoxModel<String> {

	private final String[] w = {"Вместимост","1 кг","2 кг","3 кг","4 кг",
			"6 кг","9 кг","12 кг","25 кг","50 кг","100 кг"};
	public WheighDustModel() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}

}
