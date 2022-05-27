package admin.Artikul;

import admin.Artikul.Workers.GetCurrArtikulValueInSkladWorker;
import admin.Artikul.Workers.InsertArtikulWorker;
import mydate.MyGetDate;
import utility.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddArtikulGreyDialog extends MainPanel {

	// private ClientsListComboBox2 clientComboBox;
	private final ClientsListComboBox2 clientComboBox;
	private EditableField invoiceField = null;
	// private EditableField artikulField = null;
	private final ArtikulsListComboBox artikulsComboBox;
	private JTextField skladField = null;
	private JTextField medField = null;
	private JTextField deliveryValueField = null;
	private JTextField currValueField = null;
	private JTextField bigFinalValueField = null;
	private JTextField percentProfitField = null;
	private JTextField dateField = null;
	private JTextField personField = null;
	JButton saveInDBButton = new JButton("Запази");

	// private TooltipButton deleteButton = null;

	public static void main(String[] args) {
		// AddArtikulDialog dialog =
		// new
		// AddArtikulDialog("admin.artikul","nalicni","cena","faktura","kontragent","operator");
		// JustFrame start = new JustFrame(dialog);
		// start.pack();
		// start.setResizable(false);
		// start.setFrameLocationOnTheCenter();
	}

	private String medItem;
	private String oldValueItem;
	private String currValueItem;

	public AddArtikulGreyDialog(final String artikulItem, String skladitem,
                                final String medItem, final String oldValueItem,
                                final String invoiceNumber, final String client, final String date,
                                final String seller, String percentProfit) {
		this.medItem = medItem;
		this.oldValueItem = oldValueItem;

		JPanel basePanel2 = new JPanel();
		basePanel2.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridBagLayout());

		JLabel clientLabel = new JLabel("Контрагент");
		JLabel invoiceLabel = new JLabel("Фактура No:");
		JLabel artikulLabel = new JLabel("Артикул");
		JLabel skladLabel = new JLabel("Брой");
		JLabel medLabel = new JLabel("Мер. единица  ");
		JButton currValueButton = new JButton("Текуща цена");
		currValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// first get biggest value for curent admin.artikul
				String artikul = artikulsComboBox.getEditor().getItem()
						.toString();
				/*
				 * String kontragent =
				 * clientComboBox.getSelectedItem().toString(); String
				 * invoiceByKontragent = invoiceField.getText();
				 */

				double maxArtikulValue = new GetCurrArtikulValueInSkladWorker(
						artikul/* , kontragent, invoiceByKontragent */)
						.doInBackground();
				currValueField.setText(maxArtikulValue + "");
				bigFinalValueField.setText(maxArtikulValue+"");
			}

		});
		JLabel deliveryValueLabel = new JLabel("Доставна цена");

		JLabel dateLabel = new JLabel("Дата");
		JLabel personLabel = new JLabel("Оператор");

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());

		artikulsComboBox = new ArtikulsListComboBox(GREY_AVAILABLE_ARTIKULS);
		artikulsComboBox.setSelectedItem(artikulItem);

		clientComboBox = new ClientsListComboBox2();
		clientComboBox.setSelectedItem(client);
		clientComboBox.setEnabled(false);// setEditable make program crash ! I don't know why

		invoiceField = new EditableField("", 10);
		invoiceField.setText(invoiceNumber);
		invoiceField.setEnabled(false);
		invoiceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				//JTextField textField = (JTextField) ke.getSource();
				//String invoiceNum = textField.getText();
				// System.out.printf("%s\n",
				// AddArtikulDialog.this.invoiceNumber);

			}
		});

		// artikulField = new EditableField("",10);

		skladField = new JTextField(10);
		skladField.setText(skladitem);
		skladField.setForeground(Color.red);
		skladField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				//JTextField textField = (JTextField) ke.getSource();
				//String skladNum = textField.getText();
				// System.out.printf("%s\n", AddArtikulDialog.this.skladItem);

			}
		});

		medField = new JTextField(10);
		medField.setText("брой");
		medField.setEditable(true);
		medField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				String med = textField.getText();
				AddArtikulGreyDialog.this.medItem = med;
				// System.out.printf("%s\n", AddArtikulDialog.this.medItem);

			}
		});

		currValueField = new JTextField(10);
		currValueField.setEditable(true);
		currValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				String oldVal = textField.getText();
				AddArtikulGreyDialog.this.oldValueItem = oldVal;
				// System.out.printf("%s\n",
				// AddArtikulDialog.this.oldValueItem);

			}
		});
		// oldValueField.setForeground(Color.red);
		deliveryValueField = new JTextField(10);
		deliveryValueField.setForeground(Color.red);
		deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				JTextField textField = (JTextField) ke.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
					return;
				}
				setPercentProfit(currValueField.getText(), textField.getText(), bigFinalValueField.getText());

			}
		});

		bigFinalValueField = new JTextField(10);
		bigFinalValueField.setForeground(Color.red);
		bigFinalValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				if (textField.getText().equals("")
						|| textField.getText().isEmpty()) {
					return;
				}
				setPercentProfit(currValueField.getText(), deliveryValueField
						.getText(),textField.getText());
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
					return;
				}
				double finalValue = calcFinalValue(currValueField.getText(),
						deliveryValueField.getText(), textField.getText()/* percentProfit */);
				bigFinalValueField.setText(finalValue + "");
			}
		});

		dateField = new JTextField(5);
		dateField.setText(MyGetDate.getReversedSystemDate());
		personField = new JTextField(10);
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
		gbc36.gridx = 3;
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
				if (!checkUserInput(artikulsComboBox.getEditor().getItem()
						.toString(), skladField.getText(), medField.getText(),
						deliveryValueField.getText(),
						bigFinalValueField.getText(), invoiceField.getText(),
						clientComboBox.getSelectedItem().toString(),
						dateField.getText(),
						percentProfitField.getText())) {
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
						.getWindowAncestor(AddArtikulGreyDialog.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				InsertArtikulWorker add = new InsertArtikulWorker(
						MainPanel.GREY_AVAILABLE_ARTIKULS,
						clientComboBox, artikulsComboBox, skladField, medField,
						currValueField, deliveryValueField, bigFinalValueField,
						invoiceField, dateField, personField,
						percentProfitField, jd);
				add.execute();


			}

			// }

		});

		rightPanel.add(clientLabel, gbc);
		rightPanel.add(clientComboBox, gbc10);
		rightPanel.add(artikulLabel, gbc01);
		rightPanel.add(artikulsComboBox, gbc11);

		rightPanel.add(invoiceLabel, gbc02);
		rightPanel.add(invoiceField, gbc12);

		rightPanel.add(dateLabel, gbc03);
		rightPanel.add(dateField, gbc13);

		rightPanel.add(skladLabel, gbc04);
		rightPanel.add(skladField, gbc14);

		rightPanel.add(medLabel, gbc05);
		rightPanel.add(medField, gbc15);

		rightPanel.add(currValueButton, gbc06);
		rightPanel.add(currValueField, gbc16);
		JPanel percentProfitPanel = new JPanel();
		percentProfitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		percentProfitPanel.add(new JLabel("% Печалба"));
		percentProfitPanel.add(percentProfitField);
		rightPanel.add(percentProfitPanel, gbc26);

		rightPanel.add(deliveryValueLabel, gbc07);
		rightPanel.add(deliveryValueField, gbc17);

		JPanel middleFinalValuePanel = new JPanel();
		middleFinalValuePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		middleFinalValuePanel.add(new JLabel("Крайна цена"));
		middleFinalValuePanel.add(bigFinalValueField);
		rightPanel.add(middleFinalValuePanel, gbc27);

//		JButton loadButton = new JButton("Презареди");
//		loadButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				artikulsComboBox.refreshData();
//			}
//
//		});


		rightPanel.add(saveInDBButton, gbc28);

		basePanel2.add(rightPanel);

		this.add(basePanel2);
	}

	private double calcFinalValue(String oldValue, String currValue,
			String percentProfit) {
		double oldVal = 0;
		double currVal = 0;
		double percentProf = 0;
		try {
			if (oldValue.equals("") || oldValue.isEmpty()) {
				oldVal = 0;
			} else {
				oldVal = Double.parseDouble(oldValue);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
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
		double finalVal = (currVal >= oldVal) ? currVal : oldVal;
		return MyMath
				.round(finalVal
						+ MyMath.getValueFromPercent(finalVal, percentProf), 2);

	}
	private boolean checkUserInput(String artikulItem, String skladItem,
								   String medItem, String oldValueItem, String currValueItem,
								   String invoiceNumber, String client, String date,
								   String percentProfitItem) {
		if (artikulItem.equals("") || skladItem.equals("")
				|| medItem.equals("") || oldValueItem.equals("")
				|| currValueItem.equals("") || invoiceNumber.equals("")
				|| client.equals("") || date.equals("")
				|| percentProfitItem.equals("") ) {
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
