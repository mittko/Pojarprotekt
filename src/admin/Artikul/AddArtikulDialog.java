

//
// Decompiled by Procyon v0.5.36
//

package admin.Artikul;

import admin.Artikul.Workers.InsertArtikulWorker;
import utility.MyMath;
import java.awt.FlowLayout;
import java.util.Date;
import java.awt.Cursor;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import java.text.ParseException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import mydate.MyGetDate;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import utility.ArtikulsListComboBox;
import utility.EditableField;
import utility.ClientsListComboBox2;
import utility.MainPanel;

public class AddArtikulDialog extends MainPanel
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
	private String medItem;
	private String oldValueItem;
	private String currValueItem;

	public static void main(final String[] args) {
	}

	public AddArtikulDialog(final String artikulItem, final String skladitem, final String medItem, final String oldValueItem, final String invoiceNumber, final String client, final String date, final String seller, final String percentProfit) {
		this.invoiceField = null;
		this.skladField = null;
		this.medField = null;
		this.deliveryValueField = null;
		this.bigFinalValueField = null;
		this.percentProfitField = null;
		this.dateField = null;
		this.personField = null;
		this.saveInDBButton = new JButton("������");
		this.medItem = medItem;
		this.oldValueItem = oldValueItem;
		final JPanel basePanel2 = new JPanel();
		basePanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		final JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridBagLayout());
		final JLabel clientLabel = new JLabel("����������");
		final JLabel invoiceLabel = new JLabel("������� No:");
		final JLabel artikulLabel = new JLabel("�������");
		final JLabel skladLabel = new JLabel("����");
		final JLabel medLabel = new JLabel("���. �������  ");
		final JLabel deliveryValueLabel = new JLabel("�������� ����");
		final JLabel dateLabel = new JLabel("����");
		final JLabel personLabel = new JLabel("��������");
		final JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());
		(this.artikulsComboBox = new ArtikulsListComboBox("ArtikulsDB")).setSelectedItem(artikulItem);
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
		(this.skladField = new JTextField(10)).setText(skladitem);
		this.skladField.setForeground(Color.red);
		this.skladField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				final String skladNum = textField.getText();
			}
		});
		(this.medField = new JTextField(10)).setText("����");
		this.medField.setEditable(true);
		this.medField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				final String med = textField.getText();
				AddArtikulDialog.this.medItem = med;
			}
		});
		(this.deliveryValueField = new JTextField(10)).setForeground(Color.red);
		this.deliveryValueField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent ke) {
				final JTextField textField = (JTextField)ke.getSource();
				if (textField.getText().equals("") || textField.getText().isEmpty()) {}
				AddArtikulDialog.this.setPercentProfit("0", textField.getText(), AddArtikulDialog.this.bigFinalValueField.getText());
			}
		});
		(this.bigFinalValueField = new JTextField(10)).setForeground(Color.red);
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
		(this.dateField = new JTextField(5)).setText(MyGetDate.getReversedSystemDate());
		(this.personField = new JTextField(10)).setText(MainPanel.personName);
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
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
		gbc3.gridwidth = 1;
		gbc3.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.fill = 2;
		gbc4.gridx = 1;
		gbc4.gridy = 1;
		gbc4.gridwidth = 3;
		gbc4.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = 2;
		gbc5.gridx = 0;
		gbc5.gridy = 2;
		gbc5.gridwidth = 1;
		gbc5.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = 2;
		gbc6.gridx = 1;
		gbc6.gridy = 2;
		gbc6.gridwidth = 1;
		gbc6.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc7 = new GridBagConstraints();
		gbc7.fill = 2;
		gbc7.gridx = 0;
		gbc7.gridy = 3;
		gbc7.gridwidth = 1;
		gbc7.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc8 = new GridBagConstraints();
		gbc8.fill = 2;
		gbc8.gridx = 1;
		gbc8.gridy = 3;
		gbc8.gridwidth = 1;
		gbc8.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc9 = new GridBagConstraints();
		gbc9.fill = 2;
		gbc9.gridx = 2;
		gbc9.gridy = 3;
		gbc9.gridwidth = 1;
		gbc9.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.fill = 2;
		gbc10.gridx = 3;
		gbc10.gridy = 3;
		gbc10.gridwidth = 1;
		gbc10.insets = new Insets(5, 0, 0, 0);
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
		final GridBagConstraints gbc13 = new GridBagConstraints();
		gbc13.fill = 2;
		gbc13.gridx = 2;
		gbc13.gridy = 4;
		gbc13.gridwidth = 1;
		gbc13.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc14 = new GridBagConstraints();
		gbc14.fill = 2;
		gbc14.gridx = 0;
		gbc14.gridy = 5;
		gbc14.gridwidth = 1;
		gbc14.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc15 = new GridBagConstraints();
		gbc15.fill = 2;
		gbc15.gridx = 1;
		gbc15.gridy = 5;
		gbc15.gridwidth = 1;
		gbc15.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc16 = new GridBagConstraints();
		gbc16.fill = 2;
		gbc16.gridx = 2;
		gbc16.gridy = 5;
		gbc16.gridwidth = 1;
		gbc16.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc17 = new GridBagConstraints();
		gbc17.fill = 2;
		gbc17.gridx = 3;
		gbc17.gridy = 5;
		gbc17.gridwidth = 1;
		gbc17.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc18 = new GridBagConstraints();
		gbc18.fill = 2;
		gbc18.gridx = 0;
		gbc18.gridy = 6;
		gbc18.gridwidth = 1;
		gbc18.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc19 = new GridBagConstraints();
		gbc19.fill = 2;
		gbc19.gridx = 1;
		gbc19.gridy = 6;
		gbc19.gridwidth = 1;
		gbc19.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc20 = new GridBagConstraints();
		gbc20.fill = 2;
		gbc20.gridx = 2;
		gbc20.gridy = 6;
		gbc20.gridwidth = 1;
		gbc20.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc21 = new GridBagConstraints();
		gbc21.fill = 2;
		gbc21.gridx = 2;
		gbc21.gridy = 6;
		gbc21.gridwidth = 1;
		gbc21.insets = new Insets(5, 0, 0, 0);
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
		final GridBagConstraints gbc24 = new GridBagConstraints();
		gbc24.fill = 2;
		gbc24.gridx = 2;
		gbc24.gridy = 7;
		gbc24.gridwidth = 1;
		gbc24.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc25 = new GridBagConstraints();
		gbc25.fill = 2;
		gbc25.gridx = 0;
		gbc25.gridy = 8;
		gbc25.gridwidth = 2;
		gbc25.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc26 = new GridBagConstraints();
		gbc26.fill = 2;
		gbc26.gridx = 1;
		gbc26.gridy = 8;
		gbc26.gridwidth = 1;
		gbc26.insets = new Insets(5, 0, 0, 0);
		final GridBagConstraints gbc27 = new GridBagConstraints();
		gbc27.fill = 2;
		gbc27.gridx = 2;
		gbc27.gridy = 8;
		gbc27.gridwidth = 2;
		gbc27.insets = new Insets(5, 0, 0, 0);
		this.saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				if (!AddArtikulDialog.this.checkUserInput(AddArtikulDialog.this.artikulsComboBox.getEditor().getItem().toString(), AddArtikulDialog.this.skladField.getText(), AddArtikulDialog.this.medField.getText(), AddArtikulDialog.this.deliveryValueField.getText(), AddArtikulDialog.this.bigFinalValueField.getText(), AddArtikulDialog.this.invoiceField.getText(), AddArtikulDialog.this.clientComboBox.getSelectedItem().toString(), AddArtikulDialog.this.dateField.getText(), AddArtikulDialog.this.percentProfitField.getText())) {
					return;
				}
				final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
				simpleDateFormat.setLenient(false);
				Date checkDate = null;
				try {
					checkDate = simpleDateFormat.parse(AddArtikulDialog.this.dateField.getText());
				}
				catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "������ ������ �� ���� !");
					return;
				}
				final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(AddArtikulDialog.this);
				jd.setCursor(new Cursor(3));
				final InsertArtikulWorker add = new InsertArtikulWorker("ArtikulsDB",
						AddArtikulDialog.this.clientComboBox,
						AddArtikulDialog.this.artikulsComboBox,
						AddArtikulDialog.this.skladField,
						AddArtikulDialog.this.medField,
						AddArtikulDialog.this.deliveryValueField,
						AddArtikulDialog.this.bigFinalValueField,
						AddArtikulDialog.this.invoiceField,
						AddArtikulDialog.this.dateField,
						AddArtikulDialog.this.personField,
						AddArtikulDialog.this.percentProfitField,
						jd);
				add.execute();
			}
		});
		rightPanel.add(clientLabel, gbc);
		rightPanel.add(this.clientComboBox, gbc2);
		rightPanel.add(artikulLabel, gbc3);
		rightPanel.add(this.artikulsComboBox, gbc4);
		rightPanel.add(invoiceLabel, gbc5);
		rightPanel.add(this.invoiceField, gbc6);
		rightPanel.add(dateLabel, gbc7);
		rightPanel.add(this.dateField, gbc8);
		rightPanel.add(skladLabel, gbc11);
		rightPanel.add(this.skladField, gbc12);
		rightPanel.add(medLabel, gbc14);
		rightPanel.add(this.medField, gbc15);

		final JPanel percentProfitPanel = new JPanel();
		percentProfitPanel.setLayout(new FlowLayout(0));
		percentProfitPanel.add(new JLabel("% �������"));
		percentProfitPanel.add(this.percentProfitField);
		rightPanel.add(percentProfitPanel, gbc21);
		rightPanel.add(deliveryValueLabel, gbc22);
		rightPanel.add(this.deliveryValueField, gbc23);
		final JPanel middleFinalValuePanel = new JPanel();
		middleFinalValuePanel.setLayout(new FlowLayout(0));
		middleFinalValuePanel.add(new JLabel("������ ����"));
		middleFinalValuePanel.add(this.bigFinalValueField);
		rightPanel.add(middleFinalValuePanel, gbc24);
		final JButton loadButton = new JButton("���������");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				AddArtikulDialog.this.artikulsComboBox.refreshData();
			}
		});
		rightPanel.add(loadButton, gbc25);
		rightPanel.add(this.saveInDBButton, gbc27);
		basePanel2.add(rightPanel);
		this.add(basePanel2);
	}

	private double calcFinalValue(final String oldValue, final String currValue, final String percentProfit) {
		double oldVal = 0.0;
		double currVal = 0.0;
		double percentProf = 0.0;
		try {
			if (oldValue.equals("") || oldValue.isEmpty()) {
				oldVal = 0.0;
			}
			else {
				oldVal = Double.parseDouble(oldValue);
			}
		}
		catch (NumberFormatException e) {
			return -1.0;
		}
		try {
			if (currValue.equals("") || currValue.isEmpty()) {
				currVal = 0.0;
			}
			else {
				currVal = Double.parseDouble(currValue);
			}
		}
		catch (NumberFormatException e) {
			return -1.0;
		}
		try {
			if (percentProfit.equals("") || percentProfit.isEmpty()) {
				percentProf = 0.0;
			}
			else {
				percentProf = Double.parseDouble(percentProfit);
			}
		}
		catch (NumberFormatException e) {
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
		}
		catch (Exception ex) {
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
		}
		catch (Exception ex) {
			currValue = 0.0;
		}
		try {
			deliveryValue = Double.parseDouble(deliveryVal);
		}
		catch (Exception ex) {
			deliveryValue = 0.0;
		}
		try {
			bigFinalValue = Double.parseDouble(finalValue);
		}
		catch (Exception ex) {
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
}

