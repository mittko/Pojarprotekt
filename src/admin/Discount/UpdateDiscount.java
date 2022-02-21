package admin.Discount;

import Document.TextFieldLimit;
import admin.Discount.Workers.changeDiscountOfWorker;
import admin.Discount.Workers.seeDiscountOfClientWorker;
import run.JustFrame;
import utility.ClientsListComboBox2;
import utility.MainPanel;
import utility.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UpdateDiscount extends MainPanel {

	ClientsListComboBox2 myCombo2 = null;// new ClientsListComboBox2();

	public static JLabel viewLabel = null;
	JTextField viewField = null;
	TooltipButton viewButton = null;

	public static JTextField changeField = null;
	JLabel percentLabel = null;
	TooltipButton changeButton = null;

	public UpdateDiscount() {
		this.setLayout(new BorderLayout());

		myCombo2 = new ClientsListComboBox2();
		myCombo2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				viewLabel.setText("");
			}

		});
		viewLabel = new JLabel();
		viewLabel.setPreferredSize(new Dimension(78, 30));
		viewLabel.setForeground(Color.red);
		viewLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		viewLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		viewButton = new TooltipButton("���");

		viewButton.setPreferredSize(new Dimension(90, 35));
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (myCombo2.getSelectedItem().equals("")
						|| !viewLabel.getText().isEmpty()) {
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(UpdateDiscount.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				seeDiscountOfClientWorker vw = new seeDiscountOfClientWorker(
						jd, myCombo2.getSelectedItem().toString());
				vw.execute();
			}

		});

		changeField = new JTextField(3);
		changeField.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		changeField.setForeground(Color.red);
		changeField.setPreferredSize(new Dimension(30, 30));
		changeField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		changeField.setDocument(new TextFieldLimit(4));

		changeButton = new TooltipButton("�������");

		changeButton.setPreferredSize(new Dimension(110, 35));
		changeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (myCombo2.getSelectedItem().equals("")
						|| changeField.getText().isEmpty()) {
					return;
				}
				// second check for new discount
				Double d = 0.0;
				try {
					d = Double.parseDouble(changeField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "��������� �����!");
					return;
				}
				if (d > 99.9 || d < 0) {
					JOptionPane.showMessageDialog(null, "��������� �����!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"������� �� �� ��������� ���������� �����?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
						"default");
				if (yes_no == 0) {
					JDialog jd = (JDialog) SwingUtilities
							.getWindowAncestor(UpdateDiscount.this);
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					changeDiscountOfWorker cw = new changeDiscountOfWorker(jd,
							myCombo2.getSelectedItem().toString(), changeField
									.getText());
					cw.execute();
				}
			}

		});

		percentLabel = new JLabel("%");
		percentLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		percentLabel.setForeground(Color.red);
		percentLabel.setPreferredSize(new Dimension(30, 30));

		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));

		centerPanel.add(myCombo2);
		centerPanel.add(viewLabel);
		centerPanel.add(viewButton);
		centerPanel.add(changeField);
		centerPanel.add(percentLabel);
		centerPanel.add(changeButton);

		JPanel helpPanel1 = new JPanel();
		helpPanel1.setOpaque(false);
		helpPanel1.setPreferredSize(new Dimension((int) centerPanel
				.getPreferredSize().getWidth(), 100));

		JPanel basePanel = new JPanel();// GradientPanel();

		basePanel.add(helpPanel1, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		basePanel.setPreferredSize(new Dimension((int) centerPanel
				.getPreferredSize().getWidth(), 300));

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final UpdateDiscount ud = new UpdateDiscount();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JustFrame f = new JustFrame(ud);
				f.pack();
			}

		});
	}
}
