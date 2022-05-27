package admin.Artikul;

import admin.Artikul.Workers.ChangeArtikulQuantityWorker;
import utility.EditableField;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeQuantityArtikulDialog extends MainPanel {
	private JPanel basePanel2 = null;
	private JPanel leftPanel = null;

	private JLabel clientLabel = null;
	private JLabel invoiceLabel = null;
	private JLabel artikulLabel = null;
	private JLabel quantityLabel = null;
	private JPanel rightPanel = null;

	private EditableField clientField = null;
	private EditableField invoiceField = null;
	private EditableField artikulsField = null;
	private EditableField quantityField = null;
	JButton saveInDBButton = new JButton("Запази");

	public static void main(String[] args) {
		// AddArtikulDialog dialog =
		// new
		// AddArtikulDialog("artikul","nalicni","cena","faktura","kontragent","operator");
		// JustFrame start = new JustFrame(dialog);
		// start.pack();
		// start.setResizable(false);
		// start.setFrameLocationOnTheCenter();
	}

	private String artikulItem;
	private String skladItem;
	private String medItem;
	private String oldValueItem;
	private String currValueItem;
	private String invoiceNumber;
	private String clientItem;
	private String dateItem;
	private String sellerItem;
	private String percentProfitItem;

	public ChangeQuantityArtikulDialog(final String artikulItem,
			String skladitem, final String medItem, final String oldValueItem,
			final String invoiceNumber, final String client, final String date,
			final String seller, String percentProfit) {
		this.artikulItem = artikulItem;
		this.skladItem = skladitem;
		this.medItem = medItem;
		this.oldValueItem = oldValueItem;
		this.invoiceNumber = invoiceNumber;
		this.clientItem = client;
		this.dateItem = date;
		this.sellerItem = seller;
		this.percentProfitItem = percentProfit;

		basePanel2 = new JPanel();// GradientPanel();
		basePanel2.setBorder(BorderFactory.createLineBorder(Color.black));

		leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridBagLayout());// (new GridLayout(8,1,5,20));

		clientLabel = new JLabel("Контрагент");
		invoiceLabel = new JLabel("Фактура No:");
		artikulLabel = new JLabel("Артикул");
		quantityLabel = new JLabel("Количество");

		rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());

		artikulsField = new EditableField("", 40);
		artikulsField.setEditable(false);
		artikulsField.setText(artikulItem);

		clientField = new EditableField("", 40);
		clientField.setEditable(false);
		clientField.setText(client);

		invoiceField = new EditableField("", 10);
		invoiceField.setEditable(false);
		invoiceField.setText(invoiceNumber);

		quantityField = new EditableField("", 5);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.fill = GridBagConstraints.HORIZONTAL;
		gbc10.gridx = 1;
		gbc10.gridy = 0;
		gbc10.gridwidth = 3;
		gbc10.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.fill = GridBagConstraints.HORIZONTAL;
		gbc01.gridx = 0;
		gbc01.gridy = 1;
		gbc01.gridwidth = 1;
		gbc01.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.fill = GridBagConstraints.HORIZONTAL;
		gbc11.gridx = 1;
		gbc11.gridy = 1;
		gbc11.gridwidth = 3;
		gbc11.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc02 = new GridBagConstraints();
		gbc02.fill = GridBagConstraints.HORIZONTAL;
		gbc02.gridx = 0;
		gbc02.gridy = 2;
		gbc02.gridwidth = 1;
		gbc02.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc12 = new GridBagConstraints();
		gbc12.fill = GridBagConstraints.HORIZONTAL;
		gbc12.gridx = 1;
		gbc12.gridy = 2;
		gbc12.gridwidth = 1;
		gbc12.insets = new Insets(5, 0, 0, 0);

		/*
		 * GridBagConstraints gbc22 = new GridBagConstraints(); gbc22.fill =
		 * GridBagConstraints.HORIZONTAL; gbc22.gridx = 2; gbc22.gridy = 2;
		 * gbc22.gridwidth = 1; gbc22.insets = new Insets(15,0,0,0);
		 */

		GridBagConstraints gbc03 = new GridBagConstraints();
		gbc03.fill = GridBagConstraints.HORIZONTAL;
		gbc03.gridx = 0;
		gbc03.gridy = 3;
		gbc03.gridwidth = 1;
		gbc03.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc13 = new GridBagConstraints();
		gbc13.fill = GridBagConstraints.HORIZONTAL;
		gbc13.gridx = 1;
		gbc13.gridy = 3;
		gbc13.gridwidth = 1;
		gbc13.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc23 = new GridBagConstraints();
		gbc23.fill = GridBagConstraints.HORIZONTAL;
		gbc23.gridx = 2;
		gbc23.gridy = 3;
		gbc23.gridwidth = 1;
		gbc23.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc33 = new GridBagConstraints();
		gbc33.fill = GridBagConstraints.HORIZONTAL;
		gbc33.gridx = 3;
		gbc33.gridy = 3;
		gbc33.gridwidth = 1;
		gbc33.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.fill = GridBagConstraints.HORIZONTAL;
		gbc04.gridx = 0;
		gbc04.gridy = 4;
		gbc04.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc14 = new GridBagConstraints();
		gbc14.fill = GridBagConstraints.HORIZONTAL;
		gbc14.gridx = 1;
		gbc14.gridy = 4;
		gbc14.gridwidth = 1;
		gbc14.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc24 = new GridBagConstraints();
		gbc24.fill = GridBagConstraints.HORIZONTAL;
		gbc24.gridx = 2;
		gbc24.gridy = 4;
		gbc24.gridwidth = 1;
		gbc24.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc05 = new GridBagConstraints();
		gbc05.fill = GridBagConstraints.HORIZONTAL;
		gbc05.gridx = 0;
		gbc05.gridy = 5;
		gbc05.gridwidth = 1;
		gbc05.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc15 = new GridBagConstraints();
		gbc15.fill = GridBagConstraints.HORIZONTAL;
		gbc15.gridx = 1;
		gbc15.gridy = 5;
		gbc15.gridwidth = 1;
		gbc15.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc25 = new GridBagConstraints();
		gbc25.fill = GridBagConstraints.HORIZONTAL;
		gbc25.gridx = 2;
		gbc25.gridy = 5;
		gbc25.gridwidth = 1;
		gbc25.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc35 = new GridBagConstraints();
		gbc35.fill = GridBagConstraints.HORIZONTAL;
		gbc35.gridx = 3;
		gbc35.gridy = 5;
		gbc35.gridwidth = 1;
		gbc35.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc06 = new GridBagConstraints();
		gbc06.fill = GridBagConstraints.HORIZONTAL;
		gbc06.gridx = 0;
		gbc06.gridy = 6;
		gbc06.gridwidth = 1;
		gbc06.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc16 = new GridBagConstraints();
		gbc16.fill = GridBagConstraints.HORIZONTAL;
		gbc16.gridx = 1;
		gbc16.gridy = 6;
		gbc16.gridwidth = 1;
		gbc16.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc26 = new GridBagConstraints();
		gbc26.fill = GridBagConstraints.HORIZONTAL;
		gbc26.gridx = 2;
		gbc26.gridy = 6;
		gbc26.gridwidth = 1;
		gbc26.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc36 = new GridBagConstraints();
		gbc36.fill = GridBagConstraints.HORIZONTAL;
		gbc36.gridx = 2;
		gbc36.gridy = 6;
		gbc36.gridwidth = 1;
		gbc36.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc07 = new GridBagConstraints();
		gbc07.fill = GridBagConstraints.HORIZONTAL;
		gbc07.gridx = 0;
		gbc07.gridy = 7;
		gbc07.gridwidth = 1;
		gbc07.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc17 = new GridBagConstraints();
		gbc17.fill = GridBagConstraints.HORIZONTAL;
		gbc17.gridx = 1;
		gbc17.gridy = 7;
		gbc17.gridwidth = 1;
		gbc17.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc27 = new GridBagConstraints();
		gbc27.fill = GridBagConstraints.HORIZONTAL;
		gbc27.gridx = 2;
		gbc27.gridy = 7;
		gbc27.gridwidth = 1;
		gbc27.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc08 = new GridBagConstraints();
		gbc08.fill = GridBagConstraints.HORIZONTAL;
		gbc08.gridx = 0;
		gbc08.gridy = 8;
		gbc08.gridwidth = 2;
		gbc08.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc18 = new GridBagConstraints();
		gbc18.fill = GridBagConstraints.HORIZONTAL;
		gbc18.gridx = 1;
		gbc18.gridy = 8;
		gbc18.gridwidth = 1;
		gbc18.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc28 = new GridBagConstraints();
		gbc28.fill = GridBagConstraints.HORIZONTAL;
		gbc28.gridx = 2;
		gbc28.gridy = 8;
		gbc28.gridwidth = 2;
		gbc28.insets = new Insets(5, 0, 0, 0);

		saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// System.out.printf("%s %s %s %s %s %s %s %s %s %s\n",
				// clientComboBox.getSelectedItem().toString(),
				// skladField.getText(),
				// medField.getText(),
				// oldValueField.getText(),
				// currValueField.getText(),
				// invoiceField.getText(),
				// artikulsComboBox.getSelectedItem().toString(),
				// dateField.getText(),
				// personField.getText() ,
				// percentProfitField.getText());
				String artikul = null;
				String newQuantity = null;
				String kontragent = null;
				String invoiceByKontragent = null;

				artikul = artikulsField.getText();
				newQuantity = quantityField.getText();
				kontragent = clientField.getText();
				invoiceByKontragent = invoiceField.getText();

				if (artikul.isEmpty() || newQuantity.isEmpty()
						|| kontragent.isEmpty()
						|| invoiceByKontragent.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Има незаписани данни !");
					return;
				}
				try {
					Double parseString = Double.parseDouble(newQuantity);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Грешен формат на количество");
					return;
				}
				ChangeArtikulQuantityWorker quantityArtikulWorker = new ChangeArtikulQuantityWorker(
						AVAILABLE_ARTIKULS,
						artikul, newQuantity, kontragent, invoiceByKontragent);
				quantityArtikulWorker.execute();

			}

			// }

		});

		rightPanel.add(clientLabel, gbc);
		rightPanel.add(clientField, gbc10);
		rightPanel.add(artikulLabel, gbc01);// (artikulField, gbc01);
		rightPanel.add(artikulsField, gbc11);// (artikulField, gbc01);

		rightPanel.add(invoiceLabel, gbc02);
		rightPanel.add(invoiceField, gbc12);

		rightPanel.add(quantityLabel, gbc03);
		rightPanel.add(quantityField, gbc13);
		rightPanel.add(saveInDBButton, gbc04);

		basePanel2.add(rightPanel);

		this.add(basePanel2);
	}

	private double calcFinalValue(String currValue, String percentProfit) {

		double currVal = 0;
		double percentProf = 0;

		try {
			if (currValue.equals("") || currValue.isEmpty()) {
				currVal = 0;
			} else {
				currVal = Double.parseDouble(currValue);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		try {
			if (percentProfit.equals("") || percentProfit.isEmpty()) {
				percentProf = 0;
			} else {
				percentProf = Double.parseDouble(percentProfit);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return MyMath.round(
				currVal + MyMath.getValueFromPercent(currVal, percentProf), 2);

	}

	private boolean checkUserInput(String artikulItem, String currValueItem,
			String invoiceNumber, String client, String date, String seller,
			String percentProfitItem) {
		if (artikulItem.equals("") || skladItem.equals("")
				|| medItem.equals("") || oldValueItem.equals("")
				|| currValueItem.equals("") || invoiceNumber.equals("")
				|| client.equals("") || date.equals("") || seller.equals("")
				|| percentProfitItem.equals("")) {
			JOptionPane.showMessageDialog(null, "Има непопълнени полета !");
			return false;
		}

		try {
			Double d1 = Double.parseDouble(skladItem);
			Double d2 = Double.parseDouble(oldValueItem);
			Double d3 = Double.parseDouble(currValueItem);
			Double d4 = Double.parseDouble(percentProfitItem);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Невалидни данни!");
			return false;
		}
		return true;
	}

	boolean isAvailable(String artikulItem) {
		for (int i = 0; i < AvailableArtikulsTable.artikulTableModel
				.getRowCount(); i++) {
			if (AvailableArtikulsTable.artikulTableModel.getValueAt(i, 0)
					.toString().equals(artikulItem)) {
				// System.out.println("IS AVAILABLE!");
				return true;
			}
		}
		return false;
	}

}
