package Parts;


import utils.CustomButton;
import utils.GradientPanel;
import utils.LoadIcon;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dust extends MainPanel {

	public ArrayList<CustomButton> list = null;
	public static CustomButton ������ = null;  // ������
	public static CustomButton ����_BC = null; // ���� ��
	public static CustomButton ����_ABC = null; // ���� ���
	public static CustomButton ������ = null;
	public static CustomButton ��������� = null;
	public static CustomButton ������� = null;
	public static CustomButton ������� = null;
	public static CustomButton ������ = null;
	public static CustomButton �������������� = null;
	public static CustomButton ��������4 = null;
	public static CustomButton ������������������ = null;
	public static CustomButton ������������������� = null;
	public static CustomButton ��������������� = null;
	public static CustomButton �������������������  = null;
	
	public Dust(Dimension dim) {
		list = new ArrayList<CustomButton>();
		
		JPanel block = new GradientPanel() ;
//		int buttonHeight =  block.getPreferredSize().height / 5; // old -> 100
			int buttonVGap = 5;
	        int buttonHGap = 20;
		GridLayout paneLayout = new GridLayout(0, 5, buttonHGap, buttonVGap);
		JPanel pane1 = new JPanel();
		pane1.setLayout(paneLayout);
		pane1.setOpaque(false);
		
		JPanel pane2 = new JPanel();
		pane2.setLayout(paneLayout);
		pane2.setOpaque(false);
		
		JPanel pane3 = new JPanel();
		pane3.setLayout(paneLayout);
		pane3.setOpaque(false);
		
		JPanel pane4 = new JPanel();
		pane4.setLayout(paneLayout);
		pane4.setOpaque(false);
		
		JPanel pane5 = new JPanel();
		pane5.setLayout(paneLayout);
		pane5.setOpaque(false);
		
		block.setLayout(new GridLayout(5, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
	   Dimension buttonDimension =  new Dimension(new Dimension(
				(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15)
				));
	
		CustomButton  ����� = new CustomButton();
		�����.setPreferredSize(buttonDimension);
		�����.setAutoSizedIcon(�����, new LoadIcon().setIcons("PARTS2/�����.gif"));
	//	�����.setIcon(setIcons("PARTS/�����.gif"));
		
	    �����.setToolTipText(getHTML_Text(Glava));
	    �����.setName(Glava);
	  
		��������� = new CustomButton();
		���������.setPreferredSize(buttonDimension);
		���������.setAutoSizedIcon(���������, new LoadIcon().setIcons("PARTS2/���������.gif"));
//		���������.setIcon(setIcons("PARTS/���������.gif"));
        ���������.setToolTipText(getHTML_Text(Manometar));
        ���������.setName(Manometar);
        
    	CustomButton ������ = new CustomButton();
    	������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Sphlend));
		������.setName(Sphlend);
		
		CustomButton �_������� = new CustomButton();
		�_�������.setPreferredSize(buttonDimension);
		�_�������.setAutoSizedIcon(�_�������, new LoadIcon().setIcons("PARTS2/�-�������.gif"));
	//	�_�������.setIcon(setIcons("PARTS/�-�������.gif"));
		�_�������.setToolTipText(getHTML_Text(Uplatnenie));
		�_�������.setName(Uplatnenie);
	
	
        
    	CustomButton ������ = new CustomButton();
    	������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Zatvor));
		������.setName(Zatvor);
		
		
	
	
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
//		������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Plomba));
		������.setName(Plomba);
		������.isEditable = false;
		
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������2.gif"));
   //     ������.setIcon(setIcons("PARTS/������.gif"));
        ������.setToolTipText(getHTML_Text(Markuch));
        ������.setName(Markuch);
        
		�������������� = new CustomButton();
		��������������.setPreferredSize(buttonDimension);
		��������������.setAutoSizedIcon(��������������, new LoadIcon().setIcons("PARTS2/������.gif"));
    //    ��������������.setIcon(setIcons("PARTS/������.gif"));
        ��������������.setToolTipText(getHTML_Text(DarjachZaMarkuch));
        ��������������.setName(DarjachZaMarkuch);
        
        CustomButton ������� = new CustomButton();
        �������.setPreferredSize(buttonDimension);
		�������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
//		�������.setIcon(setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(Prujina));
		�������.setName(Prujina);
		
		CustomButton �������� = new CustomButton();
		��������.setPreferredSize(buttonDimension);
		��������.setAutoSizedIcon(��������, new LoadIcon().setIcons("PARTS2/��������.gif"));
	//	��������.setIcon(setIcons("PARTS/��������.gif"));
	    ��������.setToolTipText(getHTML_Text(Struinik));
		��������.setName(Struinik);
		
		
		
	
	
		
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Patron));
		������.setName(Patron);
		
		
		CustomButton ����� = new CustomButton();
		�����.setPreferredSize(buttonDimension);
		�����.setAutoSizedIcon(�����, new LoadIcon().setIcons("PARTS2/�����.gif"));
   //     �����.setIcon(setIcons("PARTS/�����.gif"));
		�����.setToolTipText(getHTML_Text(Sonda));
		�����.setName(Sonda);
		
		
		��������4 = new CustomButton();
		��������4.setPreferredSize(buttonDimension);
	  ��������4.setAutoSizedIcon(��������4, new LoadIcon().setIcons("PARTS2/�������� 4.gif"));
	//	��������4.setIcon(setIcons("PARTS/�������� 4.gif"));
		��������4.setToolTipText(getHTML_Text(Struinik4));
		��������4.setName(Struinik4);
		
		CustomButton ����������_����� = new CustomButton();
		����������_�����.setPreferredSize(buttonDimension);
		����������_�����.setAutoSizedIcon(����������_�����, new LoadIcon().setIcons("PARTS2/���������� �����.gif"));
	//	����������_�����.setIcon(setIcons("PARTS/���������� �����.gif"));
		����������_�����.setToolTipText(getHTML_Text(BarbutajnaTraba));
		����������_�����.setName(BarbutajnaTraba);
		
		 ������� = new CustomButton();
		�������.setPreferredSize(buttonDimension);
		�������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
		
	//	�������.setIcon(setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(IglichkaZaPompane));
		�������.setName(IglichkaZaPompane);
		
	
		
	
		
		������� = new CustomButton();
		�������.setPreferredSize(buttonDimension);
		�������.setAutoSizedIcon(�������, new LoadIcon().setIcons("PARTS2/�������.gif"));
	
	//	�������.setIcon(setIcons("PARTS/�������.gif"));
		�������.setToolTipText(getHTML_Text(KapachkaZaUplatnenie));
		�������.setName(KapachkaZaUplatnenie);
	
		
		������������������ = new CustomButton();
		������������������.setPreferredSize(buttonDimension);
		������������������.setAutoSizedIcon(������������������, new LoadIcon().setIcons("PARTS2/������ ������.gif"));
	
	//	������������������.setIcon(setIcons("PARTS/������ ������.gif"));
		������������������.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		������������������.setName(TvardoHodovoKolelo);
		
		������������������� = new CustomButton();
		�������������������.setPreferredSize(buttonDimension);
		�������������������.setAutoSizedIcon(�������������������, new LoadIcon().setIcons("PARTS2/������ �� ������ �����.gif"));
	   //�������������������.setIcon(setIcons("PARTS/������ �� ������ �����.gif"));
		�������������������.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		�������������������.setName(KoleloZaVisokoTeglo);
		
		��������������� = new CustomButton();
		���������������.setPreferredSize(buttonDimension);
		���������������.setAutoSizedIcon(���������������, new LoadIcon().setIcons("PARTS2/�������������.gif"));
//		���������������.setIcon(setIcons("PARTS/�������������.gif"));
		���������������.setToolTipText(getHTML_Text(RemontKolicka));
		���������������.setName(RemontKolicka);
		
		����_BC = new CustomButton();
		����_BC.setPreferredSize(buttonDimension);
	   ����_BC.setAutoSizedIcon(����_BC, new LoadIcon().setIcons("PARTS2/bc.gif"));
	  
//		����_BC.setIcon(setIcons("PARTS/bc.gif"));
		����_BC.setToolTipText(getHTML_Text(PrahBC));
		����_BC.setName(PrahBC);
		����_BC.isEditable = false;
		
		
		
		

		
		CustomButton ���_������������� = new CustomButton();
		���_�������������.setPreferredSize(buttonDimension);
		���_�������������.setAutoSizedIcon(���_�������������, new LoadIcon().setIcons("PARTS2/���.gif"));
//		���_�������������.setIcon(setIcons("PARTS/���.gif"));
		���_�������������.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		���_�������������.setName(BoyaPojarogasitel);
		
		������������������� = new CustomButton();
		�������������������.setPreferredSize(buttonDimension);
		�������������������.setAutoSizedIcon(�������������������, new LoadIcon().setIcons("PARTS2/����������.gif"));
	//	�������������������.setIcon(setIcons("PARTS/����������.gif"));
		�������������������.setToolTipText(getHTML_Text(BoyaKolichka));
		�������������������.setName(BoyaKolichka);
		
		
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/dustEtiket.png"));
	  
	//	������.setIcon(setIcons("PARTS/dustEtiket.png"));
		������.setToolTipText(getHTML_Text(Etiket));
		������.setName(Etiket);
		
		CustomButton ��� = new CustomButton();
		���.setPreferredSize(buttonDimension);
		���.setAutoSizedIcon(���, new LoadIcon().setIcons("PARTS2/���.gif"));
	  
//		���.setIcon(setIcons("PARTS/���.gif"));
		���.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		���.setName(SadZaGasitelnoVeshtestvo);
		
		����_ABC = new CustomButton();
		����_ABC.setPreferredSize(buttonDimension);
		����_ABC.setAutoSizedIcon(����_ABC, new LoadIcon().setIcons("PARTS2/abc.gif"));
	  
	 //   ����_ABC.setIcon(setIcons("PARTS/abc.gif"));
	    ����_ABC.setToolTipText(getHTML_Text(PrahABC));
	    ����_ABC.setName(PrahABC);
	    ����_ABC.isEditable = false;
	    
    
		
//	    
//		block.setLayout(new GridLayout(5, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, (int)(4.5 * buttonHeight)));
		
	//	pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		
		pane1.add(�����);
		pane1.add(���������);
		pane1.add(������);
		pane1.add(�_�������);
		pane1.add(������);
		
	
		list.add(�����);
		list.add(���������);
		list.add(������);
		list.add(�_�������);
		list.add(������);
		
		
		list.add(������);
		list.add(������);
		list.add(��������������);
		list.add(�������);
		list.add(��������);
		
		
		
		pane2.add(������);
		pane2.add(������);
		pane2.add(��������������);
		pane2.add(�������);
		pane2.add(��������);
		
		list.add(������);
		list.add(�����);
		list.add(��������4);
		list.add(����������_�����);
		list.add(�������);
		
		pane3.add(������);
		pane3.add(�����);
		pane3.add(��������4);
		pane3.add(����������_�����);
		pane3.add(�������);
	
		list.add(�������);
		list.add(������������������);
		list.add(�������������������);
		list.add(���������������);
		list.add(����_BC);
		
		
		pane4.add(�������);
		pane4.add(������������������);
		pane4.add(�������������������);
		pane4.add(���������������);
		pane4.add(����_BC);
	
		list.add(���_�������������);
		list.add(�������������������);
		list.add(������);
		list.add(���);
		list.add(����_ABC);
		
		pane5.add(���_�������������);
		pane5.add(�������������������);
		pane5.add(������);
		pane5.add(���);
		pane5.add(����_ABC);
		
	    
	    
		
		block.add(pane1);
	
		block.add(pane2);

		block.add(pane3);
		
		block.add(pane4);
		
		block.add(pane5);
		
		this.add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Framer f = new Framer(new Dust());
	}

}
