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
				 Water.патрон.setEnabled(false);
				 Water.патрон.choiced = false;
				 Water.патрон.setBorder(Water.патрон.defaultBorder);
			 } else if(category.equals(category3)) {
				 Water.манометър.setEnabled(false);
				 Water.манометър.choiced = false;
				 Water.манометър.setBorder(Water.манометър.defaultBorder);
				 
				 Water.игличка.setEnabled(false);
				 Water.игличка.choiced = false;
				 Water.игличка.setBorder(Water.игличка.defaultBorder);
				 
				 Water.капачка.setEnabled(false);
				 Water.капачка.choiced = false;
				 Water.капачка.setBorder(Water.капачка.defaultBorder);
			 }
			
			
	        } else
	        if(type.equals(type_Water_Fame)) {
	       
			// category check
			 if(category.equals(category1)) {
				 Vodopenen.патрон.setEnabled(false);
				 Vodopenen.патрон.choiced = false;
				 Vodopenen.патрон.setBorder(Vodopenen.патрон.defaultBorder);
			 } else if(category.equals(category3)) {
				 Vodopenen.манометър.setEnabled(false);
				 Vodopenen.манометър.choiced = false;
				 Vodopenen.манометър.setBorder(Vodopenen.манометър.defaultBorder);
				 
				 Vodopenen.игличка.setEnabled(false);
				 Vodopenen.игличка.choiced = false;
				 Vodopenen.игличка.setBorder(Vodopenen.игличка.defaultBorder);
				 
				 Vodopenen.капачка.setEnabled(false);
				 Vodopenen.капачка.choiced = false;
				 Vodopenen.капачка.setBorder(Vodopenen.капачка.defaultBorder);
			 }
	      } 
	    }
	  private static void setButtonStateAccordinglyWheight(String wheight,String category) {
	    	 if(category.equals(category2)) {
				 if(wheight.equals("1 кг") || wheight.equals("2 кг") || wheight.equals("3 кг")) {
					 Dust.патрон.setEnabled(false);
					 Dust.патрон.choiced = false;
					 Dust.патрон.setBorder(Dust.патрон.defaultBorder);
					 
					 Dust.маркуч.setEnabled(false);
					 Dust.маркуч.choiced = false;
					 Dust.маркуч.setBorder(Dust.маркуч.defaultBorder);
					 
					 Dust.държачЗаМаркуч.setEnabled(false);
					 Dust.държачЗаМаркуч.choiced = false;
					 Dust.държачЗаМаркуч.setBorder(Dust.държачЗаМаркуч.defaultBorder);
					 
					 Dust.струйник4.setEnabled(false);
					 Dust.струйник4.choiced = false;
					 Dust.струйник4.setBorder(Dust.струйник4.defaultBorder);
					 
					 Dust.твърдоХодовоКолело.setEnabled(false);
					 Dust.твърдоХодовоКолело.choiced = false;
					 Dust.твърдоХодовоКолело.setBorder(Dust.твърдоХодовоКолело.defaultBorder);
					 
					 Dust.колелоЗаВисокоТегло.setEnabled(false);
					 Dust.колелоЗаВисокоТегло.choiced = false;
					 Dust.колелоЗаВисокоТегло.setBorder(Dust.колелоЗаВисокоТегло.defaultBorder);
					 
					 Dust.ремонтНаКоличка.setEnabled(false);
					 Dust.ремонтНаКоличка.choiced = false;
					 Dust.ремонтНаКоличка.setBorder(Dust.ремонтНаКоличка.defaultBorder);
					 
					 Dust.боядисванеНаКоличка.setEnabled(false);
					 Dust.боядисванеНаКоличка.choiced = false;
					 Dust.боядисванеНаКоличка.setBorder(Dust.боядисванеНаКоличка.defaultBorder);
				 } else if(wheight.equals("6 кг") || wheight.equals("9 кг") || wheight.equals("12 кг")) {
					 Dust.патрон.setEnabled(false);
					 Dust.патрон.choiced = false;
					 Dust.патрон.setBorder(Dust.патрон.defaultBorder);
					 
					 Dust.струйник4.setEnabled(false);
					 Dust.струйник4.choiced = false;
					 Dust.струйник4.setBorder(Dust.струйник4.defaultBorder);
					 
					 Dust.твърдоХодовоКолело.setEnabled(false);
					 Dust.твърдоХодовоКолело.choiced = false;
					 Dust.твърдоХодовоКолело.setBorder(Dust.твърдоХодовоКолело.defaultBorder);
					 
					 Dust.колелоЗаВисокоТегло.setEnabled(false);
					 Dust.колелоЗаВисокоТегло.choiced = false;
					 Dust.колелоЗаВисокоТегло.setBorder(Dust.колелоЗаВисокоТегло.defaultBorder);
					 
					 Dust.ремонтНаКоличка.setEnabled(false);
					 Dust.ремонтНаКоличка.choiced = false;
					 Dust.ремонтНаКоличка.setBorder(Dust.ремонтНаКоличка.defaultBorder);
					 
					 Dust.боядисванеНаКоличка.setEnabled(false);
					 Dust.боядисванеНаКоличка.choiced = false;
					 Dust.боядисванеНаКоличка.setBorder(Dust.боядисванеНаКоличка.defaultBorder);
				 } else if(wheight.equals("25 кг") || wheight.equals("50 кг") || wheight.equals("100 кг")) {
					 Dust.патрон.setEnabled(false);
					 Dust.патрон.choiced = false;
					 Dust.патрон.setBorder(Dust.патрон.defaultBorder);
				 }
				
			 } else if(category.equals(category4)) {
				 if(wheight.equals("1 кг") || wheight.equals("2 кг") || wheight.equals("3 кг")) {
					 Dust.манометър.setEnabled(false);
					 Dust.манометър.choiced = false;
					 Dust.манометър.setBorder(Dust.манометър.defaultBorder);
					 
					 Dust.игличка.setEnabled(false);
					 Dust.игличка.choiced = false;
					 Dust.игличка.setBorder(Dust.игличка.defaultBorder);
					 
					 Dust.капачка.setEnabled(false);
					 Dust.капачка.choiced = false;
					 Dust.капачка.setBorder(Dust.капачка.defaultBorder);
					 
					 Dust.маркуч.setEnabled(false);
					 Dust.маркуч.choiced = false;
					 Dust.маркуч.setBorder(Dust.маркуч.defaultBorder);
					 
					 Dust.държачЗаМаркуч.setEnabled(false);
					 Dust.държачЗаМаркуч.choiced = false;
					 Dust.държачЗаМаркуч.setBorder(Dust.държачЗаМаркуч.defaultBorder);
					 
					 Dust.твърдоХодовоКолело.setEnabled(false);
					 Dust.твърдоХодовоКолело.choiced = false;
					 Dust.твърдоХодовоКолело.setBorder(Dust.твърдоХодовоКолело.defaultBorder);
					 
					 Dust.колелоЗаВисокоТегло.setEnabled(false);
					 Dust.колелоЗаВисокоТегло.choiced = false;
					 Dust.колелоЗаВисокоТегло.setBorder(Dust.колелоЗаВисокоТегло.defaultBorder);
					 
					 Dust.ремонтНаКоличка.setEnabled(false);
					 Dust.ремонтНаКоличка.choiced = false;
					 Dust.ремонтНаКоличка.setBorder(Dust.ремонтНаКоличка.defaultBorder);
					 
					 Dust.боядисванеНаКоличка.setEnabled(false);
					 Dust.боядисванеНаКоличка.choiced = false;
					 Dust.боядисванеНаКоличка.setBorder(Dust.боядисванеНаКоличка.defaultBorder);
				 } else if(wheight.equals("6 кг") || wheight.equals("9 кг") || wheight.equals("12 кг")) {
					 Dust.манометър.setEnabled(false);
					 Dust.манометър.choiced = false;
					 Dust.манометър.setBorder(Dust.манометър.defaultBorder);
					 
					 Dust.игличка.setEnabled(false);
					 Dust.игличка.choiced = false;
					 Dust.игличка.setBorder(Dust.игличка.defaultBorder);
					 
					 Dust.капачка.setEnabled(false);
					 Dust.капачка.choiced = false;
					 Dust.капачка.setBorder(Dust.капачка.defaultBorder);
					 
					 Dust.струйник4.setEnabled(false);
					 Dust.струйник4.choiced = false;
					 Dust.струйник4.setBorder(Dust.струйник4.defaultBorder);
					 
					 Dust.твърдоХодовоКолело.setEnabled(false);
					 Dust.твърдоХодовоКолело.choiced = false;
					 Dust.твърдоХодовоКолело.setBorder(Dust.твърдоХодовоКолело.defaultBorder);
					 
					 Dust.колелоЗаВисокоТегло.setEnabled(false);
					 Dust.колелоЗаВисокоТегло.choiced = false;
					 Dust.колелоЗаВисокоТегло.setBorder(Dust.колелоЗаВисокоТегло.defaultBorder);
					 
					 Dust.ремонтНаКоличка.setEnabled(false);
					 Dust.ремонтНаКоличка.choiced = false;
					 Dust.ремонтНаКоличка.setBorder(Dust.ремонтНаКоличка.defaultBorder);
					 
					 Dust.боядисванеНаКоличка.setEnabled(false);
					 Dust.боядисванеНаКоличка.choiced = false;
					 Dust.боядисванеНаКоличка.setBorder(Dust.боядисванеНаКоличка.defaultBorder);
				 } else if(wheight.equals("25 кг") || wheight.equals("50 кг") || wheight.equals("100 кг")) {
					 Dust.манометър.setEnabled(false);
					 Dust.манометър.choiced = false;
					 Dust.манометър.setBorder(Dust.манометър.defaultBorder);
					 
					 Dust.игличка.setEnabled(false);
					 Dust.игличка.choiced = false;
					 Dust.игличка.setBorder(Dust.игличка.defaultBorder);
					 
					 Dust.капачка.setEnabled(false);
					 Dust.капачка.choiced = false;
					 Dust.капачка.setBorder(Dust.капачка.defaultBorder);
					 
				 }
				
			 }
			
	    }
	    public static void setPlomba(String type,boolean isTO) {
	    	if(type.equals(type_Prah_BC) || type.equals(type_Prah_ABC)) {
	    		// choiced bydefault
	    		Dust.пломба.setEnabled(true);
	    		Dust.пломба.choiced = true;
	    		Dust.пломба.setBorder(Dust.пломба.choiceBorder);
	    	} else if(type.equals(type_Water)) {
	    		// choiced by default
	    		Water.пломба.setEnabled(true);
	    		Water.пломба.choiced = true;
	    		Water.пломба.setBorder(Dust.пломба.choiceBorder);
	    	} else if(type.equals(type_Water_Fame)) {
	    		// choiced by default
	    		Vodopenen.пломба.setEnabled(true);
	    		Vodopenen.пломба.choiced = true;
	    		Vodopenen.пломба.setBorder(Dust.пломба.choiceBorder);
	    	} else if(type.equals(type_CO2)) {
	    		if(isTO) {
	    		Parts.CO2.пломба.setEnabled(true);
	    		Parts.CO2.пломба.isEditable = true;
	    		Parts.CO2.пломба.choiced = false;
	    		Parts.CO2.пломба.setBorder(Parts.CO2.пломба.defaultBorder);
	    		} else {
	    			// choiced by default
	    		Parts.CO2.пломба.setEnabled(true);
	    		Parts.CO2.пломба.isEditable = false;
	    		Parts.CO2.пломба.choiced = true;
	    		Parts.CO2.пломба.setBorder(Dust.пломба.choiceBorder);
	    		}
	    	}
	    }
	    public static void setEntity(String type,boolean enabled) {
			if(type.equals(type_Prah_BC)) {
				if(enabled) {
					// choiced by default
				Dust.прах_BC.setEnabled(true);
				Dust.прах_BC.choiced = true;
				Dust.прах_BC.setBorder(Dust.прах_BC.choiceBorder);
				
				// enabled false by default
				Dust.прах_ABC.setEnabled(false);
				Dust.прах_ABC.choiced = false;
				Dust.прах_ABC.setBorder(Dust.прах_ABC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.прах_BC.setEnabled(false);
					Dust.прах_BC.choiced = false;
					Dust.прах_BC.setBorder(Dust.прах_BC.defaultBorder);
					
					Dust.прах_ABC.setEnabled(false);
					Dust.прах_ABC.choiced = false;
					Dust.прах_ABC.setBorder(Dust.прах_ABC.defaultBorder);
				}
			} else if(type.equals(type_Prah_ABC)) {
				if(enabled) {
					// choiced by default
				Dust.прах_ABC.setEnabled(true);
				Dust.прах_ABC.choiced = true;
				Dust.прах_ABC.setBorder(Dust.прах_BC.choiceBorder);
				// enabled false by default
				Dust.прах_BC.setEnabled(false);
				Dust.прах_BC.choiced = false;
				Dust.прах_BC.setBorder(Dust.прах_BC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.прах_ABC.setEnabled(false);
					Dust.прах_ABC.choiced = false;
					Dust.прах_ABC.setBorder(Dust.прах_BC.defaultBorder);
					
					Dust.прах_BC.setEnabled(false);
					Dust.прах_BC.choiced = false;
					Dust.прах_BC.setBorder(Dust.прах_BC.defaultBorder);
				}
			} else if(type.equals(type_Water)) {
				if(enabled) {
					// choiced by default
				Water.вода.setEnabled(true);
				Water.вода.choiced = true;
				Water.вода.setBorder(Water.вода.choiceBorder);
				} else {
					// enabled false by default
					Water.вода.setEnabled(false);
					Water.вода.choiced = false;
					Water.вода.setBorder(Water.вода.defaultBorder);
				}
			} else if(type.equals(type_Water_Fame)) {
				if(enabled) {
					// choiced by default
				Vodopenen.вода_пяна.setEnabled(true);
				Vodopenen.вода_пяна.choiced = true;
				Vodopenen.вода_пяна.setBorder(Water.вода.choiceBorder);
				} else {
					// enabled false by default
				Vodopenen.вода_пяна.setEnabled(false);
				Vodopenen.вода_пяна.choiced = false;
				Vodopenen.вода_пяна.setBorder(Water.вода.defaultBorder);
				}
			} else if(type.equals(type_CO2)) {
				if(enabled) {
					// choiced by default
				Parts.CO2.въглероден_диоксид.setEnabled(true);
				Parts.CO2.въглероден_диоксид.choiced = true;
				Parts.CO2.въглероден_диоксид.setBorder(Parts.CO2.въглероден_диоксид.choiceBorder);
				} else {
					// enabled false by default
				Parts.CO2.въглероден_диоксид.setEnabled(false);
				Parts.CO2.въглероден_диоксид.choiced = false;
				Parts.CO2.въглероден_диоксид.setBorder(Parts.CO2.въглероден_диоксид.defaultBorder);
				}
			}
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
