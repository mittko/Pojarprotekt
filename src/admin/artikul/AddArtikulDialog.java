

//
// Decompiled by Procyon v0.5.36
//

package admin.artikul;

import admin.artikul.workers.GetCurrArtikulValueInSkladWorker;
import utils.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mydate.MyGetDate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public abstract class AddArtikulDialog extends MainPanel
{
	private final ClientsListComboBox2 clientComboBox;
	private EditableField invoiceField;
	private final ArtikulsListComboBox artikulsComboBox;
	private JTextField skladField;
	private MedComboBox medComboBox;
	private JTextField deliveryValueField;
	private JTextField currValueField;

	private JTextField bigFinalValueField;
	private JTextField percentProfitField;
	private JTextField dateField;
	private JTextField personField;

	JButton saveInDBButton;
	private String currValueItem;

	public static void main(final String[] args) {

	}

	public AddArtikulDialog(final String artikulsDB, final String artikulItem, final String skladitem, final String invoiceNumber, final String client, final String percentProfit) {
		this.invoiceField = null;
		this.skladField = null;
		this.medComboBox = null;
		this.deliveryValueField = null;
		this.bigFinalValueField = null;
		this.percentProfitField = null;
		this.dateField = null;
		this.personField = null;
		this.saveInDBButton = new JButton("Запази");
		final JPanel basePanel2 = new JPanel();
		basePanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		final JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridBagLayout());
		final JLabel clientLabel = new JLabel("Контрагент");
		final JLabel invoiceLabel = new JLabel("Фактура No:");
		final JLabel artikulLabel = new JLabel("Артикул");
		final JLabel skladLabel = new JLabel("Брой");
		final JLabel medLabel = new JLabel("Мер. единица  ");
		final JButton currValueButton = new JButton("Текуща сена");
		currValueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                String item = artikulsComboBox.getSelectedItem().toString();
                double value = new GetCurrArtikulValueInSkladWorker(
						artikulsDB,
						item
				).doInBackground();
				currValueField.setText(String.format("%.2f",value).replace(",","."));
			}
		});
		final JButton deliveryValueButton = new JButton("Доставна цена");
		deliveryValueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
               String item = artikulsComboBox.getSelectedItem().toString();
			   double value = new GetCurrArtikulValueInSkladWorker(
					   DELIVERY_ARTIKULS,
					   item
			   ).doInBackground();
			   deliveryValueField.setText(String.format("%.2f",value).replace(",","."));
			}
		});
		final JLabel dateLabel = new JLabel("    Дата");
		final JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());
		(this.artikulsComboBox = new ArtikulsListComboBox(artikulsDB)).setSelectedItem(artikulItem);
		(this.clientComboBox = new ClientsListComboBox2()).setSelectedItem(client);
		this.clientComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent event) {
				if (event.getStateChange() == 1) {}
			}
		});
		(this.invoiceField = new EditableField("", 10)).setText(invoiceNumber);
		this.invoiceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				final String invoiceNum = textField.getText();
			}
		});
		(this.skladField = new JTextField(3)).setText(skladitem);
		this.skladField.setForeground(Color.red);
		this.skladField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				final String skladNum = textField.getText();
			}
		});
		this.medComboBox = new MedComboBox();

		(this.deliveryValueField = new JTextField(6)).setForeground(Color.red);
		this.deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				if (textField.getText().equals("") || textField.getText().isEmpty()) {}
				AddArtikulDialog.this.setPercentProfit("0", textField.getText(), AddArtikulDialog.this.bigFinalValueField.getText());
			}
		});
		this.currValueField = new JTextField(6);
		this.currValueField.setEditable(false);
		this.currValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
			}
		});
		(this.bigFinalValueField = new JTextField(6)).setForeground(Color.red);
		this.bigFinalValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				final JTextField textField = (JTextField)e.getSource();
				if (textField.getText().equals("") || textField.getText().isEmpty()) {
					return;
				}
				AddArtikulDialog.this.setPercentProfit("0", AddArtikulDialog.this.deliveryValueField.getText(), textField.getText());
			}
		});
		(this.percentProfitField = new JTextField(5)).setText(percentProfit);
		this.percentProfitField.setForeground(Color.red);
		this.percentProfitField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				final JTextField textField = (JTextField)e.getSource();
				if (textField.getText().equals("") || textField.getText().isEmpty()) {}
				final double finalValue = AddArtikulDialog.this.calcFinalValue("0", AddArtikulDialog.this.deliveryValueField.getText(), textField.getText());
				AddArtikulDialog.this.bigFinalValueField.setText(finalValue + "");
			}
		});
		(this.dateField = new JTextField(10)).setText(MyGetDate.getReversedSystemDate());
		(this.personField = new JTextField(10)).setText(MainPanel.personName);

		final EditableField barcodeField = new EditableField("Баркод", 10);

		final GridBagConstraints gbc00 = new GridBagConstraints();
		gbc00.fill = GridBagConstraints.HORIZONTAL;
		gbc00.gridx = 0;
		gbc00.gridy = 0;
		gbc00.gridwidth = 2;
		gbc00.insets = new Insets(0, 0, 0, 0);

		final GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.fill = GridBagConstraints.HORIZONTAL;
		gbc01.gridx = 0;
		gbc01.gridy = 1;
		gbc01.gridwidth = 3;
		gbc01.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc02 = new GridBagConstraints();
		gbc02.fill = GridBagConstraints.HORIZONTAL;
		gbc02.gridx = 0;
		gbc02.gridy = 2;
		gbc02.gridwidth = 1;
		gbc02.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc03 = new GridBagConstraints();
		gbc03.fill = 2;
		gbc03.gridx = 0;
		gbc03.gridy = 3;
		gbc03.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.fill = 2;
		gbc04.gridx = 0;
		gbc04.gridy = 4;
		gbc04.gridwidth = 1;
		gbc04.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc05 = new GridBagConstraints();
		gbc05.fill = 2;
		gbc05.gridx = 0;
		gbc05.gridy = 5;
		gbc05.gridwidth = 1;
		gbc05.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.fill = GridBagConstraints.HORIZONTAL;
		gbc10.gridx = 1;
		gbc10.gridy = 0;
		gbc10.gridwidth = 3;
		gbc10.insets = new Insets(0, 0, 0, 0);

		final GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.fill = 2;
		gbc11.gridx = 1;
		gbc11.gridy = 1;
		gbc11.gridwidth = 3;
		gbc11.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc12 = new GridBagConstraints();
		gbc12.fill = GridBagConstraints.HORIZONTAL;
		gbc12.gridx = 1;
		gbc12.gridy = 2;
		gbc12.gridwidth = 1;
		gbc12.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc13 = new GridBagConstraints();
		gbc13.fill = 2;
		gbc13.gridx = 1;
		gbc13.gridy = 3;
		gbc13.gridwidth = 1;
		gbc13.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc14 = new GridBagConstraints();
		gbc14.fill = 2;
		gbc14.gridx = 1;
		gbc14.gridy = 4;
		gbc14.gridwidth = 1;
		gbc14.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc15 = new GridBagConstraints();
		gbc15.fill = 2;
		gbc15.gridx = 1;
		gbc15.gridy = 5;
		gbc15.gridwidth = 1;
		gbc15.insets = new Insets(5, 0, 0, 0);

		GridBagConstraints gbc06 = new GridBagConstraints();
		gbc06.gridx = 0;
		gbc06.gridy = 6;

		GridBagConstraints gbc16 = new GridBagConstraints();
		gbc16.gridx = 2;
		gbc16.gridy = 6;

		final JPanel percentProfitPanel = new JPanel();
		percentProfitPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		percentProfitPanel.add(new JLabel("% Печалба"));
		percentProfitPanel.add(this.percentProfitField);

		JPanel invoicePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		invoicePanel.add(invoiceField);
		invoicePanel.add(dateLabel);
		invoicePanel.add(dateField);

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.LEADING));

		JPanel skladFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		skladFieldPanel.add(skladField);
		skladFieldPanel.add(medLabel);
		skladFieldPanel.add(medComboBox);

		final JPanel deliveryFinalValuePanel = new JPanel();
		deliveryFinalValuePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		deliveryFinalValuePanel.add(deliveryValueField);
		deliveryFinalValuePanel.add(new JLabel("Крайна цена"));
		deliveryFinalValuePanel.add(this.bigFinalValueField);

		JPanel currValuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		currValuePanel.add(currValueField);
		currValuePanel.add(percentProfitPanel);

		JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		savePanel.add(new JLabel("                "));
		savePanel.add(saveInDBButton);

		this.saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				if (!AddArtikulDialog.this.checkUserInput(AddArtikulDialog.this.artikulsComboBox.getEditor().getItem().toString(), AddArtikulDialog.this.skladField.getText(), AddArtikulDialog
						.this.medComboBox.getSelectedItem().toString(), AddArtikulDialog.this.deliveryValueField.getText(), AddArtikulDialog.this.bigFinalValueField.getText(), AddArtikulDialog.this.invoiceField.getText(), AddArtikulDialog.this.clientComboBox.getSelectedItem().toString(), AddArtikulDialog.this.dateField.getText(), AddArtikulDialog.this.percentProfitField.getText())) {
					return;
				}
				saveInDB(AddArtikulDialog.this.clientComboBox, AddArtikulDialog.this.artikulsComboBox,
						AddArtikulDialog.this.skladField, AddArtikulDialog.this.medComboBox,
						AddArtikulDialog.this.deliveryValueField, AddArtikulDialog.this.bigFinalValueField,
						AddArtikulDialog.this.invoiceField,
						AddArtikulDialog.this.dateField, AddArtikulDialog.this.personField,
						AddArtikulDialog.this.percentProfitField,barcodeField);
			}
		});
		rightPanel.add(clientLabel, gbc00);
		rightPanel.add(artikulLabel, gbc01);
		rightPanel.add(invoiceLabel,gbc02);
		rightPanel.add(skladLabel, gbc03);
		rightPanel.add(currValueButton, gbc04);
		rightPanel.add(deliveryValueButton, gbc05);

		rightPanel.add(barcodeField,gbc06);

		rightPanel.add(this.clientComboBox, gbc10);
		rightPanel.add(this.artikulsComboBox, gbc11);
		rightPanel.add(invoicePanel, gbc12);
		rightPanel.add(skladFieldPanel, gbc13);
		rightPanel.add(currValuePanel, gbc14);
		rightPanel.add(deliveryFinalValuePanel,gbc15);


		rightPanel.add(savePanel, gbc16);


		final JButton loadButton = new JButton("Презареди");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				AddArtikulDialog.this.artikulsComboBox.refreshData();
			}
		});

		barcodeField.setPreferredSize(new Dimension((int)(deliveryFinalValuePanel.getPreferredSize().getWidth() * 0.2f),
				(int)(deliveryFinalValuePanel.getPreferredSize().getHeight() * 0.9f)));
		barcodeField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(barcodeField.getText().isEmpty()) {
					return;
				}

				ArrayList<String> list = selectArtikulsByBarcode(barcodeField.getText());
				if(list != null) {
					if(list.size() > 0) {
						artikulsComboBox.setSelectedItem(list.get(0));
						clientComboBox.setSelectedItem(list.get(5));
					} else {

						artikulsComboBox.setSelectedItem("");
						clientComboBox.setSelectedItem("");
						JOptionPane.showMessageDialog(null,"Не е намерен такъв елемент !");

					}
				}
			}
		});





		basePanel2.add(rightPanel);
		this.add(basePanel2);
	}

	private double calcFinalValue(final String oldValue, final String currValue, final String percentProfit) {
		double oldVal = 0.0;
		double currVal = 0.0;
		double percentProf = 0.0;
		try {
			if (oldValue.isEmpty()) {
				oldVal = 0.0;
			} else {
				oldVal = Double.parseDouble(oldValue);
			}
		} catch (NumberFormatException e) {
			return -1.0;
		}
		try {
			if (currValue.isEmpty()) {
				currVal = 0.0;
			} else {
				currVal = Double.parseDouble(currValue);
			}
		} catch (NumberFormatException e) {
			return -1.0;
		}
		try {
			if (percentProfit.isEmpty()) {
				percentProf = 0.0;
			} else {
				percentProf = Double.parseDouble(percentProfit);
			}
		} catch (NumberFormatException e) {
			return -1.0;
		}
		final double finalVal = Math.max(currVal, oldVal);
		return MyMath.round(finalVal + MyMath.getValueFromPercent(finalVal, percentProf), 2);
	}

	private boolean checkUserInput(final String artikulItem, final String skladItem, final String medItem, final String oldValueItem, final String currValueItem, final String invoiceNumber, final String client, final String date, final String percentProfitItem) {
		if (artikulItem.equals("") || skladItem.equals("") || medItem.equals("") || oldValueItem.equals("") || currValueItem.equals("") || invoiceNumber.equals("") || client.equals("") || date.equals("") || percentProfitItem.equals("")) {
			JOptionPane.showMessageDialog(null, "\u0418\u043c\u0430 \u043d\u0435\u043f\u043e\u043f\u044a\u043b\u043d\u0435\u043d\u0438 \u043f\u043e\u043b\u0435\u0442\u0430 !");
			return false;
		}
		try {
			final Double d1 = Double.parseDouble(skladItem);
			final Double d2 = Double.parseDouble(oldValueItem);
			final Double d3 = Double.parseDouble(currValueItem);
			final Double d4 = Double.parseDouble(percentProfitItem);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "\u041d\u0435\u0432\u0430\u043b\u0438\u0434\u043d\u0438 \u0434\u0430\u043d\u043d\u0438!");
			return false;
		}
		return true;
	}

	private void setPercentProfit(final String currVal, final String deliveryVal, final String finalValue) {
		double currValue = 0.0;
		double deliveryValue = 0.0;
		double bigFinalValue = 0.0;
		try {
			currValue = Double.parseDouble(currVal);
		} catch (Exception ex) {
			currValue = 0.0;
		}
		try {
			deliveryValue = Double.parseDouble(deliveryVal);
		} catch (Exception ex) {
			deliveryValue = 0.0;
		}
		try {
			bigFinalValue = Double.parseDouble(finalValue);
		} catch (Exception ex) {
			bigFinalValue = 0.0;
		}
		final double bestValue = Math.max(currValue, bigFinalValue);
		final double differents = bestValue - deliveryValue;
		final double wantedPercent = MyMath.round(differents / deliveryValue * 100.0, 2);
		this.percentProfitField.setText(wantedPercent + "");
	}

	boolean isAvailable(final String artikulItem) {
		for (int i = 0; i < AvailableArtikulsTable.artikulTableModel.getRowCount(); ++i) {
			if (AvailableArtikulsTable.artikulTableModel.getValueAt(i, 0).toString().equals(artikulItem)) {
				return true;
			}
		}
		return false;
	}

	public abstract void saveInDB(ClientsListComboBox2 clientComboBox,
								  ArtikulsListComboBox artikulsComboBox,
								  JTextField skladField, MedComboBox medComboBox,
								  JTextField deliveryValueField,
								  JTextField bigFinalValueField,
								  EditableField invoiceField,
								  JTextField dateField,
								  JTextField personField,JTextField percentProfitField,
								  EditableField barcodeField);

	public abstract ArrayList<String> selectArtikulsByBarcode(String command);
}

