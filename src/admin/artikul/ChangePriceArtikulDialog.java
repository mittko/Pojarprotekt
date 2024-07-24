package admin.artikul;

import admin.artikul.workers.GetDeliveryValueForArtikul;
import mydate.MyGetDate;
import utils.EditableField;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ChangePriceArtikulDialog extends MainPanel {

	private final EditableField clientField;
	private final EditableField invoiceField;

	private final EditableField artikulsField;

	private JTextField deliveryValueField = null;
	private JTextField bigFinalValueField = null;
	private JTextField percentProfitField = null;
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

	private final String skladItem;
	private final String oldValueItem;

	public ChangePriceArtikulDialog(final IEditArtikuls iEditArtikuls,final String artikulItem, String skladitem,
									final String oldValueItem, final String invoiceNumber, final String client,
									String percentProfit) {
		this.skladItem = skladitem;
		this.oldValueItem = oldValueItem;

		JPanel basePanel2 = new JPanel();
		basePanel2.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridBagLayout());// (new GridLayout(8,1,5,20));

		JLabel clientLabel = new JLabel("Контрагент");
		JLabel invoiceLabel = new JLabel("Фактура No:");
		JLabel artikulLabel = new JLabel("Артикул");

		JButton deliveryValueButton = new JButton("Доставна цена");
		deliveryValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String artikul = artikulsField.getText();
				/*
				 * String kontragent = clientField.getText(); String
				 * invoiceByKontragent = invoiceField.getText();
				 */
				GetDeliveryValueForArtikul getDeliveryValue = new GetDeliveryValueForArtikul(
						artikul/* , kontragent, invoiceByKontragent */);
				String deliveryValue = getDeliveryValue.doInBackground();
				deliveryValue = deliveryValue.replace(",", ".");
				deliveryValueField.setText(deliveryValue);
			}

		});
		JLabel dateLabel = new JLabel("Дата");
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());

		artikulsField = new EditableField("", 10);
		artikulsField.setEditable(false);
		artikulsField.setText(artikulItem);

		clientField = new EditableField("", 10);
		clientField.setEditable(false);
		clientField.setText(client);

		invoiceField = new EditableField("", 10);
		invoiceField.setEditable(false);
		invoiceField.setText(invoiceNumber);

		deliveryValueField = new JTextField(10);
		deliveryValueField.setForeground(Color.red);
		deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
				}
				double finalValue = calcFinalValue(
						textField.getText(),
						percentProfitField.getText());
				bigFinalValueField.setText(String.format("%f",finalValue));

			}
		});

		bigFinalValueField = new JTextField(10);
		bigFinalValueField.setText(oldValueItem.replace(",", "."));
		bigFinalValueField.setForeground(Color.red);
		bigFinalValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				JTextField textField = (JTextField) e.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
					return;
				}
				double bigFinalValue = 0;
				double deliveryValue = 0;
				try {
					bigFinalValue = Double.parseDouble(textField.getText());
				} catch (Exception ex) {
					bigFinalValue = 0;
				}
				try {
					deliveryValue = Double.parseDouble(deliveryValueField
							.getText());
				} catch (Exception ex) {
					deliveryValue = 0;
				}

				double differents = bigFinalValue - deliveryValue;
				double wantedPercent = 0;
				if (deliveryValue != 0) {
					wantedPercent = MyMath.round(
							(differents / deliveryValue) * 100, 2);
				}
				percentProfitField.setText(wantedPercent + "");

			}

		});
		percentProfitField = new JTextField(5);
		percentProfitField.setText(percentProfit);
		percentProfitField.setForeground(Color.red);
		percentProfitField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
				}
				double finalValue = calcFinalValue(
						deliveryValueField.getText(), textField.getText()/* percentProfit */);
				bigFinalValueField.setText(String.format("%f",finalValue));
			}
		});

		JTextField dateField = new JTextField(5);
		dateField.setEditable(false);
		dateField.setText(MyGetDate.getReversedSystemDate());
		JTextField personField = new JTextField(10);
		personField.setText(MainPanel.personName);

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


		saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				iEditArtikuls.changeArtikulPrice(artikulsField.getText(), bigFinalValueField.getText(),
						percentProfitField.getText(), clientField.getText(),
						invoiceField.getText());
			}

			// }

		});

		rightPanel.add(clientLabel, gbc);
		rightPanel.add(clientField, gbc10);
		rightPanel.add(artikulLabel, gbc01);
		rightPanel.add(artikulsField, gbc11);

		rightPanel.add(invoiceLabel, gbc02);
		rightPanel.add(invoiceField, gbc12);

		rightPanel.add(dateLabel, gbc03);
		rightPanel.add(dateField, gbc13);

		rightPanel.add(new JLabel("% Печалба"), gbc06);
		rightPanel.add(percentProfitField, gbc16);

		rightPanel.add(deliveryValueButton, gbc07);
		rightPanel.add(deliveryValueField, gbc17);

		JPanel middleFinalValuePanel = new JPanel();
		middleFinalValuePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		middleFinalValuePanel.add(new JLabel("Крайна цена"));
		middleFinalValuePanel.add(bigFinalValueField);
		rightPanel.add(middleFinalValuePanel, gbc27);

		rightPanel.add(saveInDBButton, gbc08);

		basePanel2.add(rightPanel);

		this.add(basePanel2);
	}

	private double calcFinalValue(String currValue, String percentProfit) {

		double currVal = 0;
		double percentProf = 0;

		try {
			if (currValue.isEmpty()) {
				currVal = 0;
			} else {
				currVal = Double.parseDouble(currValue);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		try {
			if (percentProfit.isEmpty()) {
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


}
