package ServiceOrder.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheightWaterFameModel extends DefaultComboBoxModel<String>{

	private final String[] w = {"����������","6 �����","9 �����","12 �����"};
	public WheightWaterFameModel() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}

}
