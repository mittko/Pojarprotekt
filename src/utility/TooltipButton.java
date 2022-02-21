package utility;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TooltipButton extends DynamicButton {

//	private CustomTooltip ct = null;
//	private final Toolkit tool = Toolkit.getDefaultToolkit();
	public TooltipButton() {
		this.setContentAreaFilled(false);
		this.addMouseListener(new MouseHandler());
	}
	public TooltipButton(String text) {
		super(text);
	}
	/*	@Override
	public JToolTip createToolTip() {
		if(ct == null) {
			ct = new CustomTooltip();
		}
		ct.setComponent(this);
		return ct;
	}*/
   /*  public void setIconSize() {
		this.setPreferredSize(new Dimension(
				this.getIcon().getIconWidth(),this.getIcon().getIconHeight()));
	}*/
//     public void setSizeInPercent(int width,int height) {
//    	 double w = (tool.getScreenSize().getWidth() / width);
//    	 double h = (tool.getScreenSize().getHeight() / height) ;
//    	 this.setPreferredSize(new Dimension((int)w,(int)h));
//     }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
      class MouseHandler extends MouseAdapter {
    	  @Override
    	  public void mouseClicked(MouseEvent me) {
    		  TooltipButton.this.setCursor(null);
    	  }
    	
    	  @Override
    	  public void mouseEntered(MouseEvent me) {
    		  setContentAreaFilled(true);
    		 TooltipButton.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	  }
    	  @Override
    	  public void mouseExited(MouseEvent me) {
    		  setContentAreaFilled(false);
    	  }
      }
}
