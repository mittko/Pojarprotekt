package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class EditableField extends JTextField implements ActionListener {
    private String text = null;
    boolean flag = false;
    private JPopupMenu popupMenu = null;
    private final String Copy = "Копирай";
    private final String Cut = "Изрежи";
    private final String Paste = "Постави";
    private boolean hasFocus = false;
    
    private Font CURRENT_FONT = 
    		new Font(Font.MONOSPACED,Font.LAYOUT_LEFT_TO_RIGHT,MainPanel.getFontSize());
    
    public EditableField(String text,int w) {
    	super(w);
    	this.text = text;
    	this.setBorder(BorderFactory.createLoweredBevelBorder());
    	// add right click popup menu
    	createPopupMenu();
    	this.addMouseListener(new MouseHandler(popupMenu));
    	
       }
    
	public EditableField(String text,int w,boolean flag) {
		super(w);
	    this.text = text;
		this.flag = flag;
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		// add right click popup menu
		createPopupMenu();
		this.addMouseListener(new MouseHandler(popupMenu));
	
	};
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getText().isEmpty()) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setBackground(Color.gray);
			g2.setColor(Color.black);
			g2.setFont(getTextFont()); // Font.Hanging_Baseline
			g2.drawString(text, 1, MainPanel.getFontSize() + 10); // figure out x, y
													// from font's
													// FontMetrics and
													// size of
													// component.
			if(flag) {
			g2.setColor(Color.red);
			g2.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,MainPanel.getFontSize() + 3));//(getFont().deriveFont(Font.BOLD));
			g2.drawString("*", this.getWidth()-20,MainPanel.getFontSize());
			}
			g2.dispose();
		} 
	}
     
	public boolean hasFocus() {
		return this.hasFocus;
	}
	private void createPopupMenu() {
		JMenuItem copyItem = new JMenuItem(Copy);
    	JMenuItem cutItem = new JMenuItem(Cut);
    	JMenuItem pasteItem = new JMenuItem(Paste);
    	
    	popupMenu = new JPopupMenu();
    	
    	popupMenu.add(copyItem);
    	popupMenu.add(cutItem);
    	popupMenu.add(pasteItem);
    	
    	Component[] components = popupMenu.getComponents();
    	for(int i = 0;i < components.length;i++) {
    		components[i].setFont(CURRENT_FONT);
    	}
    	copyItem.addActionListener(this);
    	cutItem.addActionListener(this);
    	pasteItem.addActionListener(this);
    	
	}

	  class MouseHandler extends MouseAdapter {
		  private JPopupMenu popupMenu = null;
		  
		  public MouseHandler(JPopupMenu popupMenu) {
			  this.popupMenu = popupMenu;
		  }
		
		  @Override
		  public void mousePressed(MouseEvent me) {
			  if(me.isPopupTrigger()) {
				  popupMenu.show(me.getComponent(),me.getX(), me.getY());
			  }
		  }
		  @Override
		  public void mouseReleased(MouseEvent me) {
			  if(me.isPopupTrigger()) {
				  popupMenu.show(me.getComponent(), me.getX(), me.getY());
			  }
		  }
	  }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if(cmd.equals(Copy)) {
			this.copy();
		} else if(cmd.equals(Cut)) {
			this.cut();
		} else if(cmd.equals(Paste)) {
			this.paste();
		}
	}

	public Font getTextFont() {
      return new Font(Font.DIALOG_INPUT,Font.HANGING_BASELINE,
    		  MainPanel.getFontSize() + 3); // or Font.HANGING_BASELINE
	}

}
