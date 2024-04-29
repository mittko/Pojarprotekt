package clients;

import document.TextFieldLimit;
import db.Client.ClientTable;
import net.GetCurrentIP;
import run.JustFrame;
import utils.BevelLabel;
import utils.EditableField;
import utils.LoadIcon;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddPerson extends MainPanel {
	JTextField name = null;
	JTextField tel = null;
	JTextField discountField = null;
	JCheckBox incorrectClientCheckBox;
	JPanel block = null;
	JButton button = null;
	int insertion = 0;
	int size = 45;

	public AddPerson() {

		block = new JPanel();
		block.setOpaque(false);
		block.setLayout(new GridLayout(4, 1, 10, 10));

		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		name = new EditableField(" Име на Лицето", size, true);
		name.setPreferredSize(new Dimension(50, getFontSize() + 15));

		namePanel.add(name);

		JPanel telPanel = new JPanel();
		telPanel.setOpaque(false);
		telPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		tel = new EditableField(" Телефон за контакт", size - 20, true);
		tel.setPreferredSize(new Dimension(50, getFontSize() + 15));

		telPanel.add(tel);

		JPanel discountPanel = new JPanel();
		discountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		discountField = new EditableField(" Отстъпка", 5, false);
		// discountField.setPreferredSize(new Dimension(50, getFontSize() +
		// 15));
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
		button.setText("Запиши");

		final JLabel doneText = new JLabel();

		final BevelLabel done = new BevelLabel((int) (bottom.getPreferredSize()
				.getHeight() * 1.0));
		/*
		 * done.setPreferredSize(new Dimension(
		 * (int)(bottom.getPreferredSize().getWidth() * 0.08),
		 * (int)(bottom.getPreferredSize().getHeight() * 1.0) ));;
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
						.getWindowAncestor(AddPerson.this)));
				jDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							String incorrect = incorrectClientCheckBox
									.isSelected() ? "да" : "не";

							insertion = ClientTable.insertIntoPersonTable(
									GetCurrentIP.DB_PATH,
									name.getText(), tel.getText(),
									discountField.getText(), incorrect);


						} finally {

							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									jDialog.setCursor(new Cursor(
											Cursor.DEFAULT_CURSOR));
									name.setText("");
									tel.setText("");
									discountField.setText("");
									if (insertion > 0) {
										doneText.setText(" Данните са записани успешно!");
										done.setAutoSizedIcon(done,
												new LoadIcon().setIcons(acceptImage));
										// done.setIcon(setIcons("accept2.png"));
									}
								}

							});
						}
					}
				};
				thread.start();
			}

		});
		TitledBorder title = BorderFactory.createTitledBorder(" Частни лица ");
		block.setBorder(title);
		add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JustFrame f = new JustFrame(new AddPerson());
		f.pack();
	}

	private boolean isValidText() {
		if (name.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведено име !");
			return false;
		} else if (tel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Не е въведен телефон !");
			return false;
		}
		if (!tel.getText().isEmpty() && !NewClient.isOnlyDigits(tel.getText())) {
			JOptionPane.showMessageDialog(null, "Грешен формат на телефон !");
			return false;
		}
		return true;
	}
}
