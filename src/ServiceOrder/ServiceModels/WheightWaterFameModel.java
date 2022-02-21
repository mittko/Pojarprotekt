package ServiceOrder.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheightWaterFameModel extends DefaultComboBoxModel<String>{

	private final String[] w = {"Вместимост","6 литра","9 литра","12 литра"};
	public WheightWaterFameModel() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}

}
