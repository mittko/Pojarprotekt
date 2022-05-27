package office.ServiceModels;

import javax.swing.DefaultComboBoxModel;

public class WheightCO2Model extends DefaultComboBoxModel<String>{

	private final String[] w = {"����������","1.4 ��","2 ��","3.5 ��",
			"5 ��","10 ��","15 ��","20 ��","30 ��"};
	public WheightCO2Model() {
		super();
		for(int i = 0;i < w.length;i++) {
			this.addElement(w[i]);
		}
	}
}
