package Parts;

import utility.CustomButton;
import utility.GradientPanel;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CO2 extends MainPanel {
	
	public static CustomButton пломба = null;
	public static CustomButton въглероден_диоксид = null;
	
	
	public ArrayList<CustomButton> list = null;
	public CO2(Dimension dim) {
		list = new ArrayList<CustomButton>();
		JPanel block = new GradientPanel() ;
		
		block.setLayout(new GridLayout(4, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
		JPanel pane1 = new JPanel();
		pane1.setOpaque(false);
	//	int buttonHeight = block.getPreferredSize().height / 4; // 100
		int buttonVGap = 5;
        int buttonHGap = 20;
	
        Dimension buttonDimension = new Dimension(
	    		(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));
        
		CustomButton бързВентил_Модел1 = new CustomButton();
		бързВентил_Модел1.setPreferredSize(buttonDimension);
		бързВентил_Модел1.setAutoSizedIcon(бързВентил_Модел1, new LoadIcon().setIcons("PARTS2/бърз вентил 3.5.gif"));
	//	бързВентил_Модел1.setIcon(setIcons("PARTS/бърз вентил 3.5.gif"));
		бързВентил_Модел1.setToolTipText(getHTML_Text(BarzVentil1_Model));
		бързВентил_Модел1.setName(BarzVentil1_Model);
	    
		CustomButton бързВентил_Модел2 = new CustomButton();
		бързВентил_Модел2.setPreferredSize(buttonDimension);
		бързВентил_Модел2.setAutoSizedIcon(бързВентил_Модел2, new LoadIcon().setIcons("PARTS2/бърз вентил 5.gif"));
	 // 	бързВентил_Модел2.setIcon(setIcons("PARTS2/бърз вентил 5.gif"));
		бързВентил_Модел2.setToolTipText(getHTML_Text(BarzVentil2_Model));
		бързВентил_Модел2.setName(BarzVentil2_Model);
        
		CustomButton бързВентил_Модел3 = new CustomButton();
		бързВентил_Модел3.setPreferredSize(buttonDimension);
		бързВентил_Модел3.setAutoSizedIcon(бързВентил_Модел3, new LoadIcon().setIcons("PARTS2/бърз вентил 30.gif"));
	
	//	бързВентил_Модел3.setIcon(setIcons("PARTS/бърз вентил 30.gif"));
		бързВентил_Модел3.setToolTipText(getHTML_Text(BarzVentil3_Model));
		бързВентил_Модел3.setName(BarzVentil3_Model);
        
		CustomButton бързВентил_Модел3_Права_Резба = new CustomButton();
		бързВентил_Модел3_Права_Резба.setPreferredSize(buttonDimension);
		бързВентил_Модел3_Права_Резба.setAutoSizedIcon(бързВентил_Модел3_Права_Резба, new LoadIcon().setIcons("PARTS2/бърз вентил 30 права резба.gif"));
	
	//	бързВентил_Модел3_Права_Резба.setIcon(setIcons("PARTS/бърз вентил 30 права резба.gif"));
		бързВентил_Модел3_Права_Резба.setToolTipText(getHTML_Text(BarzVentil3Model_Prava_Rezba));
		бързВентил_Модел3_Права_Резба.setName(BarzVentil3Model_Prava_Rezba);
        
		CustomButton шпленд = new CustomButton();
		шпленд.setPreferredSize(buttonDimension);
		шпленд.setAutoSizedIcon(шпленд, new LoadIcon().setIcons("PARTS2/шпленд.gif"));
		//шпленд.setIcon(setIcons("PARTS/шпленд.gif"));
		шпленд.setToolTipText(getHTML_Text(Sphlend));
		шпленд.setName(Sphlend);
		
		
		list.add(бързВентил_Модел1);
		list.add(бързВентил_Модел2);
		list.add(бързВентил_Модел3);
		list.add(бързВентил_Модел3_Права_Резба);
		list.add(шпленд);
        
		pane1.add(бързВентил_Модел1);
		pane1.add(бързВентил_Модел2);
		pane1.add(бързВентил_Модел3);
		pane1.add(бързВентил_Модел3_Права_Резба);
		pane1.add(шпленд);
		
		
		JPanel pane2 = new JPanel();
		pane2.setOpaque(false);
		
		пломба = new CustomButton();
		пломба.setPreferredSize(buttonDimension);
		пломба.setAutoSizedIcon(пломба, new LoadIcon().setIcons("PARTS2/пломба.gif"));
	//	пломба.setIcon(setIcons("PARTS/пломба.gif"));
		пломба.setToolTipText(getHTML_Text(Plomba));
		пломба.setName(Plomba);
		пломба.isEditable = false;
		
		CustomButton снегообразувател_модел1 = new CustomButton();
		снегообразувател_модел1.setPreferredSize(buttonDimension);
		снегообразувател_модел1.setAutoSizedIcon(снегообразувател_модел1, new LoadIcon().setIcons("PARTS2/снегообразувател 2 кг.gif"));
//		снегообразувател_модел1.setIcon(setIcons("PARTS/снегообразувател 2кг.gif"));
		снегообразувател_модел1.setToolTipText(getHTML_Text(Snegoobrazuvatel1_Model));
		снегообразувател_модел1.setName(Snegoobrazuvatel1_Model);
		
		CustomButton снегообразувател_модел2 = new CustomButton();
		снегообразувател_модел2.setPreferredSize(buttonDimension);
		снегообразувател_модел2.setAutoSizedIcon(снегообразувател_модел2, new LoadIcon().setIcons("PARTS2/снегообразувател 5 кг.gif"));
	//	снегообразувател_модел2.setIcon(setIcons("PARTS/снегообразувател 5кг.gif"));
		снегообразувател_модел2.setToolTipText(getHTML_Text(Snegoobrazuvatel2_Model));
		снегообразувател_модел2.setName(Snegoobrazuvatel2_Model);
		
		CustomButton снегообразувател_модел3 = new CustomButton();
		снегообразувател_модел3.setPreferredSize(buttonDimension);
		снегообразувател_модел3.setAutoSizedIcon(снегообразувател_модел3, new LoadIcon().setIcons("PARTS2/снегообразувател 30 кг.gif"));
	//	снегообразувател_модел3.setIcon(setIcons("PARTS/снегообразувател 30кг.gif"));
		снегообразувател_модел3.setToolTipText(getHTML_Text(Snegoobrazuvatel3_Model));
		снегообразувател_модел3.setName(Snegoobrazuvatel3_Model);
		
		CustomButton сонда = new CustomButton();
		сонда.setPreferredSize(buttonDimension);
		сонда.setAutoSizedIcon(сонда, new LoadIcon().setIcons("PARTS2/сонда.gif"));
//		сонда.setIcon(setIcons("PARTS/сонда.gif"));
		сонда.setToolTipText(getHTML_Text(Sonda));
		сонда.setName(Sonda);
		
		list.add(пломба);
		list.add(снегообразувател_модел1);
		list.add(снегообразувател_модел2);
		list.add(снегообразувател_модел3);
		list.add(сонда);
		
		pane2.add(пломба);
		pane2.add(снегообразувател_модел1);
		pane2.add(снегообразувател_модел2);
		pane2.add(снегообразувател_модел3);
		pane2.add(сонда);
		
		JPanel pane3 = new JPanel();
		pane3.setOpaque(false);
		
		CustomButton дръжка = new CustomButton();
		дръжка.setPreferredSize(buttonDimension);
		дръжка.setAutoSizedIcon(дръжка, new LoadIcon().setIcons("PARTS2/дръжка.gif"));
	//	дръжка.setIcon(setIcons("PARTS/drajka2.png"));
		дръжка.setToolTipText(getHTML_Text(DrajkaZaPojarogasitel));
		дръжка.setName(DrajkaZaPojarogasitel);
		
		CustomButton запресоване_накрайник = new CustomButton();
		запресоване_накрайник.setPreferredSize(buttonDimension);
		запресоване_накрайник.setAutoSizedIcon(запресоване_накрайник, new LoadIcon().setIcons("PARTS2/накрайник.gif"));
	//	запресоване_накрайник.setIcon(setIcons("PARTS/накрайник.gif"));
		запресоване_накрайник.setToolTipText(getHTML_Text(ZapresovaneNaNakrainik));
		запресоване_накрайник.setName(ZapresovaneNaNakrainik);
		
		CustomButton твърдо_колело = new CustomButton();
		твърдо_колело.setPreferredSize(buttonDimension);
		твърдо_колело.setAutoSizedIcon(твърдо_колело, new LoadIcon().setIcons("PARTS2/твърдо колело.gif"));
	//	твърдо_колело.setIcon(setIcons("PARTS/твърдо колело.gif"));
		твърдо_колело.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		твърдо_колело.setName(TvardoHodovoKolelo);
		
		CustomButton колело_високо_тегло = new CustomButton();
		колело_високо_тегло.setPreferredSize(buttonDimension);
		колело_високо_тегло.setAutoSizedIcon(колело_високо_тегло, new LoadIcon().setIcons("PARTS2/колело за високо тегло.gif"));
	//	колело_високо_тегло.setIcon(setIcons("PARTS/колело за високо тегло.gif"));
		колело_високо_тегло.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		колело_високо_тегло.setName(KoleloZaVisokoTeglo);
		
		CustomButton ремонт_количка = new CustomButton();
		ремонт_количка.setPreferredSize(buttonDimension);
		ремонт_количка.setAutoSizedIcon(ремонт_количка, new LoadIcon().setIcons("PARTS2/ремонтКоличка.gif"));
	//	ремонт_количка.setIcon(setIcons("PARTS/ремонтКоличка.gif"));
		ремонт_количка.setToolTipText(getHTML_Text(RemontKolicka));
		ремонт_количка.setName(RemontKolicka);
		
		pane3.add(дръжка);
		pane3.add(запресоване_накрайник);
		pane3.add(твърдо_колело);
		pane3.add(колело_високо_тегло);
		pane3.add(ремонт_количка);
		
		list.add(дръжка);
		list.add(запресоване_накрайник);
		list.add(твърдо_колело);
		list.add(колело_високо_тегло);
		list.add(ремонт_количка);

		JPanel pane4 = new JPanel();
		pane4.setOpaque(false);
		
		CustomButton боя_количка = new CustomButton();
		 боя_количка.setPreferredSize(buttonDimension);
		боя_количка.setAutoSizedIcon( боя_количка, new LoadIcon().setIcons("PARTS2/бояКоличка.gif"));
	//	боя_количка.setIcon(setIcons("PARTS/бояКоличка.gif"));
		боя_количка.setToolTipText(getHTML_Text(BoyaKolichka));
		боя_количка.setName(BoyaKolichka);
		
		CustomButton боя_пожарогасител = new CustomButton();
		боя_пожарогасител.setPreferredSize(buttonDimension);
		боя_пожарогасител.setAutoSizedIcon(боя_пожарогасител, new LoadIcon().setIcons("PARTS2/боя.gif"));
	//	боя_пожарогасител.setIcon(setIcons("PARTS/боя.gif"));
		боя_пожарогасител.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		боя_пожарогасител.setName(BoyaPojarogasitel);
		
		CustomButton етикет = new CustomButton();
		етикет.setPreferredSize(buttonDimension);
		етикет.setAutoSizedIcon(етикет, new LoadIcon().setIcons("PARTS2/co2Etiket.png"));
	//	етикет.setIcon(setIcons("PARTS/co2Etiket.png"));
		етикет.setToolTipText(getHTML_Text(Etiket));
		етикет.setName(Etiket);
		
		
		// enabled for now
		CustomButton съд = new CustomButton();
		съд.setPreferredSize(buttonDimension);
		съд.setAutoSizedIcon(съд, new LoadIcon().setIcons("PARTS2/съд.gif"));
	//	съд.setIcon(setIcons("PARTS/съдCO2.gif"));
		съд.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		съд.setName(SadZaGasitelnoVeshtestvo);
	    
	    въглероден_диоксид = new CustomButton();
	    въглероден_диоксид.setPreferredSize(buttonDimension);
	    въглероден_диоксид.setAutoSizedIcon(въглероден_диоксид, new LoadIcon().setIcons("PARTS2/gas.gif"));
	//    въглероден_диоксид.setIcon(setIcons("PARTS2/gas.gif"));
	    въглероден_диоксид.setToolTipText(getHTML_Text(GasitelnoVeshtestvoCO2));
	    въглероден_диоксид.setName(GasitelnoVeshtestvoCO2);
	    въглероден_диоксид.isEditable = false;
	
		//
		list.add(боя_количка);
		list.add(боя_пожарогасител);
		list.add(етикет);
		list.add(съд);  
		list.add(въглероден_диоксид);
		
		
		pane4.add(боя_количка);
		pane4.add(боя_пожарогасител);
		pane4.add(етикет);
		pane4.add(съд);
		pane4.add(въглероден_диоксид);
	
//		block.setLayout(new GridLayout(4, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, 4 * buttonHeight));
//		
//		pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane1.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane1);
//		pane2.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane2.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane2);
//		pane3.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane3.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane3);
//		pane4.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane4.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane4);
		this.add(block);
		
	//	this.setPreferredSize(block.getPreferredSize());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    //       Framer f = new Framer(new CO2());
	}

}
