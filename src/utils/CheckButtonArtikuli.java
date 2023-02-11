package utils;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CheckButtonArtikuli extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isAllowDiscount = false;
	private Icon allowIcon = null;
	private Icon disallowIcon = null;
	private final ArrayList<Double> DISCOUNTS = new ArrayList<Double>();
	private final DefaultTableModel dftm;
	private final JTextField sumField;
	private final JTextField sumFieldNoTax;

	public CheckButtonArtikuli(DefaultTableModel dftm, JTextField sumField,
			JTextField sumFieldNoTax) {
		this.dftm = dftm;
		this.sumField = sumField;
		this.sumFieldNoTax = sumFieldNoTax;
		allowIcon = new LoadIcon().setIcons(MainPanel.acceptImage);
		// this.setPreferredSize(new Dimension(45,45));
		disallowIcon = null;
		this.setContentAreaFilled(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent me) {
				if (!isEnabled()) {
					return;
				}
				// isAllowDiscount = !isAllowDiscount;
				setSelected(isAllowDiscount);
			}
		});

	}

	/*
	 * public boolean isAllowDiscount() { isAllowDiscount = !isAllowDiscount;
	 * return isAllowDiscount; }
	 */

	@Override
	public void setSelected(boolean flag) {
		if (!flag) {
			// setIcon(allowIcon);
			setDynamicSizedIcon(this, (ImageIcon) allowIcon);
			isAllowDiscount = true;
			calcSum(-1);
		} else {
			setIcon(disallowIcon);
			isAllowDiscount = false;
			calcSum(1);
		}
	}

	public void setUnselected() {
		this.isAllowDiscount = false;
		setIcon(disallowIcon);
	}

	@Override
	public boolean isSelected() {
		return isAllowDiscount;
	}

	private void calcSum(int addOrSubstract) {

		DISCOUNTS.clear();
		for (int row = 0; row < dftm.getRowCount(); row++) {
			double value = Double.parseDouble(dftm.getValueAt(row, 5)
					.toString());
			DISCOUNTS.add(MyMath.round(value, 2));
		}

		ArrayList<Object[]> helpList = new ArrayList<Object[]>();
		ArrayList<String> data = new ArrayList<String>();

		for (int row = 0; row < dftm.getRowCount(); row++) {
			data.clear();

			String doing = dftm.getValueAt(row, 0).toString();
			data.add(doing);

			String measure = dftm.getValueAt(row, 1).toString();
			data.add(measure);

			int quant = Integer.parseInt(dftm.getValueAt(row, 2).toString()); // quantity
			data.add(quant + "");

			double valu = Double
					.parseDouble(dftm.getValueAt(row, 3).toString())
					+ (addOrSubstract * (DISCOUNTS.get(row))); // value + (quant
																// * discount)

			valu = MyMath.round(valu, 2);

			data.add(valu + "");
			double sum = MyMath.round(quant * valu, 2);
			data.add(sum + "");

			helpList.add(data.toArray());
		}

		for (int row = 0; row < helpList.size(); row++) {
			Object[] obj = helpList.get(row);
			String value = obj[3].toString();
			String sum = obj[4].toString();
			dftm.setValueAt(value, row, 3);
			dftm.setValueAt(sum, row, 4);
			dftm.setValueAt(DISCOUNTS.get(row), row, 5); // discount
		}

		calcFinalSum();
	}

	public void calcFinalSum() {

		double valu = 0.0;

		for (int row = 0; row < dftm.getRowCount(); row++) {
			valu += Double.parseDouble(dftm.getValueAt(row, 4).toString());
		}
		sumFieldNoTax.setText(String.format("%.2f", valu).replace(",", "."));
		// set ÄÄÑ
		valu *= 1.2;
		// set final value
		String finalValue = String.format("%.2f", valu).replace(",", ".");
		sumField.setText(finalValue);
	}

	public void setDynamicSizedIcon(JButton button, ImageIcon icon) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(button.getPreferredSize().width,
				button.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
	}
}
