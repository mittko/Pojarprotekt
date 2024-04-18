

//
// Decompiled by Procyon v0.5.36
//

package admin.аrtikul;

import Exceptions.ErrorDialog;
import admin.аrtikul.Workers.InsertArtikulWorker;
import db.аrtikul.Artikuli_DB;
import run.JDialoger;
import utils.MyMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import java.text.ParseException;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
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
import utils.ArtikulsListComboBox;
import utils.EditableField;
import utils.ClientsListComboBox2;
import utils.MainPanel;

public abstract class AddArtikulDialog extends MainPanel
{
	private final ClientsListComboBox2 clientComboBox;
	private EditableField invoiceField;
	private final ArtikulsListComboBox artikulsComboBox;
	private JTextField skladField;
	private JTextField medField;
	private JTextField deliveryValueField;
	private JTextField bigFinalValueField;
	private JTextField percentProfitField;
	private JTextField dateField;
	private JTextField personField;

	JButton saveInDBButton;
	private String currValueItem;

	public static void main(final String[] args) {

	}

	public AddArtikulDialog(String artikulsDB, final String artikulItem, final String skladitem,  final String invoiceNumber, final String client,  final String percentProfit) {
		this.invoiceField = null;
		this.skladField = null;
		this.medField = null;
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
		final JButton deliveryValueButton = new JButton("Доставна цена");
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
		(this.medField = new JTextField(6)).setText("брой");
		this.medField.setEditable(true);
		this.medField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				final String med = textField.getText();
			}
		});

		(this.deliveryValueField = new JTextField(6)).setForeground(Color.red);
		this.deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				if (textField.getText().equals("") || textField.getText().isEmpty()) {}
				AddArtikulDialog.this.setPercentProfit("0", textField.getText(), AddArtikulDialog.this.bigFinalValueField.getText());
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

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		final GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = 2;
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.gridwidth = 3;
		gbc2.insets = new Insets(0, 0, 0, 0);
		final GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.fill = 2;
		gbc3.gridx = 0;
		gbc3.gridy = 1;
		gbc3.gridwidth = 3;
		gbc3.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.fill = 2;
		gbc4.gridx = 1;
		gbc4.gridy = 1;
		gbc4.gridwidth = 3;
		gbc4.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.HORIZONTAL;
		gbc5.gridx = 0;
		gbc5.gridy = 2;
		gbc5.gridwidth = 1;
		gbc5.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = GridBagConstraints.HORIZONTAL;
		gbc6.gridx = 1;
		gbc6.gridy = 2;
		gbc6.gridwidth = 1;
		gbc6.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.fill = 2;
		gbc11.gridx = 0;
		gbc11.gridy = 4;
		gbc11.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc12 = new GridBagConstraints();
		gbc12.fill = 2;
		gbc12.gridx = 1;
		gbc12.gridy = 4;
		gbc12.gridwidth = 1;
		gbc12.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc22 = new GridBagConstraints();
		gbc22.fill = 2;
		gbc22.gridx = 0;
		gbc22.gridy = 7;
		gbc22.gridwidth = 1;
		gbc22.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc23 = new GridBagConstraints();
		gbc23.fill = 2;
		gbc23.gridx = 1;
		gbc23.gridy = 7;
		gbc23.gridwidth = 1;
		gbc23.insets = new Insets(5, 0, 0, 0);

		final GridBagConstraints gbc25 = new GridBagConstraints();
		gbc25.fill = 2;
		gbc25.gridx = 0;
		gbc25.gridy = 8;
		gbc25.gridwidth = 1;
		gbc25.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc26 = new GridBagConstraints();
		gbc26.fill = 2;
		gbc26.gridx = 1;
		gbc26.gridy = 8;
		gbc26.gridwidth = 1;
		gbc26.insets = new Insets(5, 0, 0, 0);

		this.saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				if (!AddArtikulDialog.this.checkUserInput(AddArtikulDialog.this.artikulsComboBox.getEditor().getItem().toString(), AddArtikulDialog.this.skladField.getText(), AddArtikulDialog.this.medField.getText(), AddArtikulDialog.this.deliveryValueField.getText(), AddArtikulDialog.this.bigFinalValueField.getText(), AddArtikulDialog.this.invoiceField.getText(), AddArtikulDialog.this.clientComboBox.getSelectedItem().toString(), AddArtikulDialog.this.dateField.getText(), AddArtikulDialog.this.percentProfitField.getText())) {
					return;
				}
				saveInDB(AddArtikulDialog.this.clientComboBox, AddArtikulDialog.this.artikulsComboBox, AddArtikulDialog.this.skladField, AddArtikulDialog.this.medField, AddArtikulDialog.this.deliveryValueField, AddArtikulDialog.this.bigFinalValueField, AddArtikulDialog.this.invoiceField,
						AddArtikulDialog.this.dateField, AddArtikulDialog.this.personField,
						AddArtikulDialog.this.percentProfitField,barcodeField);
			}
		});
		rightPanel.add(clientLabel, gbc);
		rightPanel.add(this.clientComboBox, gbc2);
		rightPanel.add(artikulLabel, gbc3);
		rightPanel.add(this.artikulsComboBox, gbc4);

		rightPanel.add(invoiceLabel,gbc5);

		JPanel invoicePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		invoicePanel.add(invoiceField);
		invoicePanel.add(dateLabel);
		invoicePanel.add(dateField);

		rightPanel.add(invoicePanel, gbc6);

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.LEADING));

		rightPanel.add(skladLabel, gbc11);

		JPanel skladFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		skladFieldPanel.add(skladField);
		skladFieldPanel.add(medLabel);
		skladFieldPanel.add(medField);

		rightPanel.add(skladFieldPanel, gbc12);

		final JPanel percentProfitPanel = new JPanel();
		percentProfitPanel.setLayout(new FlowLayout(0));
		percentProfitPanel.add(new JLabel("% Печалба"));
		percentProfitPanel.add(this.percentProfitField);

		rightPanel.add(deliveryValueButton, gbc22);

		final JPanel middleFinalValuePanel = new JPanel();
		middleFinalValuePanel.setLayout(new FlowLayout(0));
		middleFinalValuePanel.add(deliveryValueField);
		middleFinalValuePanel.add(new JLabel("Крайна цена"));
		middleFinalValuePanel.add(this.bigFinalValueField);
		middleFinalValuePanel.add(percentProfitPanel);
		rightPanel.add(middleFinalValuePanel, gbc23);
		final JButton loadButton = new JButton("Презареди");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				AddArtikulDialog.this.artikulsComboBox.refreshData();
			}
		});

		barcodeField.setPreferredSize(new Dimension((int)(middleFinalValuePanel.getPreferredSize().getWidth() * 0.2f),
				(int)(middleFinalValuePanel.getPreferredSize().getHeight() * 0.9f)));
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
						medField.setText(list.get(2));
						clientComboBox.setSelectedItem(list.get(5));

					} else {

						artikulsComboBox.setSelectedItem("");
						medField.setText("");
						clientComboBox.setSelectedItem("");
						JOptionPane.showMessageDialog(null,"Не е намерен такъв елемент !");

					}
				}
			}
		});
		rightPanel.add(barcodeField, gbc25);

		JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		savePanel.add(new JLabel("                "));
		savePanel.add(saveInDBButton);
		rightPanel.add(savePanel, gbc26);

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
								  JTextField skladField, JTextField medField,
								  JTextField deliveryValueField,
								  JTextField bigFinalValueField,
								  EditableField invoiceField,
								  JTextField dateField,
								  JTextField personField,JTextField percentProfitField,
								  EditableField barcodeField);

	public abstract ArrayList<String> selectArtikulsByBarcode(String command);
}

