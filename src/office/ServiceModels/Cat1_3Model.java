package office.ServiceModels;

import utils.MainPanel;

import javax.swing.*;

public class Cat1_3Model extends DefaultComboBoxModel<String>{

	public Cat1_3Model() {
		this.addElement("Категория");
		this.addElement(MainPanel.category1);
		this.addElement(MainPanel.category3);
	}

}
