package admin.sklad;

import document.TextFieldLimit;
import office.models.*;
import admin.sklad.workers.GetCurrentExtPriceWorker;
import admin.sklad.workers.ImportNewExtinguisherInDBWorker;
import mydate.MyGetDate;
import utils.BrandListComboBox;
import utils.ClientsListComboBox2;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class AddNewExtinguisherDialog extends MainPanel {

	private final ClientsListComboBox2 kontragentsComboBox;

	private JComboBox<String> typeCombo = null;
	private final WheighDustModel wheightDustModel = new WheighDustModel();
	private final WheighWaterModel wheightWaterModel = new WheighWaterModel();
	private final WheightCO2Model wheightCO2Model = new WheightCO2Model();
	private JComboBox<String> wheightCombo = null;
	private final Cat1_3Model cat1_3 = new Cat1_3Model();
	private final Cat2_4Model cat2_4 = new Cat2_4Model();
	private final Cat5Model cat5 = new Cat5Model();
	private JComboBox<String> categoryCombo = null;
	private BrandListComboBox brandCombo = null;
	private JTextField quantityField = null;
	private final JTextField invoiceField;
	private final JTextField dateField;

	private JTextField deliveryValueField = null;
	private JTextField bestValueField = null;
	private JTextField finalValueField = null;
	private JTextField percentProfitField = null;
	private final JTextField operatorField;

	private final DefaultComboBoxModel emptyModel = new DefaultComboBoxModel();

	String kontragent;
	String prevValue;
	String percentProfit;

	public AddNewExtinguisherDialog(String kontragent, String prevValue,
			String percentProfit) {
		this.kontragent = kontragent;
		this.prevValue = prevValue;
		this.percentProfit = percentProfit;

		JPanel basePanel = new JPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel kontragentLabel = new JLabel("Контрагент");
		kontragentsComboBox = new ClientsListComboBox2();
		kontragentsComboBox.setSelectedItem(kontragent);
		kontragentsComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				// TODO Auto-generated method stub
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// event.getItem().toString();
					// do something with object
					// System.out.printf("%s\n", AddArtikulDialog.this.client);

				}
			}
		});
		JLabel invoiceLabel = new JLabel("Фактура");
		JLabel dateLabel = new JLabel("Дата");
		invoiceField = new JTextField(5);
		dateField = new JTextField(5);
		dateField.setText(MyGetDate.getReversedSystemDate());

		JLabel typeLabel = new JLabel("Вид");

		String[] types = new String[]{"Вид",
				"Пожарогасител " + type_Prah_ABC, "Пожарогасител " + type_Prah_BC,
				"Пожарогасител " + type_Water, "Пожарогасител " + type_Water_Fame,
				"Пожарогасител " + type_CO2};
		typeCombo = new JComboBox<String>(types);
		typeCombo.setEditable(true);

		// typeCombo.setPreferredSize(new Dimension(this.WIDTH / 3, 35));
		typeCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String item = typeCombo.getSelectedItem().toString();
				if (item.toUpperCase().contains("BC")
						|| item.toUpperCase().contains("ABC")) {
					categoryCombo.setModel(cat2_4);
					wheightCombo.setModel(wheightDustModel);
				} else if (item.toLowerCase().contains("водопенен")
						|| item.toLowerCase().contains("воден")) {
					categoryCombo.setModel(cat1_3);
					wheightCombo.setModel(wheightWaterModel);
				} else if (item.toUpperCase().contains("CO2")
						|| item.toUpperCase().contains("СО2")) {
					categoryCombo.setModel(cat5);
					wheightCombo.setModel(wheightCO2Model);
				}

			}

		});

		JLabel wheightLabel = new JLabel("Вместимост");

		wheightCombo = new JComboBox<String>(new String[] { "Вместимост" });
		wheightCombo.setEditable(true);

		JLabel categoryLabel = new JLabel("Категория");

		categoryCombo = new JComboBox<String>(new String[] { "Категория" });
		categoryCombo.setEditable(true);

		JLabel brandlabel = new JLabel("Марка");

		brandCombo = new BrandListComboBox();// new JComboBox<String>();
		brandCombo.setEditable(true);

		JLabel quantityLabel = new JLabel("Количество");

		quantityField = new JTextField(5);
		quantityField.setDocument(new TextFieldLimit(3));
		quantityField.setForeground(Color.red);

		JButton bestValueButton = new JButton("Пред. цена");
		bestValueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String maxArtikulValue = new GetCurrentExtPriceWorker(typeCombo
						.getSelectedItem().toString(), wheightCombo
						.getSelectedItem().toString(), categoryCombo
						.getSelectedItem().toString(), brandCombo
						.getSelectedItem().toString()).doInBackground();
				bestValueField.setText(maxArtikulValue + "");
			}
		});
		bestValueField = new JTextField(5);
		bestValueField.setText(prevValue);
		bestValueField.setEditable(true);
		bestValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				String oldVal = textField.getText();

			}
		});

		JLabel deliveryValueLabel = new JLabel("Доставна цена");

		deliveryValueField = new JTextField(5);
		deliveryValueField.setForeground(Color.red);
		deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				setPercentProfit(bestValueField.getText(),
						textField.getText(), finalValueField.getText());
			}
		});

		JLabel finalValueFieldLabel = new JLabel("Крайна цена");
		finalValueField = new JTextField(5);
		finalValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
					return;
				}
				setPercentProfit(bestValueField.getText(),deliveryValueField.getText(),
						textField.getText());
			}
		});
		finalValueField.setForeground(Color.red);

		JLabel percentProfitLabel = new JLabel("Процент печалба");
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
				double finalValue = calcFinalValue(bestValueField.getText(),
						deliveryValueField.getText(), textField.getText()/* percentProfit */);
				finalValueField.setText(finalValue + "");
			}
		});
		JLabel operatorLabel = new JLabel("Оператор");
		operatorField = new JTextField(10);
		operatorField.setText(MainPanel.personName);

		JButton importButton = new JButton();
		importButton.setText("Запиши");
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!checkInput(kontragentsComboBox.getSelectedItem()
						.toString(), invoiceField.getText(), dateField
						.getText(), typeCombo.getSelectedItem().toString(),
						wheightCombo.getSelectedItem().toString(),
						categoryCombo.getSelectedItem().toString(), brandCombo
								.getSelectedItem().toString(), quantityField
								.getText(), bestValueField.getText(),
						deliveryValueField.getText(),
						finalValueField.getText(),
						percentProfitField.getText(), operatorField.getText())) {
					return;
				}
				// CHECK IF DATE IS PARSABLE
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

				//	Basically, DateFormat sets Calendar. setLenient and the Javadoc states:
				//	Specifies whether or not date/time interpretation is to be lenient.
				//			With lenient interpretation, a date such as "February 942, 1996"
				//	will be treated as being equivalent to the 941st day after February 1, 1996
				// and NOT THROW EXCEPTION WITH WRONG DATE !!!
				simpleDateFormat.setLenient(false);
				Date checkDate = null;
				try {
					checkDate = simpleDateFormat.parse(dateField.getText());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null,"Грешен формат на дата !");
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(AddNewExtinguisherDialog.this);

				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));


				ImportNewExtinguisherInDBWorker iw2 = new ImportNewExtinguisherInDBWorker(
						typeCombo, wheightCombo, categoryCombo, brandCombo,
						quantityField, finalValueField, deliveryValueField,
						bestValueField, invoiceField, kontragentsComboBox,
						dateField, operatorField, percentProfitField, jd);
				iw2.execute();

			}

		});

		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new GridBagLayout());

		GridBagConstraints gbc00 = new GridBagConstraints();

		gbc00.gridx = 0;
		gbc00.gridy = 0;
		gbc00.insets = new Insets(0, 0, 0, 0);
		gbc00.gridwidth = 1;

		GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.fill = GridBagConstraints.HORIZONTAL;
		gbc10.gridx = 1;
		gbc10.gridy = 0;
		gbc10.insets = new Insets(0, 0, 0, 0);
		gbc10.gridwidth = 2;

		GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.gridx = 0;
		gbc01.gridy = 1;
		gbc01.gridwidth = 1;

		GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.fill = GridBagConstraints.HORIZONTAL;
		gbc11.gridx = 1;
		gbc11.gridy = 1;
		gbc11.gridwidth = 1;

		GridBagConstraints gbc02 = new GridBagConstraints();
		gbc02.gridx = 0;
		gbc02.gridy = 2;
		gbc02.gridwidth = 1;

		GridBagConstraints gbc12 = new GridBagConstraints();
		gbc12.fill = GridBagConstraints.HORIZONTAL;
		gbc12.gridx = 1;
		gbc12.gridy = 2;
		gbc12.gridwidth = 1;

		GridBagConstraints gbc03 = new GridBagConstraints();
		gbc03.gridx = 0;
		gbc03.gridy = 3;
		gbc03.gridwidth = 1;

		GridBagConstraints gbc13 = new GridBagConstraints();
		gbc13.fill = GridBagConstraints.HORIZONTAL;
		gbc13.gridx = 1;
		gbc13.gridy = 3;
		gbc13.gridwidth = 1;

		GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.gridx = 0;
		gbc04.gridy = 4;
		gbc04.gridwidth = 1;

		GridBagConstraints gbc14 = new GridBagConstraints();
		gbc14.fill = GridBagConstraints.HORIZONTAL;
		gbc14.gridx = 1;
		gbc14.gridy = 4;
		gbc14.gridwidth = 1;

		GridBagConstraints gbc05 = new GridBagConstraints();
		gbc05.gridx = 0;
		gbc05.gridy = 5;
		gbc05.gridwidth = 1;

		GridBagConstraints gbc15 = new GridBagConstraints();
		gbc15.fill = GridBagConstraints.HORIZONTAL;
		gbc15.gridx = 1;
		gbc15.gridy = 5;
		gbc15.gridwidth = 1;

		GridBagConstraints gbc06 = new GridBagConstraints();
		gbc06.gridx = 0;
		gbc06.gridy = 6;
		gbc06.gridwidth = 1;

		GridBagConstraints gbc16 = new GridBagConstraints();
		gbc16.fill = GridBagConstraints.HORIZONTAL;
		gbc16.gridx = 1;
		gbc16.gridy = 6;
		gbc16.gridwidth = 1;

		GridBagConstraints gbc07 = new GridBagConstraints();
		gbc07.fill = GridBagConstraints.HORIZONTAL;
		gbc07.gridx = 0;
		gbc07.gridy = 7;
		gbc07.gridwidth = 1;

		GridBagConstraints gbc17 = new GridBagConstraints();
		gbc17.gridx = 1;
		gbc17.gridy = 7;
		gbc17.gridwidth = 1;

		GridBagConstraints gbc08 = new GridBagConstraints();
		gbc08.gridx = 0;
		gbc08.gridy = 8;
		gbc08.gridwidth = 1;

		GridBagConstraints gbc18 = new GridBagConstraints();
		gbc18.gridx = 1;
		gbc18.gridy = 8;
		gbc18.gridwidth = 1;

		GridBagConstraints gbc28 = new GridBagConstraints();
		gbc28.gridx = 2;
		gbc28.gridy = 8;
		gbc28.gridwidth = 1;

		GridBagConstraints gbc38 = new GridBagConstraints();
		gbc38.gridx = 3;
		gbc38.gridy = 8;
		gbc38.gridwidth = 1;

		GridBagConstraints gbc09 = new GridBagConstraints();
		gbc09.gridx = 0;
		gbc09.gridy = 9;
		gbc09.gridwidth = 1;

		GridBagConstraints gbc19 = new GridBagConstraints();
		gbc19.gridx = 1;
		gbc19.gridy = 9;
		gbc19.gridwidth = 1;

		GridBagConstraints gbc29 = new GridBagConstraints();
		gbc29.gridx = 2;
		gbc29.gridy = 9;
		gbc29.gridwidth = 1;

		GridBagConstraints gbc39 = new GridBagConstraints();
		gbc39.gridx = 3;
		gbc39.gridy = 9;
		gbc39.gridwidth = 1;

		GridBagConstraints gbc010 = new GridBagConstraints();
		gbc010.fill = GridBagConstraints.HORIZONTAL;
		gbc010.gridx = 0;
		gbc010.gridy = 10;
		gbc010.gridwidth = 1;

		GridBagConstraints gbc110 = new GridBagConstraints();
		gbc110.fill = GridBagConstraints.HORIZONTAL;
		gbc110.gridx = 1;
		gbc110.gridy = 10;
		gbc110.gridwidth = 2;

		GridBagConstraints gbc310 = new GridBagConstraints();
		gbc310.fill = GridBagConstraints.HORIZONTAL;
		gbc310.gridx = 3;
		gbc310.gridy = 10;
		gbc310.gridwidth = 1;

		helpPanel.add(kontragentLabel, gbc00);
		helpPanel.add(kontragentsComboBox, gbc10);
		helpPanel.add(invoiceLabel, gbc01);
		helpPanel.add(invoiceField, gbc11);
		helpPanel.add(dateLabel, gbc02);
		helpPanel.add(dateField, gbc12);
		helpPanel.add(typeLabel, gbc03);
		helpPanel.add(typeCombo, gbc13);
		helpPanel.add(wheightLabel, gbc04);
		helpPanel.add(wheightCombo, gbc14);
		helpPanel.add(categoryLabel, gbc05);
		helpPanel.add(categoryCombo, gbc15);
		helpPanel.add(brandlabel, gbc06);
		helpPanel.add(brandCombo, gbc16);
		helpPanel.add(quantityLabel, gbc07);
		helpPanel.add(quantityField, gbc17);
		helpPanel.add(bestValueButton, gbc08);
		helpPanel.add(bestValueField, gbc18);
		helpPanel.add(percentProfitLabel, gbc28);
		helpPanel.add(percentProfitField, gbc38);
		helpPanel.add(deliveryValueLabel, gbc09);
		helpPanel.add(deliveryValueField, gbc19);
		helpPanel.add(finalValueFieldLabel, gbc29);
		helpPanel.add(finalValueField, gbc39);
		helpPanel.add(operatorLabel, gbc010);
		helpPanel.add(operatorField, gbc110);
		helpPanel.add(importButton, gbc310);

		basePanel.add(helpPanel);
		this.add(basePanel);
	}

	private boolean checkInput(String kontragent, String faktura, String date,
			String type, String wheight, String category, String brand,
			String quantity, String prevValue, String currValue,
			String finalValue, String percentProfit, String operator) {

		if (kontragent == null || faktura == null || date == null
				|| type == null || wheight == null || category == null
				|| brand == null || quantity == null || prevValue == null
				|| currValue == null || finalValue == null
				|| percentProfit == null || operator == null) {
			JOptionPane.showMessageDialog(null, "Има непопълнени полета !");
			return false;
		}
		if (kontragent.isEmpty() || faktura.isEmpty() || date.isEmpty()
				|| type.isEmpty() || wheight.isEmpty() || category.isEmpty()
				|| brand.isEmpty() || quantity.isEmpty() || prevValue.isEmpty()
				|| currValue.isEmpty() || finalValue.isEmpty()
				|| percentProfit.isEmpty() || operator.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Има непопълнени полета !");
			return false;
		}
		try {
			Double checkQuantity = Double.parseDouble(quantity);
			Double checkQuantity2 = Double.parseDouble(currValue);
			Double checkQuantity3 = Double.parseDouble(finalValue);
			Double checkQuantity4 = Double.parseDouble(percentProfit);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Невалидни данни !");
			return false;
		}

		return true;
	}



	private double calcFinalValue(String oldValue, String currValue,
			String percentProfit) {
		double oldVal = 0;
		double currVal = 0;
		int percentProf = 0;
		try {
			if (oldValue.isEmpty()) {
				oldVal = 0;
			} else {
				oldVal = Double.parseDouble(oldValue);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
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
				percentProf = Integer.parseInt(percentProfit);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		double finalVal = Math.max(currVal, oldVal);
		return MyMath
				.round(finalVal
						+ MyMath.getValueFromPercent(finalVal, percentProf), 2);

	}
	private void setPercentProfit(String currVal, String deliveryVal, String finalValue) {
		double currValue = 0;
		double deliveryValue = 0;
		double bigFinalValue = 0;
		try {
			currValue = Double.parseDouble(currVal);
		} catch (Exception ex) {
			currValue = 0;
		}
		try {
			deliveryValue = Double.parseDouble(deliveryVal);
		} catch (Exception ex) {
			deliveryValue = 0;
		}
		try {
			bigFinalValue = Double.parseDouble(finalValue);
		} catch (Exception ex) {
			bigFinalValue = 0;
		}
		double bestValue = Math.max(currValue, bigFinalValue);

		double differents = bestValue - deliveryValue;
		double wantedPercent = MyMath.round(
				(differents / deliveryValue) * 100, 2);
		percentProfitField.setText(wantedPercent + "");
	}


}
