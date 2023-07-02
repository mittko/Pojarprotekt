package WorkingBookWorkers;

import WorkingBook.View;
import WorkingBook.WorkingBook;
import db.Part_Quantity_DB.Part_Quantity;
import db.Protokol.ProtokolNumber;
import db.Protokol.ProtokolTable;
import generators.ProtokolGenerator;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class SaveInProtokolWorker extends SwingWorker {
	private int result = 0; // 1 -> success 0 -> failure
	private JDialog jd;
	private String FIXED_PROTOKOL_NUMBER = null;
	private final ProtokolGenerator pg = new ProtokolGenerator();
	private int[] next_number = null;
	
    private TreeMap<Object,Integer> partQuantityMap;
    
	public SaveInProtokolWorker(JDialog jd, String protokolNumber,TreeMap<Object,Integer> partQuantityMap) {
		this.jd = jd;
		this.FIXED_PROTOKOL_NUMBER = protokolNumber;
		this.partQuantityMap = partQuantityMap;
	}

	@Override
	public Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			int[] results = ProtokolTable.insertIntoProtokolTableDB(View.dtm_Extinguisher,
					FIXED_PROTOKOL_NUMBER, // protokolNumber
					MainPanel.personName,
					MyGetDate.getReversedSystemDate());

//			  int[] results = ProtokolTable.batchInsertIntoProtokol(
//					  View.dtm_Extinguisher,
//                      FIXED_PROTOKOL_NUMBER, // protokolNumber
//                      MainPanel.personName,
//                      MyGetDate.getReversedSystemDate());
			  
			  ArrayList<String> updateExtinguishersList = new ArrayList<String>();
			  for(int row = 0;row < results.length;row++) {
				  if(results[row] == 1) {
					  updateExtinguishersList.add(View.dtm_Extinguisher.getValueAt(row, 3).toString());
			    		result = 1;
					/*  ProtokolTable.setExtinguisherDone(View.dtm_Extinguisher
								.getValueAt(row, 3).toString());
						// - > обработен
*/				  } 
			  }
	
			
			if(result > 0) {
				// update number
				// update protokol number
			   next_number = pg.updateProtokol(FIXED_PROTOKOL_NUMBER);
						
				ProtokolNumber.updateProtokolNumberInDB(pg.digitsToString(next_number)); // change
				
				// ???? Update Service Order
				ProtokolTable.setExtinguisherDoneWithBatch(updateExtinguishersList);// in service table
				
			//  UPDATE PARTS QUANTITY
				Part_Quantity.decreaseQuantityWithBatch(partQuantityMap);
			
			}
			
		} finally {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (result > 0) {
						JOptionPane.showMessageDialog(null,
								"ƒанните са записани успешно!");
						View.printProtokolButton.setEnabled(true);
						WorkingBook.CURRENT_CLIENT = "";
						View.protokolNumberLabel.setName(pg
								.digitsToString(next_number));
					}

				}

			});
		}
		return result;
	}

/*	public String getParts(int row) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Object> value = Worker.ext_parts.get(getKey(row));
		for (int i = 0; i < value.size(); i++) {
			sb.append(value.get(i));
			if (i + 1 < value.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String getKey(int row) {

		StringBuilder sb = new StringBuilder();
		sb.append(View.dtm_Extinguisher.getValueAt(row, 3).toString());
		return sb.toString();

	}*/
}
