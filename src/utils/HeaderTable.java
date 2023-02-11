package utils;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class HeaderTable {
	private JTable headerTable;
	public DefaultTableModel tableModel;
	private JTable jTable;
	public HeaderTable() {
		// TODO Auto-generated constructor stub
		
	}

	public HeaderTable(final JTable table) {
		
		
		   final DefaultTableModel model = new DefaultTableModel() {

               private static final long serialVersionUID = 1L;

               @Override
               public int getColumnCount() {
                   return 1;
               }

               @Override
               public Object getValueAt(int row, int column) {
                   return table.convertRowIndexToModel(row) + 1;// + 0
               }

               @Override
               public int getRowCount() {
                   return table.getRowCount();
               }
               @Override
               public boolean isCellEditable(int row, int col) {
            	   return false;
               }
           };
           
           this.jTable = table;
           this.tableModel = model;
	}
	
	public JTable getRowsColumnTable() {
		  final TableRowSorter<TableModel> sorter = 
				  new TableRowSorter<TableModel>(jTable.getModel());
          jTable.setRowSorter(sorter);
       
		
		 headerTable = new JTable(tableModel);
		 headerTable.setRowHeight(jTable.getRowHeight());
         headerTable.setShowGrid(false);
         headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         headerTable.setPreferredScrollableViewportSize(new Dimension(50, 0));
         headerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
         headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

             @Override
             public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                 boolean selected = jTable.getSelectionModel().isSelectedIndex(row);
                 Component component = jTable.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(jTable, value, false, false, -1, -2);
                 ((JLabel) component).setHorizontalAlignment(JLabel.CENTER);
                 if (selected) {
                     component.setFont(component.getFont().deriveFont(Font.BOLD));
                 } else {
                     component.setFont(component.getFont().deriveFont(Font.PLAIN));
                 }
                 return component;
             }
         });
         jTable.getRowSorter().addRowSorterListener(new RowSorterListener() {

             @Override
             public void sorterChanged(RowSorterEvent e) {
                 tableModel.fireTableDataChanged();
             }
         });
         jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

             @Override
             public void valueChanged(ListSelectionEvent e) {
                 tableModel.fireTableRowsUpdated(0, tableModel.getRowCount() - 1);
             }
         });
         
         return headerTable;
	}
	
	public void setRows() {
		int rows = jTable.getRowCount();
		for(int i = 0;i < rows;i++) {
			headerTable.getModel().setValueAt(i, i, 0);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
          
	}
	 
}
