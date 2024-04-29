package reports;

import reports.renderers.InvoiceTableRenderer;
import reports.workers.PrintReportsForInvoiceDocumentsType;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CreditNoteReportTable extends MainPanel {

    private final DefaultTableModel defaultTableModel;
    private int SELECTED_INDEX = -1;
    public CreditNoteReportTable(ArrayList<ArrayList<Object>> data) {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton generateCreditNotePdfButton = new JButton("Генерирай PDF");
        generateCreditNotePdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SELECTED_INDEX == -1) {
                    return;
                }
                JDialog jd = (JDialog) SwingUtilities
                        .getWindowAncestor(CreditNoteReportTable.this);
                jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                //	PrintService ps = ChoisePrinterDialog.showPrinters();
                //	if (ps == null) {
                //		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                //		return;
                //	}
                //	if (ps != null) {

                Integer[] selectedIndexes = getSelectedIndexes(
                        SELECTED_INDEX, 0);
                PrintReportsForInvoiceDocumentsType invoiceAndProformWorker = new PrintReportsForInvoiceDocumentsType(
                        null,
                        defaultTableModel.getValueAt(selectedIndexes[0],16).toString(),
                        defaultTableModel , jd, selectedIndexes[0],
                        selectedIndexes.length, "Кредитно известие", CREDIT_NOTE_PDF_PATH,
                        defaultTableModel.getValueAt(selectedIndexes[0],17).toString());//
                invoiceAndProformWorker.execute();
            }
        });
        northPanel.add(generateCreditNotePdfButton);
        defaultTableModel = new DefaultTableModel(
        new String[] {
                "\u2116 на Фактура", "Начин на плащане", "Отстъпка",
                "Стойност", "Клиент", "Оператор", "Дата", "\u2116 на Протокол",
                "",
                "Артикул", "Мерна ед.",
                "Количество", "Ед. Цена", "Сума", "Контрагент",
                "Фактура по доставка", "\u2116 на Кредитно Известие","Дата на Кредитно Известие" }, 0){
            @Override
            public boolean isCellEditable ( int row, int column){
                return false;
            }
        };


        for (ArrayList<Object> o : data) {
            o.add(8,"");
            defaultTableModel.addRow(o.toArray());
        }
        final JTable table = new JTable(defaultTableModel);
        table.setDefaultRenderer(Object.class, new InvoiceTableRenderer());
       table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               super.mouseClicked(e);
                   SELECTED_INDEX = table.getSelectedRow();
                   table.repaint();

           }
       });
       JScrollPane scrollPane = new JScrollPane(
               table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
               JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
       );
       resizeColumnWidth(table);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       table.setRowHeight(getFontSize()+15);
       scrollPane.setPreferredSize(new Dimension(WIDTH-50,HEIGHT-70));

       BorderLayout borderLayout = new BorderLayout();

       JPanel centerPanel = new JPanel();
       centerPanel.add(scrollPane);

       this.setLayout(borderLayout);
       this.add(northPanel,BorderLayout.NORTH);
       this.add(centerPanel,BorderLayout.CENTER);


    }
    private Integer[] getSelectedIndexes(int index, int targetColumn) {
        ArrayList<Integer> listIndexes = new ArrayList<Integer>();
        // get same elements above curr element
        for (int i = index - 1; i >= 0; i--) {
            if (defaultTableModel.getValueAt(index, targetColumn).equals(
                    defaultTableModel.getValueAt(i, targetColumn))) {
                listIndexes.add(0, i);
            } else {
                break;
            }
        }

        // add curr element into list
        listIndexes.add(index);

        for (int i = index + 1; i < defaultTableModel.getRowCount(); i++) {
            if (defaultTableModel.getValueAt(index, targetColumn).equals(
                    defaultTableModel.getValueAt(i, targetColumn))) {
                listIndexes.add(i);
            } else {
                break;
            }
        }

        return listIndexes.toArray(new Integer[listIndexes.size()]);
    }
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50;// 200; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
            columnModel.getColumn(column).setPreferredWidth(
                    width + getFontSize());
        }

    }
}
