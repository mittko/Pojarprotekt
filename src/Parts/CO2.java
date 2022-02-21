package Parts;

import utility.CustomButton;
import utility.GradientPanel;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CO2 extends MainPanel {
	
	public static CustomButton ������ = null;
	public static CustomButton ����������_������� = null;
	
	
	public ArrayList<CustomButton> list = null;
	public CO2(Dimension dim) {
		list = new ArrayList<CustomButton>();
		JPanel block = new GradientPanel() ;
		
		block.setLayout(new GridLayout(4, 1, 10,5));
		block.setPreferredSize(new Dimension(dim.width,dim.height-10));//(new Dimension(this.WIDTH-20, (int)(2 * (this.HEIGHT  / 3.5))));
		
		JPanel pane1 = new JPanel();
		pane1.setOpaque(false);
	//	int buttonHeight = block.getPreferredSize().height / 4; // 100
		int buttonVGap = 5;
        int buttonHGap = 20;
	
        Dimension buttonDimension = new Dimension(
	    		(int)(block.getPreferredSize().getWidth() * 0.10),
				(int)(block.getPreferredSize().getHeight() * 0.15));
        
		CustomButton ����������_�����1 = new CustomButton();
		����������_�����1.setPreferredSize(buttonDimension);
		����������_�����1.setAutoSizedIcon(����������_�����1, new LoadIcon().setIcons("PARTS2/���� ������ 3.5.gif"));
	//	����������_�����1.setIcon(setIcons("PARTS/���� ������ 3.5.gif"));
		����������_�����1.setToolTipText(getHTML_Text(BarzVentil1_Model));
		����������_�����1.setName(BarzVentil1_Model);
	    
		CustomButton ����������_�����2 = new CustomButton();
		����������_�����2.setPreferredSize(buttonDimension);
		����������_�����2.setAutoSizedIcon(����������_�����2, new LoadIcon().setIcons("PARTS2/���� ������ 5.gif"));
	 // 	����������_�����2.setIcon(setIcons("PARTS2/���� ������ 5.gif"));
		����������_�����2.setToolTipText(getHTML_Text(BarzVentil2_Model));
		����������_�����2.setName(BarzVentil2_Model);
        
		CustomButton ����������_�����3 = new CustomButton();
		����������_�����3.setPreferredSize(buttonDimension);
		����������_�����3.setAutoSizedIcon(����������_�����3, new LoadIcon().setIcons("PARTS2/���� ������ 30.gif"));
	
	//	����������_�����3.setIcon(setIcons("PARTS/���� ������ 30.gif"));
		����������_�����3.setToolTipText(getHTML_Text(BarzVentil3_Model));
		����������_�����3.setName(BarzVentil3_Model);
        
		CustomButton ����������_�����3_�����_����� = new CustomButton();
		����������_�����3_�����_�����.setPreferredSize(buttonDimension);
		����������_�����3_�����_�����.setAutoSizedIcon(����������_�����3_�����_�����, new LoadIcon().setIcons("PARTS2/���� ������ 30 ����� �����.gif"));
	
	//	����������_�����3_�����_�����.setIcon(setIcons("PARTS/���� ������ 30 ����� �����.gif"));
		����������_�����3_�����_�����.setToolTipText(getHTML_Text(BarzVentil3Model_Prava_Rezba));
		����������_�����3_�����_�����.setName(BarzVentil3Model_Prava_Rezba);
        
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
		//������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Sphlend));
		������.setName(Sphlend);
		
		
		list.add(����������_�����1);
		list.add(����������_�����2);
		list.add(����������_�����3);
		list.add(����������_�����3_�����_�����);
		list.add(������);
        
		pane1.add(����������_�����1);
		pane1.add(����������_�����2);
		pane1.add(����������_�����3);
		pane1.add(����������_�����3_�����_�����);
		pane1.add(������);
		
		
		JPanel pane2 = new JPanel();
		pane2.setOpaque(false);
		
		������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(setIcons("PARTS/������.gif"));
		������.setToolTipText(getHTML_Text(Plomba));
		������.setName(Plomba);
		������.isEditable = false;
		
		CustomButton ����������������_�����1 = new CustomButton();
		����������������_�����1.setPreferredSize(buttonDimension);
		����������������_�����1.setAutoSizedIcon(����������������_�����1, new LoadIcon().setIcons("PARTS2/���������������� 2 ��.gif"));
//		����������������_�����1.setIcon(setIcons("PARTS/���������������� 2��.gif"));
		����������������_�����1.setToolTipText(getHTML_Text(Snegoobrazuvatel1_Model));
		����������������_�����1.setName(Snegoobrazuvatel1_Model);
		
		CustomButton ����������������_�����2 = new CustomButton();
		����������������_�����2.setPreferredSize(buttonDimension);
		����������������_�����2.setAutoSizedIcon(����������������_�����2, new LoadIcon().setIcons("PARTS2/���������������� 5 ��.gif"));
	//	����������������_�����2.setIcon(setIcons("PARTS/���������������� 5��.gif"));
		����������������_�����2.setToolTipText(getHTML_Text(Snegoobrazuvatel2_Model));
		����������������_�����2.setName(Snegoobrazuvatel2_Model);
		
		CustomButton ����������������_�����3 = new CustomButton();
		����������������_�����3.setPreferredSize(buttonDimension);
		����������������_�����3.setAutoSizedIcon(����������������_�����3, new LoadIcon().setIcons("PARTS2/���������������� 30 ��.gif"));
	//	����������������_�����3.setIcon(setIcons("PARTS/���������������� 30��.gif"));
		����������������_�����3.setToolTipText(getHTML_Text(Snegoobrazuvatel3_Model));
		����������������_�����3.setName(Snegoobrazuvatel3_Model);
		
		CustomButton ����� = new CustomButton();
		�����.setPreferredSize(buttonDimension);
		�����.setAutoSizedIcon(�����, new LoadIcon().setIcons("PARTS2/�����.gif"));
//		�����.setIcon(setIcons("PARTS/�����.gif"));
		�����.setToolTipText(getHTML_Text(Sonda));
		�����.setName(Sonda);
		
		list.add(������);
		list.add(����������������_�����1);
		list.add(����������������_�����2);
		list.add(����������������_�����3);
		list.add(�����);
		
		pane2.add(������);
		pane2.add(����������������_�����1);
		pane2.add(����������������_�����2);
		pane2.add(����������������_�����3);
		pane2.add(�����);
		
		JPanel pane3 = new JPanel();
		pane3.setOpaque(false);
		
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/������.gif"));
	//	������.setIcon(setIcons("PARTS/drajka2.png"));
		������.setToolTipText(getHTML_Text(DrajkaZaPojarogasitel));
		������.setName(DrajkaZaPojarogasitel);
		
		CustomButton �����������_��������� = new CustomButton();
		�����������_���������.setPreferredSize(buttonDimension);
		�����������_���������.setAutoSizedIcon(�����������_���������, new LoadIcon().setIcons("PARTS2/���������.gif"));
	//	�����������_���������.setIcon(setIcons("PARTS/���������.gif"));
		�����������_���������.setToolTipText(getHTML_Text(ZapresovaneNaNakrainik));
		�����������_���������.setName(ZapresovaneNaNakrainik);
		
		CustomButton ������_������ = new CustomButton();
		������_������.setPreferredSize(buttonDimension);
		������_������.setAutoSizedIcon(������_������, new LoadIcon().setIcons("PARTS2/������ ������.gif"));
	//	������_������.setIcon(setIcons("PARTS/������ ������.gif"));
		������_������.setToolTipText(getHTML_Text(TvardoHodovoKolelo));
		������_������.setName(TvardoHodovoKolelo);
		
		CustomButton ������_������_����� = new CustomButton();
		������_������_�����.setPreferredSize(buttonDimension);
		������_������_�����.setAutoSizedIcon(������_������_�����, new LoadIcon().setIcons("PARTS2/������ �� ������ �����.gif"));
	//	������_������_�����.setIcon(setIcons("PARTS/������ �� ������ �����.gif"));
		������_������_�����.setToolTipText(getHTML_Text(KoleloZaVisokoTeglo));
		������_������_�����.setName(KoleloZaVisokoTeglo);
		
		CustomButton ������_������� = new CustomButton();
		������_�������.setPreferredSize(buttonDimension);
		������_�������.setAutoSizedIcon(������_�������, new LoadIcon().setIcons("PARTS2/�������������.gif"));
	//	������_�������.setIcon(setIcons("PARTS/�������������.gif"));
		������_�������.setToolTipText(getHTML_Text(RemontKolicka));
		������_�������.setName(RemontKolicka);
		
		pane3.add(������);
		pane3.add(�����������_���������);
		pane3.add(������_������);
		pane3.add(������_������_�����);
		pane3.add(������_�������);
		
		list.add(������);
		list.add(�����������_���������);
		list.add(������_������);
		list.add(������_������_�����);
		list.add(������_�������);

		JPanel pane4 = new JPanel();
		pane4.setOpaque(false);
		
		CustomButton ���_������� = new CustomButton();
		 ���_�������.setPreferredSize(buttonDimension);
		���_�������.setAutoSizedIcon( ���_�������, new LoadIcon().setIcons("PARTS2/����������.gif"));
	//	���_�������.setIcon(setIcons("PARTS/����������.gif"));
		���_�������.setToolTipText(getHTML_Text(BoyaKolichka));
		���_�������.setName(BoyaKolichka);
		
		CustomButton ���_������������� = new CustomButton();
		���_�������������.setPreferredSize(buttonDimension);
		���_�������������.setAutoSizedIcon(���_�������������, new LoadIcon().setIcons("PARTS2/���.gif"));
	//	���_�������������.setIcon(setIcons("PARTS/���.gif"));
		���_�������������.setToolTipText(getHTML_Text(BoyaPojarogasitel));
		���_�������������.setName(BoyaPojarogasitel);
		
		CustomButton ������ = new CustomButton();
		������.setPreferredSize(buttonDimension);
		������.setAutoSizedIcon(������, new LoadIcon().setIcons("PARTS2/co2Etiket.png"));
	//	������.setIcon(setIcons("PARTS/co2Etiket.png"));
		������.setToolTipText(getHTML_Text(Etiket));
		������.setName(Etiket);
		
		
		// enabled for now
		CustomButton ��� = new CustomButton();
		���.setPreferredSize(buttonDimension);
		���.setAutoSizedIcon(���, new LoadIcon().setIcons("PARTS2/���.gif"));
	//	���.setIcon(setIcons("PARTS/���CO2.gif"));
		���.setToolTipText(getHTML_Text(SadZaGasitelnoVeshtestvo));
		���.setName(SadZaGasitelnoVeshtestvo);
	    
	    ����������_������� = new CustomButton();
	    ����������_�������.setPreferredSize(buttonDimension);
	    ����������_�������.setAutoSizedIcon(����������_�������, new LoadIcon().setIcons("PARTS2/gas.gif"));
	//    ����������_�������.setIcon(setIcons("PARTS2/gas.gif"));
	    ����������_�������.setToolTipText(getHTML_Text(GasitelnoVeshtestvoCO2));
	    ����������_�������.setName(GasitelnoVeshtestvoCO2);
	    ����������_�������.isEditable = false;
	
		//
		list.add(���_�������);
		list.add(���_�������������);
		list.add(������);
		list.add(���);  
		list.add(����������_�������);
		
		
		pane4.add(���_�������);
		pane4.add(���_�������������);
		pane4.add(������);
		pane4.add(���);
		pane4.add(����������_�������);
	
//		block.setLayout(new GridLayout(4, 1, 10,5));
//		block.setPreferredSize(new Dimension(this.WIDTH-20, 4 * buttonHeight));
//		
//		pane1.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane1.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane1);
//		pane2.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane2.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane2);
//		pane3.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane3.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane3);
//		pane4.setPreferredSize(new Dimension(this.WIDTH-20, buttonHeight));
		pane4.setLayout(new GridLayout(0, 5, buttonHGap, buttonVGap));
		block.add(pane4);
		this.add(block);
		
	//	this.setPreferredSize(block.getPreferredSize());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    //       Framer f = new Framer(new CO2());
	}

}
