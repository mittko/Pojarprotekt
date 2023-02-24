package NewClient.editClient;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import utility.BevelLabel;
import utility.EditableField;
import utility.LoadIcon;
import Document.TextFieldLimit;
import db.Client.FirmTable;
import utility.MainPanel;

public class EditFirm extends MainPanel {

	JPanel block = null;
	JTextField firm = null;
	JTextField address = null;
	JTextField eik = null;
	JTextField city = null;
	JTextField mol = null;
	JTextField email = null;
	JTextField person = null;
	JTextField telPerson = null;
	JTextField bank = null;
	JTextField BIC = null;
	JTextField IBAN = null;
	JTextField discountField = null;
	int edit = 0;
	private int size = 45;
	public static ArrayList<JComponent> components = null;
	private JCheckBox incorrectClientCheckBox;

	private JCheckBox registrationVatCheckbox;

	public EditFirm() {

		components = new ArrayList<JComponent>();
		Dimension dimension = new Dimension(50, getFontSize() + 15);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 5, 0);

		block = new JPanel();
		block.setOpaque(false);

		block.setLayout(new GridLayout(13, 0, 10, 0));

		JPanel firmPanel = new JPanel();
		firmPanel.setOpaque(false);

		firmPanel.setLayout(layout);

		firm = new EditableField(" Име на Фирма", size - 10, false);
		// firm.setEditable(false);
		firm.setPreferredSize(dimension);

		firmPanel.add(firm);

		JPanel cityPanel = new JPanel();
		cityPanel.setOpaque(false);
		cityPanel.setLayout(layout);

		city = new EditableField(" Град", size - 20, false);
		city.setPreferredSize(dimension);

		cityPanel.add(city);

		JPanel addressPanel = new JPanel();
		addressPanel.setOpaque(false);

		addressPanel.setLayout(layout);

		address = new EditableField(" Адрес", size, false);
		address.setPreferredSize(dimension);

		addressPanel.add(address);

		JPanel eikPanel = new JPanel();
		eikPanel.setOpaque(false);
		eikPanel.setLayout(layout);

		eik = new EditableField(" ЕИК", size - 20, false);
		eik.setPreferredSize(dimension);

		eikPanel.add(eik);

		JPanel molPanel = new JPanel();
		molPanel.setOpaque(false);
		molPanel.setLayout(layout);

		mol = new EditableField(" МОЛ", size - 20, false);
		mol.setPreferredSize(dimension);

		molPanel.add(mol);

		JPanel telOfFirmPanel = new JPanel();
		telOfFirmPanel.setOpaque(false);
		telOfFirmPanel.setLayout(layout);

		JPanel emailPanel = new JPanel();
		emailPanel.setOpaque(false);
		emailPanel.setLayout(layout);

		email = new EditableField(" email", size - 20, false);
		email.setPreferredSize(dimension);

		emailPanel.add(email);

		JPanel personPanel = new JPanel();
		personPanel.setOpaque(false);
		personPanel.setLayout(layout);

		person = new EditableField(" Лице за контакт", size, false);
		person.setPreferredSize(dimension);

		personPanel.add(person);

		JPanel telPersonPanel = new JPanel();
		telPersonPanel.setOpaque(false);
		telPersonPanel.setLayout(layout);

		telPerson = new EditableField(" Телефон за контакт", size - 20, false);
		telPerson.setPreferredSize(dimension);

		telPersonPanel.add(telPerson);

		JPanel bankPanel = new JPanel();
		bankPanel.setOpaque(false);
		bankPanel.setLayout(layout);

		bank = new EditableField(" Банка", size - 20, false);
		bank.setPreferredSize(dimension);

		bankPanel.add(bank);

		JPanel bicPanel = new JPanel();
		bicPanel.setOpaque(false);
		bicPanel.setLayout(layout);

		BIC = new EditableField(" BIC", size - 20, false);
		BIC.setPreferredSize(dimension);

		bicPanel.add(BIC);

		JPanel ibanPanel = new JPanel();
		ibanPanel.setOpaque(false);
		ibanPanel.setLayout(layout);

		IBAN = new EditableField(" IBAN", size - 20, false);
		IBAN.setPreferredSize(dimension);

		ibanPanel.add(IBAN);

		JPanel discountPanel = new JPanel();
		discountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		discountField = new EditableField(" Отстъпка", 5, false);
		discountField.setPreferredSize(new Dimension(50, getFontSize() + 15));
		discountField.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
				getFontSize() + 10));
		discountField.setDocument(new TextFieldLimit(2));
		discountField.setForeground(Color.red);
		discountPanel.add(discountField);

		block.add(firmPanel);
		block.add(cityPanel);
		block.add(addressPanel);
		block.add(eikPanel);
		block.add(molPanel);
		block.add(emailPanel);
		block.add(personPanel);
		block.add(telPersonPanel);
		block.add(bankPanel);
		block.add(bicPanel);
		block.add(ibanPanel);
		block.add(discountPanel);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(this.WIDTH / 2,
				(int) (this.HEIGHT * 0.05)));
		bottom.setOpaque(false);
		bottom.setLayout(layout);

		final JLabel doneText = new JLabel();

		final BevelLabel done = new BevelLabel((int) (bottom.getPreferredSize()
				.getHeight() * 1.0));
		/*
		 * done.setPreferredSize(new Dimension(
		 * (int)(bottom.getPreferredSize().getWidth() * 0.08),
		 * (int)(bottom.getPreferredSize().getHeight() * 1.0) ));;
		 */

		firm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (done.getIcon() != null) {
					done.setIcon(null);
					doneText.setText("");
				}
			}
		});

		JButton button = new JButton();
		button.setText("Запиши");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!isValidText()) {
					return;
				}

				final JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(EditFirm.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							String NEW_FIRM_NAME = firm.getText();
							String OLD_FIRM_NAME = EditClientPanel.clientCombo2
									.getSelectedItem().toString();

							String incorrect = incorrectClientCheckBox
									.isSelected() ? "да" : "не";
                            String isVatRegistered = registrationVatCheckbox
									.isSelected() ? "да" : "не";
							edit = FirmTable.editFirmTable(OLD_FIRM_NAME,
									NEW_FIRM_NAME, city.getText(),
									address.getText(), eik.getText(),
									mol.getText(), email.getText(),
									person.getText(), telPerson.getText(),
									bank.getText(), BIC.getText(),
									IBAN.getText(), discountField.getText(),
									incorrect, isVatRegistered);

							// here to add code to rename old name
							// with new name in all tables !!!

							int update = FirmTable.updateFirmNameInTable(
									SERVICE, OLD_FIRM_NAME, NEW_FIRM_NAME);
							// System.out.println("SERVICE " + update);
							update = FirmTable.updateFirmNameInTable(PROTOKOL,
									OLD_FIRM_NAME, NEW_FIRM_NAME);
							// System.out.println("PROTOKOL " + update);
							update = FirmTable.updateFirmNameInTable(BRACK,
									OLD_FIRM_NAME, NEW_FIRM_NAME);
							// System.out.println("BRACK " + update);
							update = FirmTable.updateFirmNameInTable(
									INVOICE_PARENT, OLD_FIRM_NAME,
									NEW_FIRM_NAME);
							// System.out.println("INVOICE_PARENT " + update);
							update = FirmTable
									.updateFirmNameInTable(INVOICE_CHILD,
											OLD_FIRM_NAME, NEW_FIRM_NAME);
							// System.out.println("INVOICE_CHILD " + update);
							update = FirmTable.updateFirmNameInTable(
									PROFORM_PARENT, OLD_FIRM_NAME,
									NEW_FIRM_NAME);
							// System.out.println("PROFORM_PARENT " + update);
							update = FirmTable
									.updateFirmNameInTable(PROFORM_CHILD,
											OLD_FIRM_NAME, NEW_FIRM_NAME);
							// System.out.println("PROFORM_CHILD " + update);
							update = FirmTable.updateFirmNameInTable(
									ACQUITTANCE_PARENT, OLD_FIRM_NAME,
									NEW_FIRM_NAME);
							// System.out.println("ACQUITTANCE_PARENT " +
							// update);
							update = FirmTable.updateFirmNameInTable(
									ACQUITTANCE_CHILD, OLD_FIRM_NAME,
									NEW_FIRM_NAME);
							// System.out.println("ACQUITTANCE_CHILD " +
							// update);
						} finally {
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									jd.setCursor(new Cursor(
											Cursor.DEFAULT_CURSOR));

									if (edit > 0) {
										doneText.setText(" Данните са редактирани успешно!");
										done.setAutoSizedIcon(done,
												new LoadIcon().setIcons(acceptImage));
										// done.setIcon(setIcons("accept2.png"));
										clear();
									}
								}

							});
						}
					}
				};
				thread.start();
			}

		});

		incorrectClientCheckBox = new JCheckBox();
		incorrectClientCheckBox.setText("Некоректен");
		Font newCheckBoxFont = new Font(incorrectClientCheckBox.getFont()
				.getName(), Font.ITALIC + Font.BOLD, incorrectClientCheckBox
				.getFont().getSize());
		incorrectClientCheckBox.setFont(newCheckBoxFont);

		registrationVatCheckbox = new JCheckBox();
		registrationVatCheckbox.setText("Регистрация по ДДС");
		registrationVatCheckbox.setFont(newCheckBoxFont);

		components.add(firm);
		components.add(city);
		components.add(address);
		components.add(eik);
		components.add(mol);
		components.add(email);
		components.add(person);
		components.add(telPerson);
		components.add(bank);
		components.add(BIC);
		components.add(IBAN);
		components.add(discountField);
		components.add(incorrectClientCheckBox);
		components.add(registrationVatCheckbox);

		bottom.add(incorrectClientCheckBox);
		bottom.add(registrationVatCheckbox);
		bottom.add(button);
		bottom.add(done);
		bottom.add(doneText);

		block.add(bottom);

		TitledBorder title = BorderFactory.createTitledBorder("  Фирми  ");
		block.setBorder(title);
		add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private boolean isValidText() {
		if (firm.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведена фирма !");
			return false;
		} else if (city.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен град !");
			return false;
		} else if (address.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен адрес !");
			return false;
		} else if (eik.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен ЕИК !");
			return false;
		} else if (mol.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен МОЛ !");
			return false;
		} else if (person.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Не е въведено лице за контакт !");
			return false;
		} else if (telPerson.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Не е въведен телефон на лице за контакт !");
			return false;
		} else if (!EditClientPanel.isOnlyDigits(telPerson.getText())) {
			JOptionPane.showMessageDialog(null,
					"Грешно въведен формат на телефон !");
			return false;
		}

		return true;
	}

	private void clear() {
		firm.setText("");
		city.setText("");
		address.setText("");
		eik.setText("");
		mol.setText("");
		email.setText("");
		person.setText("");
		telPerson.setText("");
		bank.setText("");
		BIC.setText("");
		IBAN.setText("");
		discountField.setText("");
		EditClientPanel.clientCombo2.setSelectedItem("");
		incorrectClientCheckBox.setSelected(false);
		registrationVatCheckbox.setSelected(false);
	}

}
