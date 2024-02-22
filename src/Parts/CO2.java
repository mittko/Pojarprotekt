package Parts;

import utils.GradientPanel;
import utils.LoadIcon;
import utils.MainPanel;
import utils.PartButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CO2 extends MainPanel {

	public static PartButton plomba = null;
	public static PartButton wugleroden_dioksid = null;

	public static PartButton cenaTehnichesko = null;
	public static PartButton cenaPrezarevdane = null;
	public static PartButton cenaHidrostaticnoIzpitvane = null;

	public ArrayList<PartButton> list = null;
	public CO2(Dimension dim) {
		list = new ArrayList<>();
		int buttonVGap = 5;
		int buttonHGap = 20;
		GridLayout paneLayout = new GridLayout(0, 5, buttonHGap, buttonVGap);

		JPanel block = new GradientPanel() ;

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

		ArrayList<JPanel> paneList = new ArrayList<>();
		paneList.add(pane1);
		paneList.add(pane2);
		paneList.add(pane3);
		paneList.add(pane4);
		paneList.add(pane5);
		// if we want to add more arts, create one more paneList add parts to it and add paneList to block

		for(JPanel panel : paneList) {
			block.add(panel);
		}

		block.setLayout(new GridLayout(block.getComponentCount(), 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width-10,
				( ((dim.height/4) * block.getComponentCount()) - (buttonVGap*5) )));

		Dimension buttonDimension = new Dimension(
				(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));

		PartButton burzWentil_Model1 = new PartButton();
		burzWentil_Model1.setPreferredSize(buttonDimension);
		burzWentil_Model1.setAutoSizedIcon(burzWentil_Model1, new LoadIcon().setIcons("PARTS2/burz_wentil3.5.gif"));
		burzWentil_Model1.setToolTipText(getHTML_Text(BarzVentil1_Model));
		burzWentil_Model1.setName(BarzVentil1_Model);

		PartButton burzWentil_Model2 = new PartButton();
		burzWentil_Model2.setPreferredSize(buttonDimension);
		burzWentil_Model2.setAutoSizedIcon(burzWentil_Model2, new LoadIcon().setIcons("PARTS2/burz_wentil5.gif"));
		burzWentil_Model2.setToolTipText(getHTML_Text(BarzVentil2_Model));
		burzWentil_Model2.setName(BarzVentil2_Model);

		PartButton burzWentil_Model3 = new PartButton();
		burzWentil_Model3.setPreferredSize(buttonDimension);
		burzWentil_Model3.setAutoSizedIcon(burzWentil_Model3, new LoadIcon().setIcons("PARTS2/burz_wentil30.gif"));
		burzWentil_Model3.setToolTipText(getHTML_Text(BarzVentil3_Model));
		burzWentil_Model3.setName(BarzVentil3_Model);

		PartButton burzWentil_Model3_PrawaRezba = new PartButton();
		burzWentil_Model3_PrawaRezba.setPreferredSize(buttonDimension);
		burzWentil_Model3_PrawaRezba.setAutoSizedIcon(burzWentil_Model3_PrawaRezba, new LoadIcon().setIcons("PARTS2/burz_wentil30prawa_rezba.gif"));
		burzWentil_Model3_PrawaRezba.setToolTipText(getHTML_Text(BarzVentil3Model_Prava_Rezba));
		burzWentil_Model3_PrawaRezba.setName(BarzVentil3Model_Prava_Rezba);

		PartButton shplend = new PartButton();
		shplend.setPreferredSize(buttonDimension);
		shplend.setAutoSizedIcon(shplend, new LoadIcon().setIcons("PARTS2/shplend.gif"));
		shplend.setToolTipText(getHTML_Text(Sphlend));
		shplend.setName(Sphlend);

		plomba = new PartButton();
		plomba.setPreferredSize(buttonDimension);
		plomba.setAutoSizedIcon(plomba, new LoadIcon().setIcons("PARTS2/plomba.gif"));
		plomba.setToolTipText(getHTML_Text(Plomba));
		plomba.setName(Plomba);
	//	plomba.isEditable = false;

		PartButton snegoobrazuwatelModel1 = new PartButton();
		snegoobrazuwatelModel1.setPreferredSize(buttonDimension);
		snegoobrazuwatelModel1.setAutoSizedIcon(snegoobrazuwatelModel1, new LoadIcon().setIcons("PARTS2/snegoobrazuwatel2.gif"));
		snegoobrazuwatelModel1.setToolTipText(getHTML_Text(Snegoobrazuvatel1_Model));
		snegoobrazuwatelModel1.setName(Snegoobrazuvatel1_Model);

		PartButton snegoobrazuwatelModel2 = new PartButton();
		snegoobrazuwatelModel2.setPreferredSize(buttonDimension);
		snegoobrazuwatelModel2.setAutoSizedIcon(snegoobrazuwatelModel2, new LoadIcon().setIcons("PARTS2/snegoobrazuwatel5.gif"));
		snegoobrazuwatelModel2.setToolTipText(getHTML_Text(Snegoobrazuvatel2_Model));
		snegoobrazuwatelModel2.setName(Snegoobrazuvatel2_Model);

		PartButton snegoobrazuwatelModel3 = new PartButton();
		snegoobrazuwatelModel3.setPreferredSize(buttonDimension);
		snegoobrazuwatelModel3.setAutoSizedIcon(snegoobrazuwatelModel3, new LoadIcon().setIcons("PARTS2/snegoobrazuwatel30.gif"));
		snegoobrazuwatelModel3.setToolTipText(getHTML_Text(Snegoobrazuvatel3_Model));
		snegoobrazuwatelModel3.setName(Snegoobrazuvatel3_Model);

		PartButton sonda = new PartButton();
		sonda.setPreferredSize(buttonDimension);
		sonda.setAutoSizedIcon(sonda, new LoadIcon().setIcons("PARTS2/sonda.gif"));
		sonda.setToolTipText(getHTML_Text(Sonda));
		sonda.setName(Sonda);

		PartButton drujka = new PartButton();
		drujka.setPreferredSize(buttonDimension);
		drujka.setAutoSizedIcon(drujka, new LoadIcon().setIcons("PARTS2/drujka.gif"));
		drujka.setToolTipText(getHTML_Text(DrajkaZaPojarogasitel));
		drujka.setName(DrajkaZaPojarogasitel);

		PartButton zapresowanNakrajnik = new PartButton();
		zapresowanNakrajnik.setPreferredSize(buttonDimension);
		zapresowanNakrajnik.setAutoSizedIcon(zapresowanNakrajnik, new LoadIcon().setIcons("PARTS2/nakrajnik.gif"));
		zapresowanNakrajnik.setToolTipText(getHTML_Text(ZapresovaneNaNakrainik));
		zapresowanNakrajnik.setName(ZapresovaneNaNakrainik);

		PartButton twurdo_kolelo = new PartButton();
		twurdo_kolelo.setPreferredSize(buttonDimension);
		twurdo_kolelo.setAutoSizedIcon(twurdo_kolelo, new LoadIcon().setIcons("PARTS2/twurdo_kolelo.gif"));
		twurdo_kolelo.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		twurdo_kolelo.setName(TvardoHodovoKolelo);

		PartButton koleloZaWisokoTeglo = new PartButton();
		koleloZaWisokoTeglo.setPreferredSize(buttonDimension);
		koleloZaWisokoTeglo.setAutoSizedIcon(koleloZaWisokoTeglo, new LoadIcon().setIcons("PARTS2/kolelo_za_wisoko_teglo.gif"));
		koleloZaWisokoTeglo.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		koleloZaWisokoTeglo.setName(KoleloZaVisokoTeglo);

		PartButton remontKolichka = new PartButton();
		remontKolichka.setPreferredSize(buttonDimension);
		remontKolichka.setAutoSizedIcon(remontKolichka, new LoadIcon().setIcons("PARTS2/remontKolichka.gif"));
		remontKolichka.setToolTipText(getHTML_Text(RemontKolicka));
		remontKolichka.setName(RemontKolicka);


		PartButton boqKolichka = new PartButton();
		boqKolichka.setPreferredSize(buttonDimension);
		boqKolichka.setAutoSizedIcon( boqKolichka, new LoadIcon().setIcons("PARTS2/boqKolichka.gif"));
		boqKolichka.setToolTipText(getHTML_Text(BoyaKolichka));
		boqKolichka.setName(BoyaKolichka);

		PartButton boqPojarogasitel = new PartButton();
		boqPojarogasitel.setPreferredSize(buttonDimension);
		boqPojarogasitel.setAutoSizedIcon(boqPojarogasitel, new LoadIcon().setIcons("PARTS2/boq.gif"));
		boqPojarogasitel.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		boqPojarogasitel.setName(BoyaPojarogasitel);

		PartButton etiket = new PartButton();
		etiket.setPreferredSize(buttonDimension);
		etiket.setAutoSizedIcon(etiket, new LoadIcon().setIcons("PARTS2/co2Etiket.png"));
		etiket.setToolTipText(getHTML_Text(Etiket));
		etiket.setName(Etiket);


		// enabled for now
		PartButton sud = new PartButton();
		sud.setPreferredSize(buttonDimension);
		sud.setAutoSizedIcon(sud, new LoadIcon().setIcons("PARTS2/sud.gif"));
		sud.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		sud.setName(SadZaGasitelnoVeshtestvo);

		wugleroden_dioksid = new PartButton();
		wugleroden_dioksid.setPreferredSize(buttonDimension);
		wugleroden_dioksid.setAutoSizedIcon(wugleroden_dioksid, new LoadIcon().setIcons("PARTS2/gas.gif"));
		wugleroden_dioksid.setToolTipText(getHTML_Text(GasitelnoVeshtestvoCO2));
		wugleroden_dioksid.setName(GasitelnoVeshtestvoCO2);
		wugleroden_dioksid.isEditable = false;

		pane1.add(burzWentil_Model1);
		pane1.add(burzWentil_Model2);
		pane1.add(burzWentil_Model3);
		pane1.add(burzWentil_Model3_PrawaRezba);
		pane1.add(shplend);

		list.add(burzWentil_Model1);
		list.add(burzWentil_Model2);
		list.add(burzWentil_Model3);
		list.add(burzWentil_Model3_PrawaRezba);
		list.add(shplend);

		pane2.add(plomba);
		pane2.add(snegoobrazuwatelModel1);
		pane2.add(snegoobrazuwatelModel2);
		pane2.add(snegoobrazuwatelModel3);
		pane2.add(sonda);

		list.add(plomba);
		list.add(snegoobrazuwatelModel1);
		list.add(snegoobrazuwatelModel2);
		list.add(snegoobrazuwatelModel3);
		list.add(sonda);

		pane3.add(drujka);
		pane3.add(zapresowanNakrajnik);
		pane3.add(twurdo_kolelo);
		pane3.add(koleloZaWisokoTeglo);
		pane3.add(remontKolichka);

		list.add(drujka);
		list.add(zapresowanNakrajnik);
		list.add(twurdo_kolelo);
		list.add(koleloZaWisokoTeglo);
		list.add(remontKolichka);

		pane4.add(boqKolichka);
		pane4.add(boqPojarogasitel);
		pane4.add(etiket);
		pane4.add(sud);
		pane4.add(wugleroden_dioksid);

		list.add(boqKolichka);
		list.add(boqPojarogasitel);
		list.add(etiket);
		list.add(sud);
		list.add(wugleroden_dioksid);

		cenaTehnichesko = new PartButton();
		cenaTehnichesko.setPreferredSize(buttonDimension);
		cenaTehnichesko.setToolTipText(getHTML_Text(CENA_TEHNICHESKO));
		cenaTehnichesko.setName(CENA_TEHNICHESKO);
		cenaTehnichesko.setText("Техническо Обслужване");
		cenaTehnichesko.isEditable = false;

		cenaPrezarevdane = new PartButton();
		cenaPrezarevdane.setPreferredSize(buttonDimension);
		cenaPrezarevdane.setToolTipText(getHTML_Text(CENA_PREZAREJDANE));
		cenaPrezarevdane.setName(CENA_PREZAREJDANE);
		cenaPrezarevdane.setText("Презареждане");
		cenaPrezarevdane.isEditable = false;

		cenaHidrostaticnoIzpitvane = new PartButton();
		cenaHidrostaticnoIzpitvane.setPreferredSize(buttonDimension);
		cenaHidrostaticnoIzpitvane.setToolTipText(getHTML_Text(CENA_HIDROSTATICHNO_IZPITVANE));
		cenaHidrostaticnoIzpitvane.setName(CENA_HIDROSTATICHNO_IZPITVANE);
		cenaHidrostaticnoIzpitvane.setText("Хидростатично Изпитване");
		cenaHidrostaticnoIzpitvane.isEditable = false;

		list.add(cenaTehnichesko);
		list.add(cenaPrezarevdane);
		list.add(cenaHidrostaticnoIzpitvane);

		pane5.add(cenaTehnichesko);
		pane5.add(cenaPrezarevdane);
		pane5.add(cenaHidrostaticnoIzpitvane);

		if(block.getComponentCount() > 4) {
			JScrollPane scrollPane = new JScrollPane(block, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(dim.width-10, dim.height-10));
			this.add(scrollPane);
		} else {
			this.add(block);
		}
		this.setOpaque(false);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//       Framer f = new Framer(new CO2());
	}

}