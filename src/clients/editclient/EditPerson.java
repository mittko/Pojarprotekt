package clients.editclient;

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

import http.RequestCallback2;
import http.client.GetClientService;
import jdk.nashorn.internal.scripts.JO;
import models.Firm;
import utils.BevelLabel;
import utils.EditableField;
import utils.LoadIcon;
import document.TextFieldLimit;
import db.Client.ClientTable;
import db.Client.FirmTable;
import utils.MainPanel;

public class EditPerson extends MainPanel {
	static JTextField name = null;
	static JTextField tel = null;
	static JTextField discountField = null;
	JPanel block = null;
	JButton button = null;
	int edit = 0;
	int size = 45;
	public static ArrayList<JComponent> components = null;
	static JCheckBox incorrectClientCheckBox;

	public EditPerson() {
		components = new ArrayList<JComponent>();
		block = new JPanel();
		block.setOpaque(false);
		block.setLayout(new GridLayout(4, 1, 10, 10));

		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		name = new EditableField(" Име на Лицето", size, false);
		// name.setEditable(false);
		name.setPreferredSize(new Dimension(50, getFontSize() + 15));

		namePanel.add(name);

		JPanel telPanel = new JPanel();
		telPanel.setOpaque(false);
		telPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		tel = new EditableField(" Телефон за контакт", size - 20, false);
		tel.setPreferredSize(new Dimension(50, getFontSize() + 15));

		telPanel.add(tel);
		JPanel discountPanel = new JPanel();
		discountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		discountField = new EditableField(" Отстъпка", 10, false);
		discountField.setPreferredSize(new Dimension(50, getFontSize() + 15));
		discountField.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
				getFontSize() + 10));
		discountField.setDocument(new TextFieldLimit(2));
		discountField.setForeground(Color.red);
		discountPanel.add(discountField);

		incorrectClientCheckBox = new JCheckBox();
		incorrectClientCheckBox.setText("Некоректен");
		Font newCheckBoxFont = new Font(incorrectClientCheckBox.getFont()
				.getName(), Font.ITALIC + Font.BOLD, incorrectClientCheckBox
				.getFont().getSize());
		incorrectClientCheckBox.setFont(newCheckBoxFont);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(this.WIDTH / 2,
				(int) (this.HEIGHT * 0.05)));
		bottom.setOpaque(false);
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		button = new JButton();
		button.setEnabled(ACCESS_MENU[4]);
		button.setText("Запиши");

		final JLabel doneText = new JLabel();

		final BevelLabel done = new BevelLabel((int) (bottom.getPreferredSize()
				.getHeight()));
		/*
		 * done.setPreferredSize(new Dimension(
		 * (int)(bottom.getPreferredSize().getWidth() * 0.08),
		 * (int)(bottom.getPreferredSize().getHeight() * 1.0) ));
		 */

		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (done.getIcon() != null) {
					done.setIcon(null);
					doneText.setText("");

				}
			}
		});

		components.add(name);
		components.add(tel);
		components.add(discountField);
		components.add(incorrectClientCheckBox);

		bottom.add(incorrectClientCheckBox);
		bottom.add(button);
		bottom.add(done);
		bottom.add(doneText);

		block.add(namePanel);
		block.add(telPanel);
		block.add(discountPanel);
		block.add(bottom);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!isValidText()) {
					return;
				}

				final JDialog jDialog = ((JDialog) (SwingUtilities
						.getWindowAncestor(EditPerson.this)));
				jDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				GetClientService service = new GetClientService();
				Firm firm = new Firm();
				String incorrect = incorrectClientCheckBox
						.isSelected() ? "да" : "не";


				firm.setFirm(name.getText());
				firm.setTelPerson(tel.getText());
				firm.setDiscount(discountField.getText());
				firm.setIncorrect_person(incorrect);

                service.editClient(firm, EditClientPanel.clientCombo2.getSelectedItem().toString(), new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						int edit = (Integer)t;

						jDialog.setCursor(new Cursor(
								Cursor.DEFAULT_CURSOR));

						if (edit > 0) {
							doneText.setText(" Данните са редактирани успешно!");
							done.setAutoSizedIcon(done,
									new LoadIcon().setIcons(acceptImage));
							name.setText("");
							tel.setText("");
							discountField.setText("");
						} else {
							JOptionPane.showMessageDialog(null,"Не намерен такъв клиент");
						}
					}
				});

			}

		});
		TitledBorder title = BorderFactory.createTitledBorder(" Частни лица ");
		block.setBorder(title);
		add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private boolean isValidText() {
		if (name.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведено име !");
			return false;
		} else if (tel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен телефон !");
			return false;
		}
		if (!tel.getText().isEmpty()
				&& !EditClientPanel.isOnlyDigits(tel.getText())) {
			JOptionPane.showMessageDialog(null, "Грешен формат на телефон !");
			return false;
		}
		return true;
	}
}
