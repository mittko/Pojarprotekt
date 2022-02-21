package Parts;

import utility.CustomButton;
import utility.GradientPanel;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Water extends MainPanel {

	public ArrayList<CustomButton> list = null;
	public static CustomButton ������ = null;
	public static CustomButton ���� = null;
	public static CustomButton ������ = null;
	public static CustomButton ��������� = null;
	public static CustomButton ������� = null;
	public static CustomButton ������� = null;
	public Water(Dimension dim) {
		list = new ArrayList<CustomButton>();
		
		JPanel block = new GradientPanel() ;
		JPanel pane1 = new JPanel();
		pane1.setOpaque(false);
		
		JPanel pane2 = new JPanel();
		pane2.setOpaque(false);
		
		JPanel pane3 = new JPanel();
		pane3.setOpaque(false);
		
		JPanel pane4 = new JPanel();
		pane4.setOpaque(false);
		
		block.setLayout(new GridLayout(4, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
	//	int buttonHeight =   block.getPreferredSize().height / 4; //100;
		int buttonVGap = 5;
        int buttonHGap = 20;
        
	    Dimension buttonDimension = new Dimension(
	    		(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));
	    
        CustomButton  ����� = new CustomButton();
    	�����.setPreferredSize(buttonDimension);
		�����.setAutoSizedIcon(�����, new LoadIcon().setIcons("PARTS2/�����.gif"));
	//	�����.setIcon(new LoadIcon().setIcons("PARTS/�����.gif"));
	    �����.setToolTipText(getHTML_Text(Glava));
	    �����.setName(Glava);
	    
	    ��������� = new CustomButton();
	    ���������.setPreferredSize(buttonDimension);
		���������.setAutoSizedIcon(���������, new LoadIcon().setIcons("PARTS2/���������.gif"));
	//	���������.setIcon(new LoadIcon().setIcons("PARTS/���������.gif"));
        ���������.setToolTipText(getHTML_Text(Manometar));
        ���������.setName(Manometar);
        
    	CustomButton ������ = new CustomButton();
    	������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Sphlend));
		������.setName(Sphlend);
		
		CustomButton  �_������� = new CustomButton();
		�_�������.setPreferredSize(buttonDimension);
		�_�������.setAutoSizedIcon(�_�������, new LoadIcon().setIcons("PARTS2/�-�������.gif"));
	//	�_�������.setIcon(new LoadIcon().setIcons("PARTS/�-�������.gif"));
		�_�������.setToolTipText(getHTML_Text(Uplatnenie));
		�_�������.setName(Uplatnenie);
	
	
        
    	CustomButton ������ = new CustomButton();
    	������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Zatvor));
		������.setName(Zatvor);
		
		
        
		
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Plomba));
		������.setName(Plomba);
		������.isEditable = false;
		
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������2.gif"));
   //     ������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
        ������.setToolTipText(getHTML_Text(Markuch));
        ������.setName(Markuch);
        
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
  //      ������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
        ������.setToolTipText(getHTML_Text(DarjachZaMarkuch));
        ������.setName(DarjachZaMarkuch);
        
        CustomButton ������� = new CustomButton();
        �������.setPreferredSize(buttonDimension);
		�������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
//		�������.setIcon(new LoadIcon().setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(Prujina));
		�������.setName(Prujina);
		
		CustomButton �������� = new CustomButton();
		��������.setPreferredSize(buttonDimension);
		��������.setAutoSizedIcon(��������, new LoadIcon().setIcons("PARTS2/��������.gif"));
	//	��������.setIcon(new LoadIcon().setIcons("PARTS/��������.gif"));
		��������.setToolTipText(getHTML_Text(Struinik));
		��������.setName(Struinik);
		
	
		
	
		
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(new LoadIcon().setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Patron));
		������.setName(Patron);
		
		
		CustomButton ����� = new CustomButton();
		�����.setPreferredSize(buttonDimension);
		�����.setAutoSizedIcon(�����, new LoadIcon().setIcons("PARTS2/�����.gif"));
   //     �����.setIcon(new LoadIcon().setIcons("PARTS/�����.gif"));
		�����.setToolTipText(getHTML_Text(Sonda));
		�����.setName(Sonda);
		
		
		CustomButton ��������4 = new CustomButton();
		��������4.setPreferredSize(buttonDimension);
	  ��������4.setAutoSizedIcon(��������4, new LoadIcon().setIcons("PARTS2/�������� 4.gif"));
	//	��������4.setIcon(new LoadIcon().setIcons("PARTS/�������� 4.gif"));
		��������4.setToolTipText(getHTML_Text(Struinik4));
		��������4.setName(Struinik4);
		
		CustomButton ����������_����� = new CustomButton();
		����������_�����.setPreferredSize(buttonDimension);
		����������_�����.setAutoSizedIcon(����������_�����, new LoadIcon().setIcons("PARTS2/���������� �����.gif"));
	//	����������_�����.setIcon(new LoadIcon().setIcons("PARTS/���������� �����.gif"));
		����������_�����.setToolTipText(getHTML_Text(BarbutajnaTraba));
		����������_�����.setName(BarbutajnaTraba);
		
		������� = new CustomButton();
		�������.setPreferredSize(buttonDimension);
	   �������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
	//	�������.setIcon(new LoadIcon().setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(IglichkaZaPompane));
		�������.setName(IglichkaZaPompane);
	
		������� = new CustomButton();
		�������.setPreferredSize(buttonDimension);
		�������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
	//	�������.setIcon(new LoadIcon().setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(KapachkaZaUplatnenie));
		�������.setName(KapachkaZaUplatnenie);
		
		CustomButton ���_������������� = new CustomButton();
		���_�������������.setPreferredSize(buttonDimension);
		���_�������������.setAutoSizedIcon(���_�������������, new LoadIcon().setIcons("PARTS2/���.gif"));
//		���_�������������.setIcon(new LoadIcon().setIcons("PARTS/���.gif"));
		���_�������������.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		���_�������������.setName(BoyaPojarogasitel);
		
		
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/waterEtiket.png"));
	//	������.setIcon(new LoadIcon().setIcons("PARTS/waterEtiket.png"));
		������.setToolTipText(getHTML_Text(Etiket));
		������.setName(Etiket);
		
		CustomButton ��� = new CustomButton();
		���.setPreferredSize(buttonDimension);
		���.setAutoSizedIcon(���, new LoadIcon().setIcons("PARTS2/���.gif"));
	//	���.setIcon(new LoadIcon().setIcons("PARTS/���.gif"));
		���.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		���.setName(SadZaGasitelnoVeshtestvo);
		
		���� = new CustomButton();
		����.setPreferredSize(buttonDimension);
		����.setAutoSizedIcon(����, new LoadIcon().setIcons("PARTS2/����.png"));
//		����.setIcon(new LoadIcon().setIcons("PARTS2/����.png"));
		����.setToolTipText(getHTML_Text(GasitelnoVeshtestvoVoda));
		����.setName(GasitelnoVeshtestvoVoda);
		����.isEditable = false;
		
	
	
		
		list.add(�����);
		list.add(���������);
		list.add(������);
		list.add(�_�������);
		list.add(������);
		
		pane1.add(�����);
		pane1.add(���������);
		pane1.add(������);
		pane1.add(�_�������);
		pane1.add(������);
		
		pane2.add(������);
		pane2.add(������);
		pane2.add(������);
		pane2.add(�������);
		pane2.add(��������);
		
		list.add(������);
		list.add(������);
		list.add(������);
		list.add(�������);
		list.add(��������);
		
		pane3.add(������);
		pane3.add(�����);
		pane3.add(��������4);
		pane3.add(����������_�����);
		pane3.add(�������);
		
		 list.add(������);
		 list.add(�����);
		 list.add(��������4);
		 list.add(����������_�����);
		 list.add(�������);
		
			list.add(�������);
			list.add(���_�������������);
			list.add(������);
			list.add(���);
			list.add(����);
			
			pane4.add(�������);
			pane4.add(���_�������������);
			pane4.add(������);
			pane4.add(���);
			pane4.add(����);
		 
//		block.setLayout(new GridLayout(4, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, 4 * buttonHeight));
//		
//		pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane1.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane1);
//		pane2.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane2.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane2);
		
		block.add(pane3);
//		pane3.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane3.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		
		block.add(pane4);
//		pane4.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane4.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		
		this.add(block);
		this.setOpaque(false);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       //   Framer f = new Framer(new Water());
	}

}
