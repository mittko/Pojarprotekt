package admin.extinguishingagent;

import admin.extinguishingagent.workers.EditExtinguishingAgentWorker;
import admin.extinguishingagent.workers.LoadExtinguishingTableValues;
import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExtinguishingAgentDialog extends MainPanel {


    public ExtinguishingAgentDialog() {
        String[] titles = new String[]{"Вид","Марка","Партида " + MainPanel.numeroSign,"Годен до"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(new String[4][4],titles) {
            @Override
            public boolean isCellEditable(int row, int column) { // custom isCellEditable function
                return column != 0;
            }
        };
        final JTable table = new JTable(defaultTableModel);
        table.setRowHeight(25);
//        table.setValueAt("Прах",0,0);
//        table.setValueAt("Вода",1,0);
//        table.setValueAt("Вода + Пяна",2,0);
//        table.setValueAt("CO2",3,0);
        JScrollPane jScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setPreferredSize(new Dimension(600,
                (int)(50 + table.getPreferredSize().getHeight() + table.getTableHeader().getPreferredSize().getHeight())));
        JButton jButton = new JButton("Запази");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = ((JDialog) (SwingUtilities
                        .getWindowAncestor(ExtinguishingAgentDialog.this)));
                jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                for (int selectedRow = 0; selectedRow < table.getColumnCount(); selectedRow++) {
                    EditExtinguishingAgentWorker editExtinguishingAgentWorker = new EditExtinguishingAgentWorker(
                            table.getValueAt(selectedRow, 0).toString(),
                            table.getValueAt(selectedRow, 1).toString(),
                            table.getValueAt(selectedRow, 2).toString(),
                            table.getValueAt(selectedRow, 3).toString(), jd);
                    try {
                        int edit = editExtinguishingAgentWorker.doInBackground();
                        System.out.println(edit);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        JButton jButton1 = new JButton("Зареди");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Object[]> values;
                JDialog jd = ((JDialog) (SwingUtilities
                        .getWindowAncestor(ExtinguishingAgentDialog.this)));
                jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                LoadExtinguishingTableValues l = new LoadExtinguishingTableValues(jd);
                try {
                    values = l.doInBackground();
                    for(int i = 0;i < values.size();i++) {
                        Object[] obj = values.get(i);
                        for(int j = 0;j < obj.length;j++) {
                            System.out.println(obj[j]);
                            table.setValueAt(obj[j], i, j);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        this.setLayout(new BorderLayout());
        this.add(jScrollPane,BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(jButton1);
        southPanel.add(jButton);
        this.add(southPanel,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                UIManager.put("swing.boldMetal", Boolean.FALSE);
                JustFrame start = new JustFrame(new ExtinguishingAgentDialog());
                start.pack();
                start.setResizable(false);
                start.setFrameLocationOnTheCenter();
            }
        });
    }
}
