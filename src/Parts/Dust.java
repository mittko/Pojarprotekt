package Parts;


import utility.PartButton;
import utility.GradientPanel;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dust extends MainPanel {

	public ArrayList<PartButton> list = null;
	public static PartButton plomba = null;  // пломба
	public static PartButton prah_BC = null; // прах ВС
	public static PartButton prah_ABC = null; // прах АВС
	public static PartButton patron = null;
	public static PartButton manometer = null;
	public static PartButton iglichka = null;
	public static PartButton kapachka = null;
	public static PartButton markuch = null;
	public static PartButton durjachZaMarkuch = null;
	public static PartButton struinki4 = null;
	public static PartButton twurdoHodowoKolelo = null;
	public static PartButton koleloZaWisokoTeglo = null;
	public static PartButton remontNaKolichka = null;
	public static PartButton boqdiswaneNaKolichka  = null;
	public static PartButton cenaTehnichesko = null;
	public static PartButton cenaHidrostatichno = null;
	public static PartButton cenaPrezarejdaneITehnichesko = null;
	public static PartButton cenaPrezarejdaneTehnicheskoIHidrostatichno = null;
	public Dust(Dimension dim) {
		list = new ArrayList<>();
		
		JPanel block = new GradientPanel() ;

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

		JPanel pane6 = new JPanel();
		pane6.setLayout(paneLayout);
		pane6.setOpaque(false);

		ArrayList<JPanel> paneList = new ArrayList<>();
		paneList.add(pane1);
		paneList.add(pane2);
		paneList.add(pane3);
		paneList.add(pane4);
		paneList.add(pane5);
        paneList.add(pane6);

		for(JPanel panel : paneList) {
			block.add(panel);
		}

		block.setLayout(new GridLayout(block.getComponentCount(), 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width-10,
				( ((dim.height/4) * block.getComponentCount()) - (buttonVGap*5) )));

	   Dimension buttonDimension =  new Dimension(new Dimension(
				(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15)
				));
	
		PartButton glawa = new PartButton();
		glawa.setPreferredSize(buttonDimension);
		glawa.setAutoSizedIcon(glawa, new LoadIcon().setIcons("PARTS2/glawa.gif"));

	    glawa.setToolTipText(getHTML_Text(Glava));
	    glawa.setName(Glava);
	  
		manometer = new PartButton();
		manometer.setPreferredSize(buttonDimension);
		manometer.setAutoSizedIcon(manometer, new LoadIcon().setIcons("PARTS2/manometer.gif"));
        manometer.setToolTipText(getHTML_Text(Manometar));
        manometer.setName(Manometar);
        
    	PartButton shplend = new PartButton();
    	shplend.setPreferredSize(buttonDimension);
		shplend.setAutoSizedIcon(shplend, new LoadIcon().setIcons("PARTS2/shplend.gif"));
		shplend.setToolTipText(getHTML_Text(Sphlend));
		shplend.setName(Sphlend);
		
		PartButton o_prusten = new PartButton();
		o_prusten.setPreferredSize(buttonDimension);
		o_prusten.setAutoSizedIcon(o_prusten, new LoadIcon().setIcons("PARTS2/o_prusten.gif"));
		o_prusten.setToolTipText(getHTML_Text(Uplatnenie));
		o_prusten.setName(Uplatnenie);

    	PartButton zatwor = new PartButton();
    	zatwor.setPreferredSize(buttonDimension);
		zatwor.setAutoSizedIcon(zatwor, new LoadIcon().setIcons("PARTS2/zatwor.gif"));
		zatwor.setToolTipText(getHTML_Text(Zatvor));
		zatwor.setName(Zatvor);

		plomba = new PartButton();
		plomba.setPreferredSize(buttonDimension);
		plomba.setAutoSizedIcon(plomba, new LoadIcon().setIcons("PARTS2/plomba.gif"));
		plomba.setToolTipText(getHTML_Text(Plomba));
		plomba.setName(Plomba);
		plomba.isEditable = false;
		
		markuch = new PartButton();
		markuch.setPreferredSize(buttonDimension);
		markuch.setAutoSizedIcon(markuch, new LoadIcon().setIcons("PARTS2/markuch2.gif"));
        markuch.setToolTipText(getHTML_Text(Markuch));
        markuch.setName(Markuch);
        
		durjachZaMarkuch = new PartButton();
		durjachZaMarkuch.setPreferredSize(buttonDimension);
		durjachZaMarkuch.setAutoSizedIcon(durjachZaMarkuch, new LoadIcon().setIcons("PARTS2/durjach.gif"));
        durjachZaMarkuch.setToolTipText(getHTML_Text(DarjachZaMarkuch));
        durjachZaMarkuch.setName(DarjachZaMarkuch);
        
        PartButton prujina = new PartButton();
        prujina.setPreferredSize(buttonDimension);
		prujina.setAutoSizedIcon(prujina, new LoadIcon().setIcons("PARTS2/prujina.gif"));
		prujina.setToolTipText(getHTML_Text(Prujina));
		prujina.setName(Prujina);
		
		PartButton struinik = new PartButton();
		struinik.setPreferredSize(buttonDimension);
		struinik.setAutoSizedIcon(struinik, new LoadIcon().setIcons("PARTS2/struinik.gif"));
	    struinik.setToolTipText(getHTML_Text(Struinik));
		struinik.setName(Struinik);

		patron = new PartButton();
		patron.setPreferredSize(buttonDimension);
		patron.setAutoSizedIcon(patron, new LoadIcon().setIcons("PARTS2/patron.gif"));
		patron.setToolTipText(getHTML_Text(Patron));
		patron.setName(Patron);

		PartButton sonda = new PartButton();
		sonda.setPreferredSize(buttonDimension);
		sonda.setAutoSizedIcon(sonda, new LoadIcon().setIcons("PARTS2/sonda.gif"));
		sonda.setToolTipText(getHTML_Text(Sonda));
		sonda.setName(Sonda);

		struinki4 = new PartButton();
		struinki4.setPreferredSize(buttonDimension);
	    struinki4.setAutoSizedIcon(struinki4, new LoadIcon().setIcons("PARTS2/struinik4.gif"));
		struinki4.setToolTipText(getHTML_Text(Struinik4));
		struinki4.setName(Struinik4);
		
		PartButton barbutajna_truba = new PartButton();
		barbutajna_truba.setPreferredSize(buttonDimension);
		barbutajna_truba.setAutoSizedIcon(barbutajna_truba, new LoadIcon().setIcons("PARTS2/barbutajna_truba.gif"));
		barbutajna_truba.setToolTipText(getHTML_Text(BarbutajnaTraba));
		barbutajna_truba.setName(BarbutajnaTraba);
		
		iglichka = new PartButton();
		iglichka.setPreferredSize(buttonDimension);
		iglichka.setAutoSizedIcon(iglichka, new LoadIcon().setIcons("PARTS2/iglichka.gif"));
		iglichka.setToolTipText(getHTML_Text(IglichkaZaPompane));
		iglichka.setName(IglichkaZaPompane);

		
		kapachka = new PartButton();
		kapachka.setPreferredSize(buttonDimension);
		kapachka.setAutoSizedIcon(kapachka, new LoadIcon().setIcons("PARTS2/kapachka.gif"));
		kapachka.setToolTipText(getHTML_Text(KapachkaZaUplatnenie));
		kapachka.setName(KapachkaZaUplatnenie);
	
		
		twurdoHodowoKolelo = new PartButton();
		twurdoHodowoKolelo.setPreferredSize(buttonDimension);
		twurdoHodowoKolelo.setAutoSizedIcon(twurdoHodowoKolelo, new LoadIcon().setIcons("PARTS2/twurdo_kolelo.gif"));
		twurdoHodowoKolelo.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		twurdoHodowoKolelo.setName(TvardoHodovoKolelo);
		
		koleloZaWisokoTeglo = new PartButton();
		koleloZaWisokoTeglo.setPreferredSize(buttonDimension);
		koleloZaWisokoTeglo.setAutoSizedIcon(koleloZaWisokoTeglo, new LoadIcon().setIcons("PARTS2/kolelo_za_wisoko_teglo.gif"));
		koleloZaWisokoTeglo.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		koleloZaWisokoTeglo.setName(KoleloZaVisokoTeglo);
		
		remontNaKolichka = new PartButton();
		remontNaKolichka.setPreferredSize(buttonDimension);
		remontNaKolichka.setAutoSizedIcon(remontNaKolichka, new LoadIcon().setIcons("PARTS2/remontKolichka.gif"));
		remontNaKolichka.setToolTipText(getHTML_Text(RemontKolicka));
		remontNaKolichka.setName(RemontKolicka);

		prah_BC = new PartButton();
		prah_BC.setPreferredSize(buttonDimension);
		prah_BC.setAutoSizedIcon(prah_BC, new LoadIcon().setIcons("PARTS2/bc.gif"));
		prah_BC.setToolTipText(getHTML_Text(PrahBC));
		prah_BC.setName(PrahBC);
		prah_BC.isEditable = false;
		
		PartButton boq_povarogasitel = new PartButton();
		boq_povarogasitel.setPreferredSize(buttonDimension);
		boq_povarogasitel.setAutoSizedIcon(boq_povarogasitel, new LoadIcon().setIcons("PARTS2/boq.gif"));
		boq_povarogasitel.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		boq_povarogasitel.setName(BoyaPojarogasitel);
		
		boqdiswaneNaKolichka = new PartButton();
		boqdiswaneNaKolichka.setPreferredSize(buttonDimension);
		boqdiswaneNaKolichka.setAutoSizedIcon(boqdiswaneNaKolichka, new LoadIcon().setIcons("PARTS2/boqKolichka.gif"));
		boqdiswaneNaKolichka.setToolTipText(getHTML_Text(BoyaKolichka));
		boqdiswaneNaKolichka.setName(BoyaKolichka);

		PartButton etiket = new PartButton();
		etiket.setPreferredSize(buttonDimension);
		etiket.setAutoSizedIcon(etiket, new LoadIcon().setIcons("PARTS2/dustEtiket.png"));
		etiket.setToolTipText(getHTML_Text(Etiket));
		etiket.setName(Etiket);
		
		PartButton sud = new PartButton();
		sud.setPreferredSize(buttonDimension);
		sud.setAutoSizedIcon(sud, new LoadIcon().setIcons("PARTS2/sud.gif"));
		sud.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		sud.setName(SadZaGasitelnoVeshtestvo);
		
		prah_ABC = new PartButton();
		prah_ABC.setPreferredSize(buttonDimension);
		prah_ABC.setAutoSizedIcon(prah_ABC, new LoadIcon().setIcons("PARTS2/abc.gif"));
	    prah_ABC.setToolTipText(getHTML_Text(PrahABC));
	    prah_ABC.setName(PrahABC);
	    prah_ABC.isEditable = false;

		cenaTehnichesko = new PartButton();
		cenaTehnichesko.setPreferredSize(buttonDimension);
		cenaTehnichesko.setAutoSizedIcon(cenaTehnichesko, new LoadIcon().setIcons("PARTS2/recharge_tehn_extinguishers.png"));
		cenaTehnichesko.setToolTipText(getHTML_Text(CenaTehnichesko));
		cenaTehnichesko.setName(CenaTehnichesko);
		cenaTehnichesko.isEditable = false;

		cenaHidrostatichno = new PartButton();
		cenaHidrostatichno.setPreferredSize(buttonDimension);
		cenaHidrostatichno.setAutoSizedIcon(cenaHidrostatichno, new LoadIcon().setIcons("PARTS2/recharge_hidro_extinguishers.png"));
		cenaHidrostatichno.setToolTipText(getHTML_Text(CenaHidrostatichno));
		cenaHidrostatichno.setName(CenaHidrostatichno);
		cenaHidrostatichno.isEditable = false;

		cenaPrezarejdaneITehnichesko = new PartButton();
		cenaPrezarejdaneITehnichesko.setPreferredSize(buttonDimension);
		cenaPrezarejdaneITehnichesko.setAutoSizedIcon(cenaPrezarejdaneITehnichesko, new LoadIcon().setIcons("PARTS2/recharge_tehn_extinguishers.png"));
		cenaPrezarejdaneITehnichesko.setToolTipText(getHTML_Text(CenaPrezarejdaneITehnichesko));
		cenaPrezarejdaneITehnichesko.setName(CenaPrezarejdaneITehnichesko);
		cenaPrezarejdaneITehnichesko.isEditable = false;

		cenaPrezarejdaneTehnicheskoIHidrostatichno = new PartButton();
		cenaPrezarejdaneTehnicheskoIHidrostatichno.setPreferredSize(buttonDimension);
		cenaPrezarejdaneTehnicheskoIHidrostatichno.setAutoSizedIcon(cenaPrezarejdaneTehnicheskoIHidrostatichno, new LoadIcon().setIcons("PARTS2/recharge_tehn_hidro_extinguishers.png"));
		cenaPrezarejdaneTehnicheskoIHidrostatichno.setToolTipText(getHTML_Text(CenaPrezarejdaneTehnicheskoIHidrostatichno));
		cenaPrezarejdaneTehnicheskoIHidrostatichno.setName(CenaPrezarejdaneTehnicheskoIHidrostatichno);
		cenaPrezarejdaneTehnicheskoIHidrostatichno.isEditable = false;

		pane1.add(glawa);
		pane1.add(manometer);
		pane1.add(shplend);
		pane1.add(o_prusten);
		pane1.add(zatwor);

		list.add(glawa);
		list.add(manometer);
		list.add(shplend);
		list.add(o_prusten);
		list.add(zatwor);

		list.add(plomba);
		list.add(markuch);
		list.add(durjachZaMarkuch);
		list.add(prujina);
		list.add(struinik);

		pane2.add(plomba);
		pane2.add(markuch);
		pane2.add(durjachZaMarkuch);
		pane2.add(prujina);
		pane2.add(struinik);
		
		list.add(patron);
		list.add(sonda);
		list.add(struinki4);
		list.add(barbutajna_truba);
		list.add(iglichka);
		
		pane3.add(patron);
		pane3.add(sonda);
		pane3.add(struinki4);
		pane3.add(barbutajna_truba);
		pane3.add(iglichka);
	
		list.add(kapachka);
		list.add(twurdoHodowoKolelo);
		list.add(koleloZaWisokoTeglo);
		list.add(remontNaKolichka);
		list.add(prah_BC);

		pane4.add(kapachka);
		pane4.add(twurdoHodowoKolelo);
		pane4.add(koleloZaWisokoTeglo);
		pane4.add(remontNaKolichka);
		pane4.add(prah_BC);
	
		list.add(boq_povarogasitel);
		list.add(boqdiswaneNaKolichka);
		list.add(etiket);
		list.add(sud);
		list.add(prah_ABC);
		
		pane5.add(boq_povarogasitel);
		pane5.add(boqdiswaneNaKolichka);
		pane5.add(etiket);
		pane5.add(sud);
		pane5.add(prah_ABC);

		list.add(cenaTehnichesko);
		list.add(cenaHidrostatichno);
		list.add(cenaPrezarejdaneITehnichesko);
		list.add(cenaPrezarejdaneTehnicheskoIHidrostatichno);

		pane6.add(cenaTehnichesko);
		pane6.add(cenaHidrostatichno);
		pane6.add(cenaPrezarejdaneITehnichesko);
		pane6.add(cenaPrezarejdaneTehnicheskoIHidrostatichno);

		if(block.getComponentCount() > 4) {
			JScrollPane scrollPane = new JScrollPane(block, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(dim.width-10, dim.height-10));
			this.add(scrollPane);
		} else {
	    	this.add(block);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Framer f = new Framer(new Dust());
	}

}
