package admin.sklad;

import http.RequestCallback2;
import http.new_extinguishers.NewExtinguisherService;
import http.sklad.GetArtikulService;
import utils.EditableField;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ChangePriceOfNewExtinguisherDialog extends MainPanel {

	private final EditableField kontragentsField;

	private EditableField typeField = null;
	private JTextField wheightField = null;
	private JTextField categoryField = null;
	private JTextField brandField = null;

	private final JTextField invoiceField;

	private JTextField deliveryValueField = null;
	private JTextField finalValueField = null;
	private JTextField percentProfitField = null;

	String kontragent;
	String invoiceByKontragent;
	String date;
	String type;
	String wheight;
	String category;
	String brand;
	String operator;

	private final JLabel gapLabel = new JLabel("(x)");
	private final JLabel gapLabel2 = new JLabel("(y)");

	public ChangePriceOfNewExtinguisherDialog(String kontragent,
			String invoiceByKontragent, String date, String type,
			String wheight, String category, String brand, String operator) {
		this.kontragent = kontragent;
		this.invoiceByKontragent = invoiceByKontragent;
		this.date = date;
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.operator = operator;

		JPanel basePanel = new JPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel kontragentLabel = new JLabel("Контрагент");
		kontragentsField = new EditableField("Контрагент", 30);
		kontragentsField.setEditable(false);
		kontragentsField.setText(kontragent);

		JLabel invoiceLabel = new JLabel("Фактура");
		JLabel dateLabel = new JLabel("Дата");
		invoiceField = new JTextField(10);
		invoiceField.setText(invoiceByKontragent);
		invoiceField.setEditable(false);

		JTextField dateField = new JTextField(10);
		dateField.setEditable(false);
		dateField.setText(date);

		JLabel typeLabel = new JLabel("Вид");

		typeField = new EditableField("", 25);
		typeField.setText(type);
		typeField.setEditable(false);

		JLabel wheightLabel = new JLabel("Вместимост");

		wheightField = new JTextField(10);
		wheightField.setText(wheight);
		wheightField.setEditable(false);

		JLabel categoryLabel = new JLabel("Категория");

		categoryField = new JTextField(10);
		categoryField.setText(category);
		categoryField.setEditable(false);

		JLabel brandlabel = new JLabel("Марка");

		brandField = new JTextField(20);// new JComboBox<String>();
		brandField.setText(brand);
		brandField.setEditable(false);

		JButton deliveryValueButton = new JButton("Доставна цена");
		deliveryValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String artikul = typeField.getText() + " ( Нов ) "
						+ wheightField.getText();
				String kontragent = kontragentsField.getText();
				String invoiceByKontragent = invoiceField.getText();


				GetArtikulService service = new GetArtikulService();
				service.getArtikulValue(MainPanel.DELIVERY_ARTIKULS, artikul, new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						Double result = (Double) t;
						if(result > 0) {
							deliveryValueField.setText(String.format("%.2f",result).replace(",","."));
						}
					}
				});
			}

		});
		// oldValueField.setForeground(Color.red);
		deliveryValueField = new JTextField(5);
		deliveryValueField.setForeground(Color.red);
		deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
				}
				double finalValue = calcFinalValue(
						textField.getText()/* currValue */,
						percentProfitField.getText());
				finalValueField.setText(finalValue + "");
			}
		});

		JLabel finalValueFieldLabel = new JLabel("Крайна цена");
		finalValueField = new JTextField(5);
		// finalValueField.setEditable(false);
		finalValueField.setForeground(Color.red);
		finalValueField.addKeyListener(new KeyAdapter() {
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

		JLabel percentProfitLabel = new JLabel("Процент печалба");
		percentProfitField = new JTextField(5);

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
				finalValueField.setText(finalValue + "");
			}
		});
		JLabel operatorLabel = new JLabel("Оператор");
		JTextField operatorField = new JTextField("", 20);
		operatorField.setText(operator);

		JButton importButton = new JButton();
		importButton.setText("Запиши");
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ChangePriceOfNewExtinguisherDialog.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				String price = finalValueField.getText();
				String percentProfit = percentProfitField.getText();
				String type = typeField.getText();
				String wheight = wheightField.getText();
				String category = categoryField.getText();
				String brand = brandField.getText();
				String kontragent = kontragentsField.getText();
				String invoiceByKontragent = invoiceField.getText();

				NewExtinguisherService service = new NewExtinguisherService();
				service.editExtinguisherPrice(price, percentProfit, kontragent, invoiceByKontragent,
						type, wheight, category, brand, new RequestCallback2() {
							@Override
							public <T> void callback(T t) {

								jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

								Integer result = (Integer) t;
								if(result > 0) {
									JOptionPane.showMessageDialog(null,
											"Данните са записани успешно!");
								}
							}
						});
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
		gbc10.gridwidth = 5;

		GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.gridx = 0;
		gbc01.gridy = 1;
		gbc01.gridwidth = 1;

		GridBagConstraints gbc11 = new GridBagConstraints();

		gbc11.gridx = 1;
		gbc11.gridy = 1;
		gbc11.gridwidth = 1;

		GridBagConstraints gbc02 = new GridBagConstraints();
		gbc02.gridx = 0;
		gbc02.gridy = 2;
		gbc02.gridwidth = 1;

		GridBagConstraints gbc12 = new GridBagConstraints();

		gbc12.gridx = 1;
		gbc12.gridy = 2;
		gbc12.gridwidth = 1;

		GridBagConstraints gbc03 = new GridBagConstraints();
		gbc03.gridx = 0;
		gbc03.gridy = 3;
		gbc03.gridwidth = 1;

		GridBagConstraints gbc13 = new GridBagConstraints();

		gbc13.gridx = 1;
		gbc13.gridy = 3;
		gbc13.gridwidth = 1;

		GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.gridx = 0;
		gbc04.gridy = 4;
		gbc04.gridwidth = 1;

		GridBagConstraints gbc14 = new GridBagConstraints();

		gbc14.gridx = 1;
		gbc14.gridy = 4;
		gbc14.gridwidth = 1;

		GridBagConstraints gbc05 = new GridBagConstraints();
		gbc05.gridx = 0;
		gbc05.gridy = 5;
		gbc05.gridwidth = 1;

		GridBagConstraints gbc15 = new GridBagConstraints();

		gbc15.gridx = 1;
		gbc15.gridy = 5;
		gbc15.gridwidth = 1;

		GridBagConstraints gbc06 = new GridBagConstraints();
		gbc06.gridx = 0;
		gbc06.gridy = 6;
		gbc06.gridwidth = 1;

		GridBagConstraints gbc16 = new GridBagConstraints();

		gbc16.gridx = 1;
		gbc16.gridy = 6;
		gbc16.gridwidth = 1;

		GridBagConstraints gbc07 = new GridBagConstraints();

		gbc07.gridx = 0;
		gbc07.gridy = 7;
		gbc07.gridwidth = 1;

		GridBagConstraints gbc17 = new GridBagConstraints();

		gbc17.gridx = 1;
		gbc17.gridy = 7;
		gbc17.gridwidth = 1;


		GridBagConstraints gbc09 = new GridBagConstraints();
		gbc09.gridx = 0;
		gbc09.gridy = 9;
		gbc09.gridwidth = 1;

		GridBagConstraints gbc19 = new GridBagConstraints();
		gbc19.gridx = 1;
		gbc19.gridy = 9;
		gbc19.gridwidth = 1;

		GridBagConstraints gbc29 = new GridBagConstraints();
		gbc29.fill = GridBagConstraints.HORIZONTAL;
		gbc29.gridx = 2;
		gbc29.gridy = 9;
		gbc29.gridwidth = 1;

		GridBagConstraints gbc39 = new GridBagConstraints();
		gbc39.fill = GridBagConstraints.HORIZONTAL;
		gbc39.gridx = 3;
		gbc39.gridy = 9;
		gbc39.gridwidth = 1;

		GridBagConstraints gbc010 = new GridBagConstraints();
		gbc010.fill = GridBagConstraints.HORIZONTAL;
		gbc010.gridx = 0;
		gbc010.gridy = 10;
		gbc010.gridwidth = 1;

		GridBagConstraints gbc110 = new GridBagConstraints();
		gbc110.gridx = 1;
		gbc110.gridy = 10;
		gbc110.gridwidth = 1;

		GridBagConstraints gbc210 = new GridBagConstraints();
		gbc210.fill = GridBagConstraints.HORIZONTAL;
		gbc210.gridx = 2;
		gbc210.gridy = 10;
		gbc210.gridwidth = 1;

		helpPanel.add(kontragentLabel, gbc00);
		helpPanel.add(kontragentsField, gbc10);
		helpPanel.add(invoiceLabel, gbc01);
		helpPanel.add(invoiceField, gbc11);
		helpPanel.add(dateLabel, gbc02);
		helpPanel.add(dateField, gbc12);
		helpPanel.add(typeLabel, gbc03);
		helpPanel.add(typeField, gbc13);
		helpPanel.add(wheightLabel, gbc04);
		helpPanel.add(wheightField, gbc14);
		helpPanel.add(categoryLabel, gbc05);
		helpPanel.add(categoryField, gbc15);
		helpPanel.add(brandlabel, gbc06);
		helpPanel.add(brandField, gbc16);
		helpPanel.add(percentProfitLabel, gbc07);
		helpPanel.add(percentProfitField, gbc17);
		helpPanel.add(deliveryValueButton, gbc09);
		helpPanel.add(deliveryValueField, gbc19);
		helpPanel.add(finalValueFieldLabel, gbc29);
		helpPanel.add(finalValueField, gbc39);

		helpPanel.add(operatorLabel, gbc010);
		helpPanel.add(operatorField, gbc110);
		helpPanel.add(importButton, gbc210);

		basePanel.add(helpPanel);
		this.add(basePanel);
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
