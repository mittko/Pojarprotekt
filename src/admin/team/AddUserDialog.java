package admin.team;

import admin.team.workers.AddUserWorker;
import http.RequestCallback2;
import http.user.GetUserService;
import models.User;
import utils.CustomCheckBox;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserDialog extends MainPanel {

	TooltipButton addUserButton;

	JPanel gridPanel;

	JLabel userLabel;
	JLabel passwordLabel;
	JLabel serviceOrderLabel;
	JLabel workingBookLabel;
	JLabel invoiceLabel;
	JLabel reportsLabel;
	JLabel newExtlabel;
	JLabel hiddenMenuLabel;
	JLabel acquittancelabel;

	JTextField userField;
	JTextField passwordField;
	CustomCheckBox serviceOrderCheckBox;
	CustomCheckBox workingBookCheckBox;
	CustomCheckBox invoiceCheckBox;
	CustomCheckBox reportsCheckBox;
	CustomCheckBox newExtCheckBox;
	CustomCheckBox hiddenMenuCheckBox;
	CustomCheckBox acquittanceCheckBox;

	private final Dimension checkBoxDimension = new Dimension(
			MainPanel.getFontSize() * 2, MainPanel.getFontSize() * 2);

	public AddUserDialog() {

		gridPanel = new JPanel();
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gridPanel.setLayout(new GridBagLayout());

		userLabel = new JLabel("Потребител");
		GridBagConstraints gbc00 = new GridBagConstraints();
		gbc00.gridx = 0;
		gbc00.gridy = 0;
		gbc00.ipady = 20;

		passwordLabel = new JLabel("Парола");
		GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.gridx = 0;
		gbc01.gridy = 1;

		serviceOrderLabel = new JLabel("Сервизна Поръчка");
		GridBagConstraints gbc02 = new GridBagConstraints();
		gbc02.gridx = 0;
		gbc02.gridy = 2;

		workingBookLabel = new JLabel("Работна Книга");
		GridBagConstraints gbc03 = new GridBagConstraints();
		gbc03.gridx = 0;
		gbc03.gridy = 3;

		invoiceLabel = new JLabel("Фактури");
		GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.gridx = 0;
		gbc04.gridy = 4;

		reportsLabel = new JLabel("Справки");
		GridBagConstraints gbc05 = new GridBagConstraints();
		gbc05.gridx = 0;
		gbc05.gridy = 5;

		newExtlabel = new JLabel("Нови пожарогасители");
		GridBagConstraints gbc06 = new GridBagConstraints();
		gbc06.gridx = 0;
		gbc06.gridy = 6;

		hiddenMenuLabel = new JLabel("Скрито Меню");
		GridBagConstraints gbc07 = new GridBagConstraints();
		gbc07.gridx = 0;
		gbc07.gridy = 7;

		acquittancelabel = new JLabel("Стокова разписка");
		GridBagConstraints gbc08 = new GridBagConstraints();
		gbc08.gridx = 0;
		gbc08.gridy = 8;

		userField = new JTextField(20);
		GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.gridx = 1;
		gbc10.gridy = 0;

		passwordField = new JTextField(20);
		GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.gridx = 1;
		gbc11.gridy = 1;// ???

		serviceOrderCheckBox = new CustomCheckBox();
		serviceOrderCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc12 = new GridBagConstraints();
		gbc12.gridx = 1;
		gbc12.gridy = 2;

		workingBookCheckBox = new CustomCheckBox();
		workingBookCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc13 = new GridBagConstraints();
		gbc13.gridx = 1;
		gbc13.gridy = 3;

		invoiceCheckBox = new CustomCheckBox();
		invoiceCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc14 = new GridBagConstraints();
		gbc14.gridx = 1;
		gbc14.gridy = 4;

		reportsCheckBox = new CustomCheckBox();
		reportsCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc15 = new GridBagConstraints();
		gbc15.gridx = 1;
		gbc15.gridy = 5;

		newExtCheckBox = new CustomCheckBox();
		newExtCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc16 = new GridBagConstraints();
		gbc16.gridx = 1;
		gbc16.gridy = 6;

		hiddenMenuCheckBox = new CustomCheckBox();
		hiddenMenuCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc17 = new GridBagConstraints();
		gbc17.gridx = 1;
		gbc17.gridy = 7;

		acquittanceCheckBox = new CustomCheckBox();
		acquittanceCheckBox.setPreferredSize(checkBoxDimension);
		GridBagConstraints gbc18 = new GridBagConstraints();
		gbc18.gridx = 1;
		gbc18.gridy = 8;

		GridBagConstraints gbc19 = new GridBagConstraints();
		gbc19.gridx = 1;
		gbc19.gridy = 9;
		addUserButton = new TooltipButton("Запиши потребител");
		addUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (userField.getText().isEmpty()
						|| passwordField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Не е въведено пълно име на потребител!");
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(AddUserDialog.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));


				User user = new User();
				user.setUsser(userField.getText());
				user.setPassword(passwordField.getText());
				user.setService_Order(serviceOrderCheckBox.getPressed() ? "yes"
						: "no");
				user.setWorking_Book(workingBookCheckBox.getPressed() ? "yes" : "no");
				user.setInvoice(invoiceCheckBox.getPressed() ? "yes" : "no");
				user.setReports(reportsCheckBox.getPressed() ? "yes" : "no");
				user.setNew_Ext(newExtCheckBox.getPressed() ? "yes" : "no");
				user.setHidden_Menu(hiddenMenuCheckBox.getPressed() ? "yes" : "no");
				user.setAcquittance(acquittanceCheckBox.getPressed() ? "yes" : "no");

				GetUserService service = new GetUserService();
				service.createUser(user, new RequestCallback2() {
					@Override
					public <T> void callback(T t) {

						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						Integer result = (Integer) t;
						if(result > 0) {
							JOptionPane.showMessageDialog(null, "Данните са записани успешно!");
						}
					}
				});

//				AddUserWorker add = new AddUserWorker(
//						userField.getText(),
//						passwordField.getText(),
//						serviceOrderCheckBox.getPressed() ? "yes"
//								: "no",
//						workingBookCheckBox.getPressed() ? "yes" : "no",
//						invoiceCheckBox.getPressed() ? "yes" : "no",
//						reportsCheckBox.getPressed() ? "yes" : "no",
//						newExtCheckBox.getPressed() ? "yes" : "no",
//						hiddenMenuCheckBox.getPressed() ? "yes" : "no",
//						acquittanceCheckBox.getPressed() ? "yes" : "no",
//						jd);
//
//				add.execute();
			}

		});

		gridPanel.add(userLabel, gbc00);
		gridPanel.add(passwordLabel, gbc01);
		gridPanel.add(serviceOrderLabel, gbc02);
		gridPanel.add(workingBookLabel, gbc03);
		gridPanel.add(invoiceLabel, gbc04);
		gridPanel.add(reportsLabel, gbc05);
		gridPanel.add(newExtlabel, gbc06);
		gridPanel.add(hiddenMenuLabel, gbc07);
		gridPanel.add(acquittancelabel, gbc08);

		gridPanel.add(userField, gbc10);
		gridPanel.add(passwordField, gbc11);
		gridPanel.add(serviceOrderCheckBox, gbc12);
		gridPanel.add(workingBookCheckBox, gbc13);
		gridPanel.add(invoiceCheckBox, gbc14);
		gridPanel.add(reportsCheckBox, gbc15);
		gridPanel.add(newExtCheckBox, gbc16);
		gridPanel.add(hiddenMenuCheckBox, gbc17);
		gridPanel.add(acquittanceCheckBox, gbc18);
		gridPanel.add(addUserButton, gbc19);

		this.add(gridPanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
