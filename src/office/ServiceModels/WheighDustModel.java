package office.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheighDustModel extends DefaultComboBoxModel<String> {

	private final String[] w = {"����������","1 ��","2 ��","3 ��","4 ��",
			"6 ��","9 ��","12 ��","25 ��","50 ��","100 ��"};
	public WheighDustModel() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}

}
