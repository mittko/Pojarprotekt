package WorkingBook;

import Parts.CO2;
import Parts.Dust;
import Parts.Vodopenen;
import Parts.Water;
import utils.MainPanel;

import static utils.MainPanel.type_Prah_BC;

public class WorkerState extends MainPanel {

	public WorkerState() {

	}

	public static void setButtonStateAccordinglyCategory(String type,String wheight,String category) {

		switch (type) {
			case type_Prah_BC:
			case type_Prah_ABC:

				// category check
				if (category.equals(category2)) {
					setButtonStateAccordinglyWheight(wheight, category2);
				} else if (category.equals(category4)) {
					setButtonStateAccordinglyWheight(wheight, category4);
				}

				break;
			case type_Water:

				// category check
				if (category.equals(category1)) {
					Water.patron.setEnabled(false);
					Water.patron.choiced = false;
					Water.patron.setBorder(Water.patron.defaultBorder);
				} else if (category.equals(category3)) {
					Water.manometer.setEnabled(false);
					Water.manometer.choiced = false;
					Water.manometer.setBorder(Water.manometer.defaultBorder);

					Water.iglichka.setEnabled(false);
					Water.iglichka.choiced = false;
					Water.iglichka.setBorder(Water.iglichka.defaultBorder);

					Water.kapachka.setEnabled(false);
					Water.kapachka.choiced = false;
					Water.kapachka.setBorder(Water.kapachka.defaultBorder);
				}


				break;
			case type_Water_Fame:

				// category check
				if (category.equals(category1)) {
					Vodopenen.patron.setEnabled(false);
					Vodopenen.patron.choiced = false;
					Vodopenen.patron.setBorder(Vodopenen.patron.defaultBorder);
				} else if (category.equals(category3)) {
					Vodopenen.manometer.setEnabled(false);
					Vodopenen.manometer.choiced = false;
					Vodopenen.manometer.setBorder(Vodopenen.manometer.defaultBorder);

					Vodopenen.iglichka.setEnabled(false);
					Vodopenen.iglichka.choiced = false;
					Vodopenen.iglichka.setBorder(Vodopenen.iglichka.defaultBorder);

					Vodopenen.kapachka.setEnabled(false);
					Vodopenen.kapachka.choiced = false;
					Vodopenen.kapachka.setBorder(Vodopenen.kapachka.defaultBorder);
				}
				break;
		}
	}

	private static void setButtonStateAccordinglyWheight(String wheight,String category) {
		if(category.equals(category2)) {
			switch (wheight) {
				case "1 ??":
				case "2 ??":
				case "3 ??":
					Dust.patron.setEnabled(false);
					Dust.patron.choiced = false;
					Dust.patron.setBorder(Dust.patron.defaultBorder);

					Dust.markuch.setEnabled(false);
					Dust.markuch.choiced = false;
					Dust.markuch.setBorder(Dust.markuch.defaultBorder);

					Dust.durjachZaMarkuch.setEnabled(false);
					Dust.durjachZaMarkuch.choiced = false;
					Dust.durjachZaMarkuch.setBorder(Dust.durjachZaMarkuch.defaultBorder);

					Dust.struinki4.setEnabled(false);
					Dust.struinki4.choiced = false;
					Dust.struinki4.setBorder(Dust.struinki4.defaultBorder);

					Dust.twurdoHodowoKolelo.setEnabled(false);
					Dust.twurdoHodowoKolelo.choiced = false;
					Dust.twurdoHodowoKolelo.setBorder(Dust.twurdoHodowoKolelo.defaultBorder);

					Dust.koleloZaWisokoTeglo.setEnabled(false);
					Dust.koleloZaWisokoTeglo.choiced = false;
					Dust.koleloZaWisokoTeglo.setBorder(Dust.koleloZaWisokoTeglo.defaultBorder);

					Dust.remontNaKolichka.setEnabled(false);
					Dust.remontNaKolichka.choiced = false;
					Dust.remontNaKolichka.setBorder(Dust.remontNaKolichka.defaultBorder);

					Dust.boqdiswaneNaKolichka.setEnabled(false);
					Dust.boqdiswaneNaKolichka.choiced = false;
					Dust.boqdiswaneNaKolichka.setBorder(Dust.boqdiswaneNaKolichka.defaultBorder);
					break;
				case "6 ??":
				case "9 ??":
				case "12 ??":
					Dust.patron.setEnabled(false);
					Dust.patron.choiced = false;
					Dust.patron.setBorder(Dust.patron.defaultBorder);

					Dust.struinki4.setEnabled(false);
					Dust.struinki4.choiced = false;
					Dust.struinki4.setBorder(Dust.struinki4.defaultBorder);

					Dust.twurdoHodowoKolelo.setEnabled(false);
					Dust.twurdoHodowoKolelo.choiced = false;
					Dust.twurdoHodowoKolelo.setBorder(Dust.twurdoHodowoKolelo.defaultBorder);

					Dust.koleloZaWisokoTeglo.setEnabled(false);
					Dust.koleloZaWisokoTeglo.choiced = false;
					Dust.koleloZaWisokoTeglo.setBorder(Dust.koleloZaWisokoTeglo.defaultBorder);

					Dust.remontNaKolichka.setEnabled(false);
					Dust.remontNaKolichka.choiced = false;
					Dust.remontNaKolichka.setBorder(Dust.remontNaKolichka.defaultBorder);

					Dust.boqdiswaneNaKolichka.setEnabled(false);
					Dust.boqdiswaneNaKolichka.choiced = false;
					Dust.boqdiswaneNaKolichka.setBorder(Dust.boqdiswaneNaKolichka.defaultBorder);
					break;
				case "25 ??":
				case "50 ??":
				case "100 ??":
					Dust.patron.setEnabled(false);
					Dust.patron.choiced = false;
					Dust.patron.setBorder(Dust.patron.defaultBorder);
					break;
			}

		} else if(category.equals(category4)) {
			switch (wheight) {
				case "1 ??":
				case "2 ??":
				case "3 ??":
					Dust.manometer.setEnabled(false);
					Dust.manometer.choiced = false;
					Dust.manometer.setBorder(Dust.manometer.defaultBorder);

					Dust.iglichka.setEnabled(false);
					Dust.iglichka.choiced = false;
					Dust.iglichka.setBorder(Dust.iglichka.defaultBorder);

					Dust.kapachka.setEnabled(false);
					Dust.kapachka.choiced = false;
					Dust.kapachka.setBorder(Dust.kapachka.defaultBorder);

					Dust.markuch.setEnabled(false);
					Dust.markuch.choiced = false;
					Dust.markuch.setBorder(Dust.markuch.defaultBorder);

					Dust.durjachZaMarkuch.setEnabled(false);
					Dust.durjachZaMarkuch.choiced = false;
					Dust.durjachZaMarkuch.setBorder(Dust.durjachZaMarkuch.defaultBorder);

					Dust.twurdoHodowoKolelo.setEnabled(false);
					Dust.twurdoHodowoKolelo.choiced = false;
					Dust.twurdoHodowoKolelo.setBorder(Dust.twurdoHodowoKolelo.defaultBorder);

					Dust.koleloZaWisokoTeglo.setEnabled(false);
					Dust.koleloZaWisokoTeglo.choiced = false;
					Dust.koleloZaWisokoTeglo.setBorder(Dust.koleloZaWisokoTeglo.defaultBorder);

					Dust.remontNaKolichka.setEnabled(false);
					Dust.remontNaKolichka.choiced = false;
					Dust.remontNaKolichka.setBorder(Dust.remontNaKolichka.defaultBorder);

					Dust.boqdiswaneNaKolichka.setEnabled(false);
					Dust.boqdiswaneNaKolichka.choiced = false;
					Dust.boqdiswaneNaKolichka.setBorder(Dust.boqdiswaneNaKolichka.defaultBorder);
					break;
				case "6 ??":
				case "9 ??":
				case "12 ??":
					Dust.manometer.setEnabled(false);
					Dust.manometer.choiced = false;
					Dust.manometer.setBorder(Dust.manometer.defaultBorder);

					Dust.iglichka.setEnabled(false);
					Dust.iglichka.choiced = false;
					Dust.iglichka.setBorder(Dust.iglichka.defaultBorder);

					Dust.kapachka.setEnabled(false);
					Dust.kapachka.choiced = false;
					Dust.kapachka.setBorder(Dust.kapachka.defaultBorder);

					Dust.struinki4.setEnabled(false);
					Dust.struinki4.choiced = false;
					Dust.struinki4.setBorder(Dust.struinki4.defaultBorder);

					Dust.twurdoHodowoKolelo.setEnabled(false);
					Dust.twurdoHodowoKolelo.choiced = false;
					Dust.twurdoHodowoKolelo.setBorder(Dust.twurdoHodowoKolelo.defaultBorder);

					Dust.koleloZaWisokoTeglo.setEnabled(false);
					Dust.koleloZaWisokoTeglo.choiced = false;
					Dust.koleloZaWisokoTeglo.setBorder(Dust.koleloZaWisokoTeglo.defaultBorder);

					Dust.remontNaKolichka.setEnabled(false);
					Dust.remontNaKolichka.choiced = false;
					Dust.remontNaKolichka.setBorder(Dust.remontNaKolichka.defaultBorder);

					Dust.boqdiswaneNaKolichka.setEnabled(false);
					Dust.boqdiswaneNaKolichka.choiced = false;
					Dust.boqdiswaneNaKolichka.setBorder(Dust.boqdiswaneNaKolichka.defaultBorder);
					break;
				case "25 ??":
				case "50 ??":
				case "100 ??":
					Dust.manometer.setEnabled(false);
					Dust.manometer.choiced = false;
					Dust.manometer.setBorder(Dust.manometer.defaultBorder);

					Dust.iglichka.setEnabled(false);
					Dust.iglichka.choiced = false;
					Dust.iglichka.setBorder(Dust.iglichka.defaultBorder);

					Dust.kapachka.setEnabled(false);
					Dust.kapachka.choiced = false;
					Dust.kapachka.setBorder(Dust.kapachka.defaultBorder);

					break;
			}

		}

	}

	public static void markCenaTehnicheskoObslujvane(String type) {
		switch (type) {
			case type_Prah_BC:
			case type_Prah_ABC :
				Dust.cenaTehnichesko.setEnabled(true);
				Dust.cenaTehnichesko.choiced = true;
				Dust.cenaTehnichesko.setBorder(Dust.cenaTehnichesko.choiceBorder);
			case type_Water:
				Water.cenaTehnichesko.setEnabled(true);
				Water.cenaTehnichesko.choiced = true;
				Water.cenaTehnichesko.setBorder(Water.cenaTehnichesko.choiceBorder);
				break;
			case type_Water_Fame:
				Vodopenen.cenaTehnichesko.setEnabled(true);
				Vodopenen.cenaTehnichesko.choiced = true;
				Vodopenen.cenaTehnichesko.setBorder(Vodopenen.cenaTehnichesko.choiceBorder);
				break;
			case type_CO2:
				CO2.cenaTehnichesko.setEnabled(true);
				CO2.cenaTehnichesko.choiced = true;
				CO2.cenaTehnichesko.setBorder(CO2.cenaTehnichesko.choiceBorder);
				break;
		}
	}

	public static void markCenaPrezarejdane(String type) {
		switch (type) {
			case type_Prah_BC:
			case type_Prah_ABC:
				Dust.cenaPrezarevdane.setEnabled(true);
				Dust.cenaPrezarevdane.choiced = true;
				Dust.cenaPrezarevdane.setBorder(Dust.cenaPrezarevdane.choiceBorder);
			case type_Water:
				Water.cenaPrezarevdane.setEnabled(true);
				Water.cenaPrezarevdane.choiced = true;
				Water.cenaPrezarevdane.setBorder(Water.cenaPrezarevdane.choiceBorder);
				break;
			case type_Water_Fame:
				Vodopenen.cenaPrezarevdane.setEnabled(true);
				Vodopenen.cenaPrezarevdane.choiced = true;
				Vodopenen.cenaPrezarevdane.setBorder(Vodopenen.cenaPrezarevdane.choiceBorder);
				break;
			case type_CO2:
				CO2.cenaPrezarevdane.setEnabled(true);
				CO2.cenaPrezarevdane.choiced = true;
				CO2.cenaPrezarevdane.setBorder(CO2.cenaPrezarevdane.choiceBorder);
				break;
		}
	}

	public static void markCenaHidrostatichnoIzpitvane(String type) {
		switch (type) {
			case type_Prah_BC:
			case type_Prah_ABC:
				Dust.cenaHidrostaticnoIzpitvane.setEnabled(true);
				Dust.cenaHidrostaticnoIzpitvane.choiced = true;
				Dust.cenaHidrostaticnoIzpitvane.setBorder(Dust.cenaHidrostaticnoIzpitvane.choiceBorder);
			case type_Water:
				Water.cenaHidrostaticnoIzpitvane.setEnabled(true);
				Water.cenaHidrostaticnoIzpitvane.choiced = true;
				Water.cenaHidrostaticnoIzpitvane.setBorder(Water.cenaHidrostaticnoIzpitvane.choiceBorder);
				break;
			case type_Water_Fame:
				Vodopenen.cenaHidrostaticnoIzpitvane.setEnabled(true);
				Vodopenen.cenaHidrostaticnoIzpitvane.choiced = true;
				Vodopenen.cenaHidrostaticnoIzpitvane.setBorder(Vodopenen.cenaHidrostaticnoIzpitvane.choiceBorder);
				break;
			case type_CO2:
				CO2.cenaHidrostaticnoIzpitvane.setEnabled(true);
				CO2.cenaHidrostaticnoIzpitvane.choiced = true;
				CO2.cenaHidrostaticnoIzpitvane.setBorder(CO2.cenaHidrostaticnoIzpitvane.choiceBorder);
				break;
		}
	}
	public static void markPlomba(String type,boolean isTO) {
		switch (type) {
			case type_Prah_BC:
			case type_Prah_ABC:
				// choiced bydefault
				Dust.plomba.setEnabled(true);
				Dust.plomba.choiced = true;
				Dust.plomba.setBorder(Dust.plomba.choiceBorder);
				break;
			case type_Water:
				// choiced by default
				Water.plomba.setEnabled(true);
				Water.plomba.choiced = true;
				Water.plomba.setBorder(Dust.plomba.choiceBorder);
				break;
			case type_Water_Fame:
				// choiced by default
				Vodopenen.plomba.setEnabled(true);
				Vodopenen.plomba.choiced = true;
				Vodopenen.plomba.setBorder(Dust.plomba.choiceBorder);
				break;
			case type_CO2:
				if (isTO) {
					Parts.CO2.plomba.setEnabled(true);
					Parts.CO2.plomba.isEditable = true;
					Parts.CO2.plomba.choiced = false;
					Parts.CO2.plomba.setBorder(Parts.CO2.plomba.defaultBorder);
				} else {
					// choiced by default
					Parts.CO2.plomba.setEnabled(true);
					Parts.CO2.plomba.isEditable = false;
					Parts.CO2.plomba.choiced = true;
					Parts.CO2.plomba.setBorder(Dust.plomba.choiceBorder);
				}
				break;
		}
	}

	public static void markEntity(String type,boolean enabled) {
		switch (type) {
			case type_Prah_BC:
				if (enabled) {
					// choiced by default
					Dust.prah_BC.setEnabled(true);
					Dust.prah_BC.choiced = true;
					Dust.prah_BC.setBorder(Dust.prah_BC.choiceBorder);

					// enabled false by default
					Dust.prah_ABC.setEnabled(false);
					Dust.prah_ABC.choiced = false;
					Dust.prah_ABC.setBorder(Dust.prah_ABC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.prah_BC.setEnabled(false);
					Dust.prah_BC.choiced = false;
					Dust.prah_BC.setBorder(Dust.prah_BC.defaultBorder);

					Dust.prah_ABC.setEnabled(false);
					Dust.prah_ABC.choiced = false;
					Dust.prah_ABC.setBorder(Dust.prah_ABC.defaultBorder);
				}
				break;
			case type_Prah_ABC:
				if (enabled) {
					// choiced by default
					Dust.prah_ABC.setEnabled(true);
					Dust.prah_ABC.choiced = true;
					Dust.prah_ABC.setBorder(Dust.prah_BC.choiceBorder);
					// enabled false by default
					Dust.prah_BC.setEnabled(false);
					Dust.prah_BC.choiced = false;
					Dust.prah_BC.setBorder(Dust.prah_BC.defaultBorder);
				} else {
					// enabled false both by default
					Dust.prah_ABC.setEnabled(false);
					Dust.prah_ABC.choiced = false;
					Dust.prah_ABC.setBorder(Dust.prah_BC.defaultBorder);

					Dust.prah_BC.setEnabled(false);
					Dust.prah_BC.choiced = false;
					Dust.prah_BC.setBorder(Dust.prah_BC.defaultBorder);
				}
				break;
			case type_Water:
				if (enabled) {
					// choiced by default
					Water.woda.setEnabled(true);
					Water.woda.choiced = true;
					Water.woda.setBorder(Water.woda.choiceBorder);
				} else {
					// enabled false by default
					Water.woda.setEnabled(false);
					Water.woda.choiced = false;
					Water.woda.setBorder(Water.woda.defaultBorder);
				}
				break;
			case type_Water_Fame:
				if (enabled) {
					// choiced by default
					Vodopenen.woda_pqna.setEnabled(true);
					Vodopenen.woda_pqna.choiced = true;
					Vodopenen.woda_pqna.setBorder(Water.woda.choiceBorder);
				} else {
					// enabled false by default
					Vodopenen.woda_pqna.setEnabled(false);
					Vodopenen.woda_pqna.choiced = false;
					Vodopenen.woda_pqna.setBorder(Water.woda.defaultBorder);
				}
				break;
			case type_CO2:
				if (enabled) {
					// choiced by default
					Parts.CO2.wugleroden_dioksid.setEnabled(true);
					Parts.CO2.wugleroden_dioksid.choiced = true;
					Parts.CO2.wugleroden_dioksid.setBorder(Parts.CO2.wugleroden_dioksid.choiceBorder);
				} else {
					// enabled false by default
					Parts.CO2.wugleroden_dioksid.setEnabled(false);
					Parts.CO2.wugleroden_dioksid.choiced = false;
					Parts.CO2.wugleroden_dioksid.setBorder(Parts.CO2.wugleroden_dioksid.defaultBorder);
				}
				break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}