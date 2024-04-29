package admin.parts.quantity;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import run.JDialoger;
import utils.MainPanel;
import utils.TooltipButton;
import admin.parts.quantity.renderers.Renderer;
import admin.parts.quantity.workers.SeePartsQuantityWorker;
import db.Part_Quantity_DB.Part_Quantity;

public class PartsQuantityTable extends MainPanel {

	JPanel northPanel;
	TooltipButton seeButton;
	TooltipButton addQuantityButton;
	JPanel centerPanel;
	JScrollPane scroll;
	JTable table;
	DefaultTableModel dftm;

	public static final String[] PARTS = { Glava, Manometar, Sphlend,
			Uplatnenie, Zatvor, Plomba, Markuch, DarjachZaMarkuch, Prujina,
			Struinik, Patron, Sonda, Struinik4, BarbutajnaTraba,
			IglichkaZaPompane, KapachkaZaUplatnenie, TvardoHodovoKolelo,
			KoleloZaVisokoTeglo, RemontKolicka, PrahBC, PrahABC,
			BoyaPojarogasitel, BoyaKolichka, Etiket, SadZaGasitelnoVeshtestvo,
			GasitelnoVeshtestvoVoda, GasitelnoVeshtestvoVodaPyana,
			BarzVentil1_Model, BarzVentil2_Model, BarzVentil3_Model,
			BarzVentil3Model_Prava_Rezba, Snegoobrazuvatel1_Model,
			Snegoobrazuvatel2_Model, Snegoobrazuvatel3_Model,
			DrajkaZaPojarogasitel, ZapresovaneNaNakrainik,
			GasitelnoVeshtestvoCO2,

	};

	public PartsQuantityTable() {
		northPanel = new JPanel();
		seeButton = new TooltipButton("Зареди данните");
		seeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(PartsQuantityTable.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				SeePartsQuantityWorker seeW = new SeePartsQuantityWorker(dftm,
						jd);
				seeW.execute();
			}

		});
		addQuantityButton = new TooltipButton("Добави");
		addQuantityButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialoger jDialoger = new JDialoger();
				jDialoger.setContentPane(new AddPartDialog());
				jDialoger.setResizable(false);
				jDialoger.Show();
			}

		});
		northPanel.add(seeButton);
		northPanel.add(addQuantityButton);

		centerPanel = new JPanel();
		dftm = new DefaultTableModel(new String[] { "Резервна Част ", "К-во" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(dftm);
		table.getColumnModel().getColumn(0).setPreferredWidth(WIDTH - 50 - 100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);

		scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 70));

		centerPanel.add(scroll);

		this.setLayout(new BorderLayout());

		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Framer f = new Framer(new PartsQuantityTable());
				// f.pack();
				// insertAll();
			}

		});
	}

	// init parts

	static void insertAll() {
		for (int i = 0; i < PARTS.length; i++) {
			Part_Quantity.insertPartIntoQuantity(PARTS[i], 0);
		}
	}
}
