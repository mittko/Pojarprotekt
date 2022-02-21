package Parts;

import utility.CustomButton;
import utility.GradientPanel;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Water extends MainPanel {

	public ArrayList<CustomButton> list = null;
	public static CustomButton пломба = null;
	public static CustomButton вода = null;
	public static CustomButton патрон = null;
	public static CustomButton манометър = null;
	public static CustomButton игличка = null;
	public static CustomButton капачка = null;
	public Water(Dimension dim) {
		list = new ArrayList<CustomButton>();
		
		JPanel block = new GradientPanel() ;
		JPanel pane1 = new JPanel();
		pane1.setOpaque(false);
		
		JPanel pane2 = new JPanel();
		pane2.setOpaque(false);
		
		JPanel pane3 = new JPanel();
		pane3.setOpaque(false);
		
		JPanel pane4 = new JPanel();
		pane4.setOpaque(false);
		
		block.setLayout(new GridLayout(4, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
	//	int buttonHeight =   block.getPreferredSize().height / 4; //100;
		int buttonVGap = 5;
        int buttonHGap = 20;
        
	    Dimension buttonDimension = new Dimension(
	    		(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));
	    
        CustomButton  глава = new CustomButton();
    	глава.setPreferredSize(buttonDimension);
		глава.setAutoSizedIcon(глава, new LoadIcon().setIcons("PARTS2/глава.gif"));
	//	глава.setIcon(new LoadIcon().setIcons("PARTS/глава.gif"));
	    глава.setToolTipText(getHTML_Text(Glava));
	    глава.setName(Glava);
	    
	    манометър = new CustomButton();
	    манометър.setPreferredSize(buttonDimension);
		манометър.setAutoSizedIcon(манометър, new LoadIcon().setIcons("PARTS2/манометър.gif"));
	//	манометър.setIcon(new LoadIcon().setIcons("PARTS/манометър.gif"));
        манометър.setToolTipText(getHTML_Text(Manometar));
        манометър.setName(Manometar);
        
    	CustomButton шпленд = new CustomButton();
    	шпленд.setPreferredSize(buttonDimension);
		шпленд.setAutoSizedIcon(шпленд, new LoadIcon().setIcons("PARTS2/шпленд.gif"));
	//	шпленд.setIcon(new LoadIcon().setIcons("PARTS/шпленд.gif"));
		шпленд.setToolTipText(getHTML_Text(Sphlend));
		шпленд.setName(Sphlend);
		
		CustomButton  о_пръстен = new CustomButton();
		о_пръстен.setPreferredSize(buttonDimension);
		о_пръстен.setAutoSizedIcon(о_пръстен, new LoadIcon().setIcons("PARTS2/о-пръстен.gif"));
	//	о_пръстен.setIcon(new LoadIcon().setIcons("PARTS/о-пръстен.gif"));
		о_пръстен.setToolTipText(getHTML_Text(Uplatnenie));
		о_пръстен.setName(Uplatnenie);
	
	
        
    	CustomButton затвор = new CustomButton();
    	затвор.setPreferredSize(buttonDimension);
		затвор.setAutoSizedIcon(затвор, new LoadIcon().setIcons("PARTS2/затвор.gif"));
	//	затвор.setIcon(new LoadIcon().setIcons("PARTS/затвор.gif"));
		затвор.setToolTipText(getHTML_Text(Zatvor));
		затвор.setName(Zatvor);
		
		
        
		
		пломба = new CustomButton();
		пломба.setPreferredSize(buttonDimension);
		пломба.setAutoSizedIcon(пломба, new LoadIcon().setIcons("PARTS2/пломба.gif"));
	//	пломба.setIcon(new LoadIcon().setIcons("PARTS/пломба.gif"));
		пломба.setToolTipText(getHTML_Text(Plomba));
		пломба.setName(Plomba);
		пломба.isEditable = false;
		
		CustomButton маркуч = new CustomButton();
		маркуч.setPreferredSize(buttonDimension);
		маркуч.setAutoSizedIcon(маркуч, new LoadIcon().setIcons("PARTS2/маркуч2.gif"));
   //     маркуч.setIcon(new LoadIcon().setIcons("PARTS/маркуч.gif"));
        маркуч.setToolTipText(getHTML_Text(Markuch));
        маркуч.setName(Markuch);
        
		CustomButton държач = new CustomButton();
		държач.setPreferredSize(buttonDimension);
		държач.setAutoSizedIcon(държач, new LoadIcon().setIcons("PARTS2/държач.gif"));
  //      държач.setIcon(new LoadIcon().setIcons("PARTS/държач.gif"));
        държач.setToolTipText(getHTML_Text(DarjachZaMarkuch));
        държач.setName(DarjachZaMarkuch);
        
        CustomButton пружина = new CustomButton();
        пружина.setPreferredSize(buttonDimension);
		пружина.setAutoSizedIcon(пружина, new LoadIcon().setIcons("PARTS2/пружина.gif"));
//		пружина.setIcon(new LoadIcon().setIcons("PARTS/пружина.gif"));
		пружина.setToolTipText(getHTML_Text(Prujina));
		пружина.setName(Prujina);
		
		CustomButton струйник = new CustomButton();
		струйник.setPreferredSize(buttonDimension);
		струйник.setAutoSizedIcon(струйник, new LoadIcon().setIcons("PARTS2/струйник.gif"));
	//	струйник.setIcon(new LoadIcon().setIcons("PARTS/струйник.gif"));
		струйник.setToolTipText(getHTML_Text(Struinik));
		струйник.setName(Struinik);
		
	
		
	
		
		патрон = new CustomButton();
		патрон.setPreferredSize(buttonDimension);
		патрон.setAutoSizedIcon(патрон, new LoadIcon().setIcons("PARTS2/патрон.gif"));
	//	патрон.setIcon(new LoadIcon().setIcons("PARTS/патрон.gif"));
		патрон.setToolTipText(getHTML_Text(Patron));
		патрон.setName(Patron);
		
		
		CustomButton сонда = new CustomButton();
		сонда.setPreferredSize(buttonDimension);
		сонда.setAutoSizedIcon(сонда, new LoadIcon().setIcons("PARTS2/сонда.gif"));
   //     сонда.setIcon(new LoadIcon().setIcons("PARTS/сонда.gif"));
		сонда.setToolTipText(getHTML_Text(Sonda));
		сонда.setName(Sonda);
		
		
		CustomButton струйник4 = new CustomButton();
		струйник4.setPreferredSize(buttonDimension);
	  струйник4.setAutoSizedIcon(струйник4, new LoadIcon().setIcons("PARTS2/струйник 4.gif"));
	//	струйник4.setIcon(new LoadIcon().setIcons("PARTS/струйник 4.gif"));
		струйник4.setToolTipText(getHTML_Text(Struinik4));
		струйник4.setName(Struinik4);
		
		CustomButton барбутажна_тръба = new CustomButton();
		барбутажна_тръба.setPreferredSize(buttonDimension);
		барбутажна_тръба.setAutoSizedIcon(барбутажна_тръба, new LoadIcon().setIcons("PARTS2/барбутажна тръба.gif"));
	//	барбутажна_тръба.setIcon(new LoadIcon().setIcons("PARTS/барбутажна тръба.gif"));
		барбутажна_тръба.setToolTipText(getHTML_Text(BarbutajnaTraba));
		барбутажна_тръба.setName(BarbutajnaTraba);
		
		игличка = new CustomButton();
		игличка.setPreferredSize(buttonDimension);
	   игличка.setAutoSizedIcon(игличка, new LoadIcon().setIcons("PARTS2/игличка.gif"));
	//	игличка.setIcon(new LoadIcon().setIcons("PARTS/игличка.gif"));
		игличка.setToolTipText(getHTML_Text(IglichkaZaPompane));
		игличка.setName(IglichkaZaPompane);
	
		капачка = new CustomButton();
		капачка.setPreferredSize(buttonDimension);
		капачка.setAutoSizedIcon(капачка, new LoadIcon().setIcons("PARTS2/капачка.gif"));
	//	капачка.setIcon(new LoadIcon().setIcons("PARTS/капачка.gif"));
		капачка.setToolTipText(getHTML_Text(KapachkaZaUplatnenie));
		капачка.setName(KapachkaZaUplatnenie);
		
		CustomButton боя_пожарогасител = new CustomButton();
		боя_пожарогасител.setPreferredSize(buttonDimension);
		боя_пожарогасител.setAutoSizedIcon(боя_пожарогасител, new LoadIcon().setIcons("PARTS2/боя.gif"));
//		боя_пожарогасител.setIcon(new LoadIcon().setIcons("PARTS/боя.gif"));
		боя_пожарогасител.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		боя_пожарогасител.setName(BoyaPojarogasitel);
		
		
		CustomButton етикет = new CustomButton();
		етикет.setPreferredSize(buttonDimension);
		етикет.setAutoSizedIcon(етикет, new LoadIcon().setIcons("PARTS2/waterEtiket.png"));
	//	етикет.setIcon(new LoadIcon().setIcons("PARTS/waterEtiket.png"));
		етикет.setToolTipText(getHTML_Text(Etiket));
		етикет.setName(Etiket);
		
		CustomButton съд = new CustomButton();
		съд.setPreferredSize(buttonDimension);
		съд.setAutoSizedIcon(съд, new LoadIcon().setIcons("PARTS2/съд.gif"));
	//	съд.setIcon(new LoadIcon().setIcons("PARTS/съд.gif"));
		съд.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		съд.setName(SadZaGasitelnoVeshtestvo);
		
		вода = new CustomButton();
		вода.setPreferredSize(buttonDimension);
		вода.setAutoSizedIcon(вода, new LoadIcon().setIcons("PARTS2/вода.png"));
//		вода.setIcon(new LoadIcon().setIcons("PARTS2/вода.png"));
		вода.setToolTipText(getHTML_Text(GasitelnoVeshtestvoVoda));
		вода.setName(GasitelnoVeshtestvoVoda);
		вода.isEditable = false;
		
	
	
		
		list.add(глава);
		list.add(манометър);
		list.add(шпленд);
		list.add(о_пръстен);
		list.add(затвор);
		
		pane1.add(глава);
		pane1.add(манометър);
		pane1.add(шпленд);
		pane1.add(о_пръстен);
		pane1.add(затвор);
		
		pane2.add(пломба);
		pane2.add(маркуч);
		pane2.add(държач);
		pane2.add(пружина);
		pane2.add(струйник);
		
		list.add(пломба);
		list.add(маркуч);
		list.add(държач);
		list.add(пружина);
		list.add(струйник);
		
		pane3.add(патрон);
		pane3.add(сонда);
		pane3.add(струйник4);
		pane3.add(барбутажна_тръба);
		pane3.add(игличка);
		
		 list.add(патрон);
		 list.add(сонда);
		 list.add(струйник4);
		 list.add(барбутажна_тръба);
		 list.add(игличка);
		
			list.add(капачка);
			list.add(боя_пожарогасител);
			list.add(етикет);
			list.add(съд);
			list.add(вода);
			
			pane4.add(капачка);
			pane4.add(боя_пожарогасител);
			pane4.add(етикет);
			pane4.add(съд);
			pane4.add(вода);
		 
//		block.setLayout(new GridLayout(4, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, 4 * buttonHeight));
//		
//		pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane1.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane1);
//		pane2.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane2.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane2);
		
		block.add(pane3);
//		pane3.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane3.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		
		block.add(pane4);
//		pane4.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane4.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		
		this.add(block);
		this.setOpaque(false);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       //   Framer f = new Framer(new Water());
	}

}
