package Reports.ReportsRenderers;


import javax.swing.JComboBox;

public class NoEditableCombo extends JComboBox<Object>{
	
					public NoEditableCombo(String[] items) {
						    super(items);
				   }
				  @Override
				  public void setSelectedItem(Object o) {
					  super.setSelectedItem(null);
				  }
}
