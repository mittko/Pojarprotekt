package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DiscountButtonArtikuli extends JButton {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean addDiscount = false;
	private Icon addIcon = null;
	private Icon substractIcon = null;
	private final ArrayList<Double> totals = new ArrayList<>();
	private final DefaultTableModel dftm;
	private final JTextField sumField;
	private final JTextField sumFieldNoTax;

	public void setTotals(ArrayList<Double> totals) {
		this.totals.clear();
		this.totals.addAll(totals);
	}
	public DiscountButtonArtikuli(final DefaultTableModel dftm, JTextField sumField,
								  JTextField sumFieldNoTax) {
		this.dftm = dftm;
		this.sumField = sumField;
		this.sumFieldNoTax = sumFieldNoTax;
		addIcon = new LoadIcon().setIcons(MainPanel.addPercentImage);
		// this.setPreferredSize(new Dimension(45,45));
		substractIcon = new LoadIcon().setIcons(MainPanel.substractPercentImage);
		this.setContentAreaFilled(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent me) {
				if (dftm.getRowCount() == 0) {
					return;
				}
				setSelected(addDiscount);
			}
		});

	}


	  public boolean isAllowDiscount() {
		addDiscount = !addDiscount;
	  return addDiscount; }


	@Override
	public void setSelected(boolean flag) {
		if (!flag) {
			// setIcon(allowIcon);
			setDynamicSizedIcon(this, (ImageIcon) substractIcon);
			addDiscount = true;
			calcSum(-1);
		} else {
			setDynamicSizedIcon(this, (ImageIcon) addIcon);
			addDiscount = false;
			calcSum(1);
		}
	}
    public void setDefaultIcon() {
		setIcon(null);
	}
	public void setUnselected() {
		this.addDiscount = false; // attention !!!
		setIcon(substractIcon);
	}

	/*@Override
	public boolean isSelected() {
		return isAllowDiscount;
	}*/

	private void calcSum(int addOrSubstract) {
		ArrayList<Object[]> helpList = new ArrayList<Object[]>();
		ArrayList<String> data = new ArrayList<String>();

		double discount = 0;
		for (int row = 0; row < dftm.getRowCount(); row++) {
			data.clear();

			String doing = dftm.getValueAt(row, 0).toString();
			data.add(doing);

			String measure = dftm.getValueAt(row, 1).toString();
			data.add(measure);

			int quant = Integer.parseInt(dftm.getValueAt(row, 2).toString()); // quantity
			data.add(quant + "");

			double total = totals.get(row);// Double.parseDouble(dftm.getValueAt(row,3).toString());
			try {
				discount = Double.parseDouble(dftm.getValueAt(row, 5).toString());
			} catch (NumberFormatException numberFormatException) {
				discount = 0;
			}

			double valu = 0;
			double percentValue =
					(discount / 100) * total;
			if(addOrSubstract < 0) {
				valu = total - percentValue;
			} else {
				valu = total + percentValue;

			}
		/*	old solution if(addOrSubstract < 0) {
				double percentValue =
						(discount / 100) * total;
				valu =
						total - percentValue;
			} else {
				valu = total / (1 - (discount / 100));

			}*/
			valu = MyMath.round(valu, 2);

			data.add(valu + "");
			double sum = MyMath.round(quant * valu, 2);
			data.add(sum + "");

			data.add(discount+"");
			helpList.add(data.toArray());
		}

		for (int row = 0; row < helpList.size(); row++) {
			Object[] obj = helpList.get(row);
			String value = obj[3].toString();
			String sum = obj[4].toString();
			String disc = obj[5].toString();
			dftm.setValueAt(value, row, 3);
			dftm.setValueAt(sum, row, 4);
			dftm.setValueAt(disc, row, 5); // discount
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
				button.getPreferredSize().height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
	}
}
