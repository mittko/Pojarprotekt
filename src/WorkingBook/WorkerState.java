package WorkingBook;

import Parts.Dust;
import Parts.Vodopenen;
import Parts.Water;
import utility.MainPanel;

public class WorkerState extends MainPanel {

	public WorkerState() {
		
	}
	  public static void setButtonStateAccordinglyCategory(String type,String wheight,String category) {

	        if(type.equals(type_Prah_BC) || type.equals(type_Prah_ABC)) {
	      
			// category check
			 if(category.equals(category2)) {
				setButtonStateAccordinglyWheight(wheight,category2);
			 } else if(category.equals(category4)) {
				setButtonStateAccordinglyWheight(wheight,category4);
			 }
			
	         } else if(type.equals(type_Water)) {
	     
			// category check
			 if(category.equals(category1)) {
				 Water.������.setEnabled(false);
				 Water.������.choiced = false;
				 Water.������.setBorder(Water.������.defaultBorder);
			 } else if(category.equals(category3)) {
				 Water.���������.setEnabled(false);
				 Water.���������.choiced = false;
				 Water.���������.setBorder(Water.���������.defaultBorder);
				 
				 Water.�������.setEnabled(false);
				 Water.�������.choiced = false;
				 Water.�������.setBorder(Water.�������.defaultBorder);
				 
				 Water.�������.setEnabled(false);
				 Water.�������.choiced = false;
				 Water.�������.setBorder(Water.�������.defaultBorder);
			 }
			
			
	        } else
	        if(type.equals(type_Water_Fame)) {
	       
			// category check
			 if(category.equals(category1)) {
				 Vodopenen.������.setEnabled(false);
				 Vodopenen.������.choiced = false;
				 Vodopenen.������.setBorder(Vodopenen.������.defaultBorder);
			 } else if(category.equals(category3)) {
				 Vodopenen.���������.setEnabled(false);
				 Vodopenen.���������.choiced = false;
				 Vodopenen.���������.setBorder(Vodopenen.���������.defaultBorder);
				 
				 Vodopenen.�������.setEnabled(false);
				 Vodopenen.�������.choiced = false;
				 Vodopenen.�������.setBorder(Vodopenen.�������.defaultBorder);
				 
				 Vodopenen.�������.setEnabled(false);
				 Vodopenen.�������.choiced = false;
				 Vodopenen.�������.setBorder(Vodopenen.�������.defaultBorder);
			 }
	      } 
	    }
	  private static void setButtonStateAccordinglyWheight(String wheight,String category) {
	    	 if(category.equals(category2)) {
				 if(wheight.equals("1 ��") || wheight.equals("2 ��") || wheight.equals("3 ��")) {
					 Dust.������.setEnabled(false);
					 Dust.������.choiced = false;
					 Dust.������.setBorder(Dust.������.defaultBorder);
					 
					 Dust.������.setEnabled(false);
					 Dust.������.choiced = false;
					 Dust.������.setBorder(Dust.������.defaultBorder);
					 
					 Dust.��������������.setEnabled(false);
					 Dust.��������������.choiced = false;
					 Dust.��������������.setBorder(Dust.��������������.defaultBorder);
					 
					 Dust.��������4.setEnabled(false);
					 Dust.��������4.choiced = false;
					 Dust.��������4.setBorder(Dust.��������4.defaultBorder);
					 
					 Dust.������������������.setEnabled(false);
					 Dust.������������������.choiced = false;
					 Dust.������������������.setBorder(Dust.������������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
					 
					 Dust.���������������.setEnabled(false);
					 Dust.���������������.choiced = false;
					 Dust.���������������.setBorder(Dust.���������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
				 } else if(wheight.equals("6 ��") || wheight.equals("9 ��") || wheight.equals("12 ��")) {
					 Dust.������.setEnabled(false);
					 Dust.������.choiced = false;
					 Dust.������.setBorder(Dust.������.defaultBorder);
					 
					 Dust.��������4.setEnabled(false);
					 Dust.��������4.choiced = false;
					 Dust.��������4.setBorder(Dust.��������4.defaultBorder);
					 
					 Dust.������������������.setEnabled(false);
					 Dust.������������������.choiced = false;
					 Dust.������������������.setBorder(Dust.������������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
					 
					 Dust.���������������.setEnabled(false);
					 Dust.���������������.choiced = false;
					 Dust.���������������.setBorder(Dust.���������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
				 } else if(wheight.equals("25 ��") || wheight.equals("50 ��") || wheight.equals("100 ��")) {
					 Dust.������.setEnabled(false);
					 Dust.������.choiced = false;
					 Dust.������.setBorder(Dust.������.defaultBorder);
				 }
				
			 } else if(category.equals(category4)) {
				 if(wheight.equals("1 ��") || wheight.equals("2 ��") || wheight.equals("3 ��")) {
					 Dust.���������.setEnabled(false);
					 Dust.���������.choiced = false;
					 Dust.���������.setBorder(Dust.���������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
					 Dust.������.setEnabled(false);
					 Dust.������.choiced = false;
					 Dust.������.setBorder(Dust.������.defaultBorder);
					 
					 Dust.��������������.setEnabled(false);
					 Dust.��������������.choiced = false;
					 Dust.��������������.setBorder(Dust.��������������.defaultBorder);
					 
					 Dust.������������������.setEnabled(false);
					 Dust.������������������.choiced = false;
					 Dust.������������������.setBorder(Dust.������������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
					 
					 Dust.���������������.setEnabled(false);
					 Dust.���������������.choiced = false;
					 Dust.���������������.setBorder(Dust.���������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
				 } else if(wheight.equals("6 ��") || wheight.equals("9 ��") || wheight.equals("12 ��")) {
					 Dust.���������.setEnabled(false);
					 Dust.���������.choiced = false;
					 Dust.���������.setBorder(Dust.���������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
					 Dust.��������4.setEnabled(false);
					 Dust.��������4.choiced = false;
					 Dust.��������4.setBorder(Dust.��������4.defaultBorder);
					 
					 Dust.������������������.setEnabled(false);
					 Dust.������������������.choiced = false;
					 Dust.������������������.setBorder(Dust.������������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
					 
					 Dust.���������������.setEnabled(false);
					 Dust.���������������.choiced = false;
					 Dust.���������������.setBorder(Dust.���������������.defaultBorder);
					 
					 Dust.�������������������.setEnabled(false);
					 Dust.�������������������.choiced = false;
					 Dust.�������������������.setBorder(Dust.�������������������.defaultBorder);
				 } else if(wheight.equals("25 ��") || wheight.equals("50 ��") || wheight.equals("100 ��")) {
					 Dust.���������.setEnabled(false);
					 Dust.���������.choiced = false;
					 Dust.���������.setBorder(Dust.���������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
					 Dust.�������.setEnabled(false);
					 Dust.�������.choiced = false;
					 Dust.�������.setBorder(Dust.�������.defaultBorder);
					 
				 }
				
			 }
			
	    }
	    public static void setPlomba(String type,boolean isTO) {
	    	if(type.equals(type_Prah_BC) || type.equals(type_Prah_ABC)) {
	    		// choiced bydefault
	    		Dust.������.setEnabled(true);
	    		Dust.������.choiced = true;
	    		Dust.������.setBorder(Dust.������.choiceBorder);
	    	} else if(type.equals(type_Water)) {
	    		// choiced by default
	    		Water.������.setEnabled(true);
	    		Water.������.choiced = true;
	    		Water.������.setBorder(Dust.������.choiceBorder);
	    	} else if(type.equals(type_Water_Fame)) {
	    		// choiced by default
	    		Vodopenen.������.setEnabled(true);
	    		Vodopenen.������.choiced = true;
	    		Vodopenen.������.setBorder(Dust.������.choiceBorder);
	    	} else if(type.equals(type_CO2)) {
	    		if(isTO) {
	    		Parts.CO2.������.setEnabled(true);
	    		Parts.CO2.������.isEditable = true;
	    		Parts.CO2.������.choiced = false;
	    		Parts.CO2.������.setBorder(Parts.CO2.������.defaultBorder);
	    		} else {
	    			// choiced by default
	    		Parts.CO2.������.setEnabled(true);
	    		Parts.CO2.������.isEditable = false;
	    		Parts.CO2.������.choiced = true;
	    		Parts.CO2.������.setBorder(Dust.������.choiceBorder);
	    		}
	    	}
	    }
	    public static void setEntity(String type,boolean enabled) {
			if(type.equals(type_Prah_BC)) {
				if(enabled) {
					// choiced by default
				Dust.����_BC.setEnabled(true);
				Dust.����_BC.choiced = true;
				Dust.����_BC.setBorder(Dust.����_BC.choiceBorder);
				
				// enabled false by default
				Dust.����_ABC.setEnabled(false);
				Dust.����_ABC.choiced = false;
				Dust.����_ABC.setBorder(Dust.����_ABC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.����_BC.setEnabled(false);
					Dust.����_BC.choiced = false;
					Dust.����_BC.setBorder(Dust.����_BC.defaultBorder);
					
					Dust.����_ABC.setEnabled(false);
					Dust.����_ABC.choiced = false;
					Dust.����_ABC.setBorder(Dust.����_ABC.defaultBorder);
				}
			} else if(type.equals(type_Prah_ABC)) {
				if(enabled) {
					// choiced by default
				Dust.����_ABC.setEnabled(true);
				Dust.����_ABC.choiced = true;
				Dust.����_ABC.setBorder(Dust.����_BC.choiceBorder);
				// enabled false by default
				Dust.����_BC.setEnabled(false);
				Dust.����_BC.choiced = false;
				Dust.����_BC.setBorder(Dust.����_BC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.����_ABC.setEnabled(false);
					Dust.����_ABC.choiced = false;
					Dust.����_ABC.setBorder(Dust.����_BC.defaultBorder);
					
					Dust.����_BC.setEnabled(false);
					Dust.����_BC.choiced = false;
					Dust.����_BC.setBorder(Dust.����_BC.defaultBorder);
				}
			} else if(type.equals(type_Water)) {
				if(enabled) {
					// choiced by default
				Water.����.setEnabled(true);
				Water.����.choiced = true;
				Water.����.setBorder(Water.����.choiceBorder);
				} else {
					// enabled false by default
					Water.����.setEnabled(false);
					Water.����.choiced = false;
					Water.����.setBorder(Water.����.defaultBorder);
				}
			} else if(type.equals(type_Water_Fame)) {
				if(enabled) {
					// choiced by default
				Vodopenen.����_����.setEnabled(true);
				Vodopenen.����_����.choiced = true;
				Vodopenen.����_����.setBorder(Water.����.choiceBorder);
				} else {
					// enabled false by default
				Vodopenen.����_����.setEnabled(false);
				Vodopenen.����_����.choiced = false;
				Vodopenen.����_����.setBorder(Water.����.defaultBorder);
				}
			} else if(type.equals(type_CO2)) {
				if(enabled) {
					// choiced by default
				Parts.CO2.����������_�������.setEnabled(true);
				Parts.CO2.����������_�������.choiced = true;
				Parts.CO2.����������_�������.setBorder(Parts.CO2.����������_�������.choiceBorder);
				} else {
					// enabled false by default
				Parts.CO2.����������_�������.setEnabled(false);
				Parts.CO2.����������_�������.choiced = false;
				Parts.CO2.����������_�������.setBorder(Parts.CO2.����������_�������.defaultBorder);
				}
			}
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
