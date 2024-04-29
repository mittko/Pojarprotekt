package admin.аrtikul;

import utils.EditableField;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeQuantityArtikulDialog extends MainPanel {

	private final EditableField clientField;
	private final EditableField invoiceField;
	private final EditableField artikulsField;
	private final EditableField quantityField;
	JButton saveInDBButton = new JButton("Запази");

	public static void main(String[] args) {
		// AddArtikulDialog dialog =
		// new
		// AddArtikulDialog("artikul","nalicni","cena","faktura","kontragent","operator");
		// JustFrame start = new JustFrame(dialog);
		// start.pack();
		// start.setResizable(false);
		// start.setFrameLocationOnTheCenter();
	}

	private final String skladItem;
	private final String medItem;
	private final String oldValueItem;

	public ChangeQuantityArtikulDialog(final IEditArtikuls iEditArtikuls,final String artikulItem,
			String skladitem, final String medItem, final String oldValueItem,
			final String invoiceNumber, final String client) {
		this.skladItem = skladitem;
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
		JLabel quantityLabel = new JLabel("Количество");

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridBagLayout());

		artikulsField = new EditableField("", 40);
		artikulsField.setEditable(false);
		artikulsField.setText(artikulItem);

		clientField = new EditableField("", 40);
		clientField.setEditable(false);
		clientField.setText(client);

		invoiceField = new EditableField("", 10);
		invoiceField.setEditable(false);
		invoiceField.setText(invoiceNumber);

		quantityField = new EditableField("", 5);

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

		GridBagConstraints gbc04 = new GridBagConstraints();
		gbc04.fill = GridBagConstraints.HORIZONTAL;
		gbc04.gridx = 0;
		gbc04.gridy = 4;
		gbc04.insets = new Insets(5, 0, 0, 0);


		saveInDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// System.out.printf("%s %s %s %s %s %s %s %s %s %s\n",
				// clientComboBox.getSelectedItem().toString(),
				// skladField.getText(),
				// medField.getText(),
				// oldValueField.getText(),
				// currValueField.getText(),
				// invoiceField.getText(),
				// artikulsComboBox.getSelectedItem().toString(),
				// dateField.getText(),
				// personField.getText() ,
				// percentProfitField.getText());
				String artikul = null;
				String newQuantity = null;
				String kontragent = null;
				String invoiceByKontragent = null;

				artikul = artikulsField.getText();
				newQuantity = quantityField.getText();
				kontragent = clientField.getText();
				invoiceByKontragent = invoiceField.getText();

				if (artikul.isEmpty() || newQuantity.isEmpty()
						|| kontragent.isEmpty()
						|| invoiceByKontragent.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Има незаписани данни !");
					return;
				}
				try {
					Double parseString = Double.parseDouble(newQuantity);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Грешен формат на количество");
					return;
				}
				iEditArtikuls.changeArtikulQuantity(artikul,newQuantity,kontragent,invoiceByKontragent);

			}

			// }

		});

		rightPanel.add(clientLabel, gbc);
		rightPanel.add(clientField, gbc10);
		rightPanel.add(artikulLabel, gbc01);
		rightPanel.add(artikulsField, gbc11);

		rightPanel.add(invoiceLabel, gbc02);
		rightPanel.add(invoiceField, gbc12);

		rightPanel.add(quantityLabel, gbc03);
		rightPanel.add(quantityField, gbc13);
		rightPanel.add(saveInDBButton, gbc04);

		basePanel2.add(rightPanel);

		this.add(basePanel2);
	}


}
