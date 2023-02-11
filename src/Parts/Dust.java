package Parts;


import utils.CustomButton;
import utils.GradientPanel;
import utils.LoadIcon;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dust extends MainPanel {

	public ArrayList<CustomButton> list = null;
	public static CustomButton пломба = null;  // пломба
	public static CustomButton прах_BC = null; // прах ВС
	public static CustomButton прах_ABC = null; // прах АВС
	public static CustomButton патрон = null;
	public static CustomButton манометър = null;
	public static CustomButton игличка = null;
	public static CustomButton капачка = null;
	public static CustomButton маркуч = null;
	public static CustomButton държачЗаМаркуч = null;
	public static CustomButton струйник4 = null;
	public static CustomButton твърдоХодовоКолело = null;
	public static CustomButton колелоЗаВисокоТегло = null;
	public static CustomButton ремонтНаКоличка = null;
	public static CustomButton боядисванеНаКоличка  = null;
	
	public Dust(Dimension dim) {
		list = new ArrayList<CustomButton>();
		
		JPanel block = new GradientPanel() ;
//		int buttonHeight =  block.getPreferredSize().height / 5; // old -> 100
			int buttonVGap = 5;
	        int buttonHGap = 20;
		GridLayout paneLayout = new GridLayout(0, 5, buttonHGap, buttonVGap);
		JPanel pane1 = new JPanel();
		pane1.setLayout(paneLayout);
		pane1.setOpaque(false);
		
		JPanel pane2 = new JPanel();
		pane2.setLayout(paneLayout);
		pane2.setOpaque(false);
		
		JPanel pane3 = new JPanel();
		pane3.setLayout(paneLayout);
		pane3.setOpaque(false);
		
		JPanel pane4 = new JPanel();
		pane4.setLayout(paneLayout);
		pane4.setOpaque(false);
		
		JPanel pane5 = new JPanel();
		pane5.setLayout(paneLayout);
		pane5.setOpaque(false);
		
		block.setLayout(new GridLayout(5, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
	   Dimension buttonDimension =  new Dimension(new Dimension(
				(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15)
				));
	
		CustomButton  глава = new CustomButton();
		глава.setPreferredSize(buttonDimension);
		глава.setAutoSizedIcon(глава, new LoadIcon().setIcons("PARTS2/глава.gif"));
	//	глава.setIcon(setIcons("PARTS/глава.gif"));
		
	    глава.setToolTipText(getHTML_Text(Glava));
	    глава.setName(Glava);
	  
		манометър = new CustomButton();
		манометър.setPreferredSize(buttonDimension);
		манометър.setAutoSizedIcon(манометър, new LoadIcon().setIcons("PARTS2/манометър.gif"));
//		манометър.setIcon(setIcons("PARTS/манометър.gif"));
        манометър.setToolTipText(getHTML_Text(Manometar));
        манометър.setName(Manometar);
        
    	CustomButton шпленд = new CustomButton();
    	шпленд.setPreferredSize(buttonDimension);
		шпленд.setAutoSizedIcon(шпленд, new LoadIcon().setIcons("PARTS2/шпленд.gif"));
	//	шпленд.setIcon(setIcons("PARTS/шпленд.gif"));
		шпленд.setToolTipText(getHTML_Text(Sphlend));
		шпленд.setName(Sphlend);
		
		CustomButton о_пръстен = new CustomButton();
		о_пръстен.setPreferredSize(buttonDimension);
		о_пръстен.setAutoSizedIcon(о_пръстен, new LoadIcon().setIcons("PARTS2/о-пръстен.gif"));
	//	о_пръстен.setIcon(setIcons("PARTS/о-пръстен.gif"));
		о_пръстен.setToolTipText(getHTML_Text(Uplatnenie));
		о_пръстен.setName(Uplatnenie);
	
	
        
    	CustomButton затвор = new CustomButton();
    	затвор.setPreferredSize(buttonDimension);
		затвор.setAutoSizedIcon(затвор, new LoadIcon().setIcons("PARTS2/затвор.gif"));
	//	затвор.setIcon(setIcons("PARTS/затвор.gif"));
		затвор.setToolTipText(getHTML_Text(Zatvor));
		затвор.setName(Zatvor);
		
		
	
	
		пломба = new CustomButton();
		пломба.setPreferredSize(buttonDimension);
		пломба.setAutoSizedIcon(пломба, new LoadIcon().setIcons("PARTS2/пломба.gif"));
//		пломба.setIcon(setIcons("PARTS/пломба.gif"));
		пломба.setToolTipText(getHTML_Text(Plomba));
		пломба.setName(Plomba);
		пломба.isEditable = false;
		
		маркуч = new CustomButton();
		маркуч.setPreferredSize(buttonDimension);
		маркуч.setAutoSizedIcon(маркуч, new LoadIcon().setIcons("PARTS2/маркуч2.gif"));
   //     маркуч.setIcon(setIcons("PARTS/маркуч.gif"));
        маркуч.setToolTipText(getHTML_Text(Markuch));
        маркуч.setName(Markuch);
        
		държачЗаМаркуч = new CustomButton();
		държачЗаМаркуч.setPreferredSize(buttonDimension);
		държачЗаМаркуч.setAutoSizedIcon(държачЗаМаркуч, new LoadIcon().setIcons("PARTS2/държач.gif"));
    //    държачЗаМаркуч.setIcon(setIcons("PARTS/държач.gif"));
        държачЗаМаркуч.setToolTipText(getHTML_Text(DarjachZaMarkuch));
        държачЗаМаркуч.setName(DarjachZaMarkuch);
        
        CustomButton пружина = new CustomButton();
        пружина.setPreferredSize(buttonDimension);
		пружина.setAutoSizedIcon(пружина, new LoadIcon().setIcons("PARTS2/пружина.gif"));
//		пружина.setIcon(setIcons("PARTS/пружина.gif"));
		пружина.setToolTipText(getHTML_Text(Prujina));
		пружина.setName(Prujina);
		
		CustomButton струйник = new CustomButton();
		струйник.setPreferredSize(buttonDimension);
		струйник.setAutoSizedIcon(струйник, new LoadIcon().setIcons("PARTS2/струйник.gif"));
	//	струйник.setIcon(setIcons("PARTS/струйник.gif"));
	    струйник.setToolTipText(getHTML_Text(Struinik));
		струйник.setName(Struinik);
		
		
		
	
	
		
		патрон = new CustomButton();
		патрон.setPreferredSize(buttonDimension);
		патрон.setAutoSizedIcon(патрон, new LoadIcon().setIcons("PARTS2/патрон.gif"));
	//	патрон.setIcon(setIcons("PARTS/патрон.gif"));
		патрон.setToolTipText(getHTML_Text(Patron));
		патрон.setName(Patron);
		
		
		CustomButton сонда = new CustomButton();
		сонда.setPreferredSize(buttonDimension);
		сонда.setAutoSizedIcon(сонда, new LoadIcon().setIcons("PARTS2/сонда.gif"));
   //     сонда.setIcon(setIcons("PARTS/сонда.gif"));
		сонда.setToolTipText(getHTML_Text(Sonda));
		сонда.setName(Sonda);
		
		
		струйник4 = new CustomButton();
		струйник4.setPreferredSize(buttonDimension);
	  струйник4.setAutoSizedIcon(струйник4, new LoadIcon().setIcons("PARTS2/струйник 4.gif"));
	//	струйник4.setIcon(setIcons("PARTS/струйник 4.gif"));
		струйник4.setToolTipText(getHTML_Text(Struinik4));
		струйник4.setName(Struinik4);
		
		CustomButton барбутажна_тръба = new CustomButton();
		барбутажна_тръба.setPreferredSize(buttonDimension);
		барбутажна_тръба.setAutoSizedIcon(барбутажна_тръба, new LoadIcon().setIcons("PARTS2/барбутажна тръба.gif"));
	//	барбутажна_тръба.setIcon(setIcons("PARTS/барбутажна тръба.gif"));
		барбутажна_тръба.setToolTipText(getHTML_Text(BarbutajnaTraba));
		барбутажна_тръба.setName(BarbutajnaTraba);
		
		 игличка = new CustomButton();
		игличка.setPreferredSize(buttonDimension);
		игличка.setAutoSizedIcon(игличка, new LoadIcon().setIcons("PARTS2/игличка.gif"));
		
	//	игличка.setIcon(setIcons("PARTS/игличка.gif"));
		игличка.setToolTipText(getHTML_Text(IglichkaZaPompane));
		игличка.setName(IglichkaZaPompane);
		
	
		
	
		
		капачка = new CustomButton();
		капачка.setPreferredSize(buttonDimension);
		капачка.setAutoSizedIcon(капачка, new LoadIcon().setIcons("PARTS2/капачка.gif"));
	
	//	капачка.setIcon(setIcons("PARTS/капачка.gif"));
		капачка.setToolTipText(getHTML_Text(KapachkaZaUplatnenie));
		капачка.setName(KapachkaZaUplatnenie);
	
		
		твърдоХодовоКолело = new CustomButton();
		твърдоХодовоКолело.setPreferredSize(buttonDimension);
		твърдоХодовоКолело.setAutoSizedIcon(твърдоХодовоКолело, new LoadIcon().setIcons("PARTS2/твърдо колело.gif"));
	
	//	твърдоХодовоКолело.setIcon(setIcons("PARTS/твърдо колело.gif"));
		твърдоХодовоКолело.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		твърдоХодовоКолело.setName(TvardoHodovoKolelo);
		
		колелоЗаВисокоТегло = new CustomButton();
		колелоЗаВисокоТегло.setPreferredSize(buttonDimension);
		колелоЗаВисокоТегло.setAutoSizedIcon(колелоЗаВисокоТегло, new LoadIcon().setIcons("PARTS2/колело за високо тегло.gif"));
	   //колелоЗаВисокоТегло.setIcon(setIcons("PARTS/колело за високо тегло.gif"));
		колелоЗаВисокоТегло.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		колелоЗаВисокоТегло.setName(KoleloZaVisokoTeglo);
		
		ремонтНаКоличка = new CustomButton();
		ремонтНаКоличка.setPreferredSize(buttonDimension);
		ремонтНаКоличка.setAutoSizedIcon(ремонтНаКоличка, new LoadIcon().setIcons("PARTS2/ремонтКоличка.gif"));
//		ремонтНаКоличка.setIcon(setIcons("PARTS/ремонтКоличка.gif"));
		ремонтНаКоличка.setToolTipText(getHTML_Text(RemontKolicka));
		ремонтНаКоличка.setName(RemontKolicka);
		
		прах_BC = new CustomButton();
		прах_BC.setPreferredSize(buttonDimension);
	   прах_BC.setAutoSizedIcon(прах_BC, new LoadIcon().setIcons("PARTS2/bc.gif"));
	  
//		прах_BC.setIcon(setIcons("PARTS/bc.gif"));
		прах_BC.setToolTipText(getHTML_Text(PrahBC));
		прах_BC.setName(PrahBC);
		прах_BC.isEditable = false;
		
		
		
		

		
		CustomButton боя_пожарогасител = new CustomButton();
		боя_пожарогасител.setPreferredSize(buttonDimension);
		боя_пожарогасител.setAutoSizedIcon(боя_пожарогасител, new LoadIcon().setIcons("PARTS2/боя.gif"));
//		боя_пожарогасител.setIcon(setIcons("PARTS/боя.gif"));
		боя_пожарогасител.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		боя_пожарогасител.setName(BoyaPojarogasitel);
		
		боядисванеНаКоличка = new CustomButton();
		боядисванеНаКоличка.setPreferredSize(buttonDimension);
		боядисванеНаКоличка.setAutoSizedIcon(боядисванеНаКоличка, new LoadIcon().setIcons("PARTS2/бояКоличка.gif"));
	//	боядисванеНаКоличка.setIcon(setIcons("PARTS/бояКоличка.gif"));
		боядисванеНаКоличка.setToolTipText(getHTML_Text(BoyaKolichka));
		боядисванеНаКоличка.setName(BoyaKolichka);
		
		
		CustomButton етикет = new CustomButton();
		етикет.setPreferredSize(buttonDimension);
		етикет.setAutoSizedIcon(етикет, new LoadIcon().setIcons("PARTS2/dustEtiket.png"));
	  
	//	етикет.setIcon(setIcons("PARTS/dustEtiket.png"));
		етикет.setToolTipText(getHTML_Text(Etiket));
		етикет.setName(Etiket);
		
		CustomButton съд = new CustomButton();
		съд.setPreferredSize(buttonDimension);
		съд.setAutoSizedIcon(съд, new LoadIcon().setIcons("PARTS2/съд.gif"));
	  
//		съд.setIcon(setIcons("PARTS/съд.gif"));
		съд.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		съд.setName(SadZaGasitelnoVeshtestvo);
		
		прах_ABC = new CustomButton();
		прах_ABC.setPreferredSize(buttonDimension);
		прах_ABC.setAutoSizedIcon(прах_ABC, new LoadIcon().setIcons("PARTS2/abc.gif"));
	  
	 //   прах_ABC.setIcon(setIcons("PARTS/abc.gif"));
	    прах_ABC.setToolTipText(getHTML_Text(PrahABC));
	    прах_ABC.setName(PrahABC);
	    прах_ABC.isEditable = false;
	    
    
		
//	    
//		block.setLayout(new GridLayout(5, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, (int)(4.5 * buttonHeight)));
		
	//	pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		
		pane1.add(глава);
		pane1.add(манометър);
		pane1.add(шпленд);
		pane1.add(о_пръстен);
		pane1.add(затвор);
		
	
		list.add(глава);
		list.add(манометър);
		list.add(шпленд);
		list.add(о_пръстен);
		list.add(затвор);
		
		
		list.add(пломба);
		list.add(маркуч);
		list.add(държачЗаМаркуч);
		list.add(пружина);
		list.add(струйник);
		
		
		
		pane2.add(пломба);
		pane2.add(маркуч);
		pane2.add(държачЗаМаркуч);
		pane2.add(пружина);
		pane2.add(струйник);
		
		list.add(патрон);
		list.add(сонда);
		list.add(струйник4);
		list.add(барбутажна_тръба);
		list.add(игличка);
		
		pane3.add(патрон);
		pane3.add(сонда);
		pane3.add(струйник4);
		pane3.add(барбутажна_тръба);
		pane3.add(игличка);
	
		list.add(капачка);
		list.add(твърдоХодовоКолело);
		list.add(колелоЗаВисокоТегло);
		list.add(ремонтНаКоличка);
		list.add(прах_BC);
		
		
		pane4.add(капачка);
		pane4.add(твърдоХодовоКолело);
		pane4.add(колелоЗаВисокоТегло);
		pane4.add(ремонтНаКоличка);
		pane4.add(прах_BC);
	
		list.add(боя_пожарогасител);
		list.add(боядисванеНаКоличка);
		list.add(етикет);
		list.add(съд);
		list.add(прах_ABC);
		
		pane5.add(боя_пожарогасител);
		pane5.add(боядисванеНаКоличка);
		pane5.add(етикет);
		pane5.add(съд);
		pane5.add(прах_ABC);
		
	    
	    
		
		block.add(pane1);
	
		block.add(pane2);

		block.add(pane3);
		
		block.add(pane4);
		
		block.add(pane5);
		
		this.add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Framer f = new Framer(new Dust());
	}

}
