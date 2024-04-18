package invoice.Sklad;

import admin.�rtikul.Workers.BiggestPriceForInvoiceWorker;
import db.�rtikul.Artikuli_DB;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.util.ArrayList;

public class SkladArtiklulPanel extends SkladArtikulFrame implements ILoadArtikuls{
    private final DefaultTableModel invoiceTableModel;
    private final double clientDiscountInPercentage;
    private final boolean isDiscountSelected;
    public SkladArtiklulPanel(DefaultTableModel invoiceTableModel, double clientDiscount, boolean isDiscountSelect) {
        super();
        this.invoiceTableModel = invoiceTableModel;
        this.clientDiscountInPercentage = clientDiscount;
        this.isDiscountSelected = isDiscountSelect;
    }

    @Override
    public void addArtikuls(JTable skladTable) {
        for (int row = 0; row < skladTable.getRowCount(); row++) {
            if ((Boolean) skladTable.getValueAt(row, 5)) {

                TableCellEditor cellEditor = skladTable.getColumnModel()
                        .getColumn(4).getCellEditor();
                cellEditor.stopCellEditing();

                String artikul = skladTable.getValueAt(row, 0).toString(); // artikul

                String skladQuantity = skladTable.getValueAt(row, 1).toString();


                String med = skladTable.getValueAt( row, 2).toString(); // ����� �������


                // String value = "";
                // try {
                // value = skladTable.getValueAt(row, 3).toString();
                // } catch (Exception ex) {
                // JOptionPane.showMessageDialog(null, ex.toString());
                // return;
                // }
                String quantity = skladTable.getValueAt(row, 4).toString();

                double val = new BiggestPriceForInvoiceWorker(artikul)
                        .doInBackground();
                if (val == 0) {
                    // ��� �� ������ ������ ��� ������� �� ������ �� ���� �����
                    // �� ����� �������� �� ���������� � ���������
                    val = Double.parseDouble(skladTable.getValueAt(row, 3)
                            .toString());
                }

                double discountInSum = getDiscount(val,
                        clientDiscountInPercentage);
                if (isDiscountSelected) {
                    val = val - discountInSum;
                }

                double userQ = 0;

                try {
                    userQ = Double.parseDouble(quantity);
                    double availableQ = Double.parseDouble(skladQuantity);

                    /*
                     * if (availableQ <= 0) {
                     * JOptionPane.showMessageDialog(null,
                     * "��������� �� � �������"); return; }
                     */
                    if (userQ <= 0) {
                        JOptionPane
                                .showMessageDialog(null,
                                        "�� ���� �� ��������� "
                                                + "����������� \n ��������� "
                                                + "��� 0 !!! (  " + (row + 1)
                                                + "��� )");
                        return;
                    }

                    if (userQ > availableQ) {
                        JOptionPane.showMessageDialog(null,
                                "����� �������� ���������� � ������! ( " + (row + 1) +
                                        " ���)"); return; }

                } catch (Exception ex) {
                    JOptionPane
                            .showMessageDialog(null,
                                    "�� � �������� ����������! (" + (row + 1)
                                            + " ���)");
                    return;
                }

                String allSum = "";
                try {
                    allSum = MyMath.round(
                            Double.parseDouble(quantity) * MyMath.round(val, 2),
                            2) + "";
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"" + ex.getMessage());
                    return;
                }

                // check for dublicate artikuls
                String kontragent = skladTable.getValueAt(row, 6).toString();
                String invoiceByKontragent = skladTable.getValueAt(row, 7)
                        .toString();

                for (int i = 0; i < invoiceTableModel.getRowCount(); i++) {
                    if (invoiceTableModel.getValueAt(i, 0).toString()
                            .equals(artikul) && invoiceTableModel.getValueAt(i,6).toString().equals(kontragent)
                            && invoiceTableModel.getValueAt(i, 7).toString().equals(invoiceByKontragent)) {
                        JOptionPane.showMessageDialog(null,
                                "���� ������� ���� � �������! > " + artikul + " " + kontragent + " " + invoiceByKontragent);
                        return;
                    }
                }


//				invoiceTableModel.addRow(new Object[] { artikul,
//						userQ < 2 ? "����" : "����", quantity,
//						MyMath.round(val, 2), allSum,
//						MyMath.round(discountInSum, 2), kontragent,
//						invoiceByKontragent });  OLD LOGIC THAT WORK !!!

                invoiceTableModel.addRow(new Object[] { artikul,
                        /*userQ < 2 ? "����" : "����"*/med, quantity,
                        MyMath.round(val, 2), allSum,
                        clientDiscountInPercentage, kontragent,
                        invoiceByKontragent });
                // clear selected values
                skladTable.setValueAt("", row, 4);
                skladTable.setValueAt(false, row, 5);

            }
        }
        goodbyeCruelWorld();
    }

    public ArrayList<Object[]> getArtikuls() {
        return Artikuli_DB.getAvailableArtikuls(AVAILABLE_ARTIKULS);
    }

    private void goodbyeCruelWorld() {
        JDialog jd = (JDialog) SwingUtilities
                .getWindowAncestor(SkladArtiklulPanel.this);
        jd.dispose();
    }


}
