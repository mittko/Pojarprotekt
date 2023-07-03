package Parts;

import utils.GradientPanel;
import utils.LoadIcon;
import utils.MainPanel;
import utils.PartButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Water extends MainPanel {

	public ArrayList<PartButton> list = null;
	public static PartButton plomba = null;
	public static PartButton woda = null;
	public static PartButton patron = null;
	public static PartButton manometer = null;
	public static PartButton iglichka = null;
	public static PartButton kapachka = null;


	public Water(Dimension dim) {
		int buttonVGap = 5;
		int buttonHGap = 20;
		list = new ArrayList<>();
		GridLayout paneLayout = new GridLayout(0, 5, buttonHGap, buttonVGap);
		JPanel block = new GradientPanel();

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
	//	paneList.add(pane5);  if we want to add more arts, create one more paneList add parts to it and add paneList to block

		for(JPanel panel : paneList) {
			block.add(panel);
		}

		block.setLayout(new GridLayout(block.getComponentCount(), 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width-10,
				( ((dim.height/4) * block.getComponentCount()) - (buttonVGap*5) )));

		Dimension buttonDimension = new Dimension(
				(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));

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

		PartButton markuch = new PartButton();
		markuch.setPreferredSize(buttonDimension);
		markuch.setAutoSizedIcon(markuch, new LoadIcon().setIcons("PARTS2/markuch2.gif"));
		markuch.setToolTipText(getHTML_Text(Markuch));
		markuch.setName(Markuch);

		PartButton durjach = new PartButton();
		durjach.setPreferredSize(buttonDimension);
		durjach.setAutoSizedIcon(durjach, new LoadIcon().setIcons("PARTS2/durjach.gif"));
		durjach.setToolTipText(getHTML_Text(DarjachZaMarkuch));
		durjach.setName(DarjachZaMarkuch);

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

		PartButton struinik4 = new PartButton();
		struinik4.setPreferredSize(buttonDimension);
		struinik4.setAutoSizedIcon(struinik4, new LoadIcon().setIcons("PARTS2/struinik4.gif"));
		struinik4.setToolTipText(getHTML_Text(Struinik4));
		struinik4.setName(Struinik4);

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

		PartButton boq_pojarogasitel = new PartButton();
		boq_pojarogasitel.setPreferredSize(buttonDimension);
		boq_pojarogasitel.setAutoSizedIcon(boq_pojarogasitel, new LoadIcon().setIcons("PARTS2/boq.gif"));
		boq_pojarogasitel.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		boq_pojarogasitel.setName(BoyaPojarogasitel);

		PartButton etiket = new PartButton();
		etiket.setPreferredSize(buttonDimension);
		etiket.setAutoSizedIcon(etiket, new LoadIcon().setIcons("PARTS2/waterEtiket.png"));
		etiket.setToolTipText(getHTML_Text(Etiket));
		etiket.setName(Etiket);

		PartButton sud = new PartButton();
		sud.setPreferredSize(buttonDimension);
		sud.setAutoSizedIcon(sud, new LoadIcon().setIcons("PARTS2/sud.gif"));
		sud.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		sud.setName(SadZaGasitelnoVeshtestvo);

		woda = new PartButton();
		woda.setPreferredSize(buttonDimension);
		woda.setAutoSizedIcon(woda, new LoadIcon().setIcons("PARTS2/woda.png"));
		woda.setToolTipText(getHTML_Text(GasitelnoVeshtestvoVoda));
		woda.setName(GasitelnoVeshtestvoVoda);
		woda.isEditable = false;


		list.add(glawa);
		list.add(manometer);
		list.add(shplend);
		list.add(o_prusten);
		list.add(zatwor);

		pane1.add(glawa);
		pane1.add(manometer);
		pane1.add(shplend);
		pane1.add(o_prusten);
		pane1.add(zatwor);

		list.add(plomba);
		list.add(markuch);
		list.add(durjach);
		list.add(prujina);
		list.add(struinik);

		pane2.add(plomba);
		pane2.add(markuch);
		pane2.add(durjach);
		pane2.add(prujina);
		pane2.add(struinik);

		list.add(patron);
		list.add(sonda);
		list.add(struinik4);
		list.add(barbutajna_truba);
		list.add(iglichka);

		pane3.add(patron);
		pane3.add(sonda);
		pane3.add(struinik4);
		pane3.add(barbutajna_truba);
		pane3.add(iglichka);

		list.add(kapachka);
		list.add(boq_pojarogasitel);
		list.add(etiket);
		list.add(sud);
		list.add(woda);

		pane4.add(kapachka);
		pane4.add(boq_pojarogasitel);
		pane4.add(etiket);
		pane4.add(sud);
		pane4.add(woda);


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
		//   Framer f = new Framer(new Water());
	}

}