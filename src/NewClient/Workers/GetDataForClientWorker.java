package NewClient.Workers;

import NewClient.editClient.EditFirm;
import NewClient.editClient.EditPerson;
import db.Client.ClientTable;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GetDataForClientWorker extends SwingWorker {

	private String client = null;
	private String destination = null;
	private String name = null;
	private JDialog jd = null;
	private ArrayList<String> dataList = null;

	public GetDataForClientWorker(String client, String destination,
			String name, JDialog jd) {
		this.client = client;
		this.destination = destination;
		this.name = name;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			dataList = ClientTable.getClientData(client, destination, name);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					if (dataList != null && dataList.size() > 0) {
						if (destination.equals(MainPanel.FIRM)) {

							for (int i = 0; i < dataList.size(); i++) {
								JComponent component = EditFirm.components
										.get(i);
								if (component instanceof JTextField) {
									JTextField textField = (JTextField) component;
									textField.setText(dataList.get(i));
								} else if (component instanceof JCheckBox) {
									JCheckBox checkBox = (JCheckBox) component;
									if (dataList.get(i) == null) { // не е
																	// поставена
																	// стойност
										checkBox.setSelected(false);
									} else if (dataList.get(i).equals("да")) {
										checkBox.setSelected(true);
									} else if (dataList.get(i).equals("не")) {
										checkBox.setSelected(false);
									}

								}
							}
						} else if (destination.equals(MainPanel.PERSON)) {
							for (int i = 0; i < dataList.size(); i++) {
								JComponent component = EditPerson.components
										.get(i);
								if (component instanceof JTextField) {
									JTextField textField = (JTextField) component;
									textField.setText(dataList.get(i));
								} else if (component instanceof JCheckBox) {
									JCheckBox checkBox = (JCheckBox) component;
									if (dataList.get(i) == null) { // не е
																	// поставена
																	// стойност
										checkBox.setSelected(false);
									} else if (dataList.get(i).equals("да")) {
										checkBox.setSelected(true);
									} else if (dataList.get(i).equals("не")) {
										checkBox.setSelected(false);
									}

								}

							}
						}
					}
				}
			});

		}

		return dataList;
	}

}
