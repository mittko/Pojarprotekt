package NewClient;

import Document.TextFieldLimit;
import db.Client.FirmTable;
import run.JustFrame;
import utility.BevelLabel;
import utility.EditableField;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddFirm extends MainPanel {
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
	JCheckBox incorrectClientCheckBox;

	JCheckBox registeredVatCheckBox;
	int insertion = 0;
	private int size = 45;

	public AddFirm() {
		Dimension dimension = new Dimension(50, getFontSize() + 15);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 5, 0);

		block = new JPanel();
		block.setOpaque(false);

		block.setLayout(new GridLayout(13, 0, 10, 0));

		JPanel firmPanel = new JPanel();
		firmPanel.setOpaque(false);

		firmPanel.setLayout(layout);

		firm = new EditableField(" ��� �� �����", size - 10, true);
		firm.setPreferredSize(dimension);

		firmPanel.add(firm);

		JPanel cityPanel = new JPanel();
		cityPanel.setOpaque(false);
		cityPanel.setLayout(layout);

		city = new EditableField(" ����", size - 20, true);
		city.setPreferredSize(dimension);

		cityPanel.add(city);

		JPanel addressPanel = new JPanel();
		addressPanel.setOpaque(false);

		addressPanel.setLayout(layout);

		address = new EditableField(" �����", size, true);
		address.setPreferredSize(dimension);

		addressPanel.add(address);

		JPanel eikPanel = new JPanel();
		eikPanel.setOpaque(false);
		eikPanel.setLayout(layout);

		eik = new EditableField(" ���", size - 20, true);
		eik.setPreferredSize(dimension);

		eikPanel.add(eik);

		JPanel molPanel = new JPanel();
		molPanel.setOpaque(false);
		molPanel.setLayout(layout);

		mol = new EditableField(" ���", size - 20, true);
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

		person = new EditableField(" ���� �� �������", size, true);
		person.setPreferredSize(dimension);

		personPanel.add(person);

		JPanel telPersonPanel = new JPanel();
		telPersonPanel.setOpaque(false);
		telPersonPanel.setLayout(layout);

		telPerson = new EditableField(" ������� �� �������", size - 20, true);
		telPerson.setPreferredSize(dimension);

		telPersonPanel.add(telPerson);

		JPanel bankPanel = new JPanel();
		bankPanel.setOpaque(false);
		bankPanel.setLayout(layout);

		bank = new EditableField(" �����", size - 20, false);
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

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(this.WIDTH / 2,
				(int) (this.HEIGHT * 0.05)));
		bottom.setOpaque(false);
		bottom.setLayout(layout);

		final JLabel doneText = new JLabel();

		final BevelLabel done = new BevelLabel((int) (bottom.getPreferredSize()
				.getHeight()));
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

		JPanel discountPanel = new JPanel();
		discountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		discountField = new EditableField(" ��������", 5, false);
		discountField.setForeground(Color.red);
		discountField.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
				getFontSize() + 10));
		discountField.setPreferredSize(dimension);
		discountField.setDocument(new TextFieldLimit(2));

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

		JButton button = new JButton();
		button.setText("������");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!isValidText()) {
					return;
				}

				final JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(AddFirm.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							String incorrect = incorrectClientCheckBox
									.isSelected() ? "��" : "��";

							String isVatRegistered = registeredVatCheckBox.isSelected()
									? "��" : "��";

							insertion = FirmTable.insertIntoFirmTable(
									firm.getText(), city.getText(),
									address.getText(), eik.getText(),
									mol.getText(), email.getText(),
									person.getText(), telPerson.getText(),
									bank.getText(), BIC.getText(),
									IBAN.getText(), discountField.getText(),
									incorrect, isVatRegistered);
						} finally {
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									jd.setCursor(new Cursor(
											Cursor.DEFAULT_CURSOR));

									if (insertion > 0) {
										doneText.setText(" ������� �� �������� �������!");
										// done.setIcon(setIcons("accept2.png"));
										done.setAutoSizedIcon(done,
												new LoadIcon().setIcons(acceptImage));
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
		incorrectClientCheckBox.setText("����������");
		Font newCheckBoxFont = new Font(incorrectClientCheckBox.getFont()
				.getName(), Font.ITALIC + Font.BOLD, incorrectClientCheckBox
				.getFont().getSize());
		incorrectClientCheckBox.setFont(newCheckBoxFont);

		registeredVatCheckBox = new JCheckBox();
		registeredVatCheckBox.setText("����������� �� ���");
		registeredVatCheckBox.setFont(newCheckBoxFont);

		bottom.add(incorrectClientCheckBox);
		bottom.add(registeredVatCheckBox);
		bottom.add(button);
		bottom.add(done);
		bottom.add(doneText);

		block.add(bottom);

		TitledBorder title = BorderFactory.createTitledBorder("  �����  ");
		block.setBorder(title);
		add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JustFrame f = new JustFrame(new AddFirm());
		f.pack();
	}

	private boolean isValidText() {
		if (firm.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�� � �������� ����� !");
			return false;
		} else if (city.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�� � ������� ���� !");
			return false;
		} else if (address.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�� � ������� ����� !");
			return false;
		} else if (eik.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�� � ������� ��� !");
			return false;
		} else if (mol.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�� � ������� ��� !");
			return false;
		} else if (person.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"�� � �������� ���� �� ������� !");
			return false;
		} else if (telPerson.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"�� � ������� ������� �� ���� �� ������� !");
			return false;
		} else if (!NewClient.isOnlyDigits(telPerson.getText())) {
			JOptionPane.showMessageDialog(null,
					"������ ������� ������ �� ������� !");
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

	}
}
