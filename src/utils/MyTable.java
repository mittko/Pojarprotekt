package utils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTable extends JTable  {

    public MyTable(final DefaultTableModel tableModel){
        super(tableModel);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                onTableChanged(e);
            }
        });

        JMenuItem jMenuItem = new JMenuItem("Изтрий ред");
        jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTable thisTable = MyTable.this;
                int selectedRow = thisTable.getSelectedRow();
                if(selectedRow != -1) {
                    removeAt(thisTable.getSelectedRow());
                } else {
                    JOptionPane.showMessageDialog(null,"Не е избран ред за изтриване !");
                }
            }
        });
        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(jMenuItem);
        this.setComponentPopupMenu(jPopupMenu);

    }

    public void removeAt(int row) {}

    public void onTableChanged(TableModelEvent tableModelEvent){}

}
