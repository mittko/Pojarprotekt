package Reports.ReportsEditor;

import javax.swing.DefaultCellEditor;

import Reports.ReportsRenderers.NoEditableCombo;

public class MyComboBoxEditor extends DefaultCellEditor {

	public MyComboBoxEditor(String[] items) {
		
		super(new NoEditableCombo(items));
		
		}
 /*  private static String[] getSubItems(String[] items) {
	   String[] subItems = new String[items.length];
	   for(int i = 0;i < items.length;i++) {
		   subItems[i] = items[i];
	   }
	   return subItems;
   }*/
    
}
