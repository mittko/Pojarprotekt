package utils;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import run.JustFrame;
import files.TextReader;

public class BrandListComboBox extends EditableComboBox {

	@Override
	public String getDataPath() {
		return "files/brand.txt";
	}
}
