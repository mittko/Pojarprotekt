package ServiceOrder.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheighWaterModel extends DefaultComboBoxModel<String> {

	private final String[] w = {"����������","6 �����","9 �����","12 �����"};
	public WheighWaterModel() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}
}
