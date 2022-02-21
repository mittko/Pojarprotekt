package utility;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.ListModel;

public class CustomList extends JList<Object>  {
    CustomTooltip ct;
    public CustomList() {
    	 this.addMouseMotionListener(new MouseMotionAdapter() {
    	        @Override
    	        public void mouseMoved(MouseEvent e) {
    	            JList l = (JList)e.getSource();
    	            ListModel m = l.getModel();
    	            int index = l.locationToIndex(e.getPoint());
    	            if( index>-1 ) {
    	                l.setToolTipText(m.getElementAt(index).toString());
    	            }
    	        }
    	    });
	}
    @Override
    public String getToolTipText(MouseEvent me) {
    	return "djdjjfjfj";
    }

    /*@Override
    public void setToolTipText(String txt) {
    	super.setToolTipText(txt);
    }*/
   
	@Override
	public JToolTip createToolTip() {
		System.out.println("eho@@@@");
		if(ct == null) {
			ct = new CustomTooltip();
		}
		ct.setComponent(this);
		return ct;
	}
}
