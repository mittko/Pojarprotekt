package office.ServiceModels;

import utils.MainPanel;

import javax.swing.*;

public class Cat2_4Model extends DefaultComboBoxModel<String>{



	public Cat2_4Model() {
	 
	  this.addElement("Категория");
	  this.addElement(MainPanel.category2);
	  this.addElement(MainPanel.category4);
	}


}
