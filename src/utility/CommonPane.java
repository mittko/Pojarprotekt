package utility;

import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class CommonPane extends JOptionPane {

	public static String showMessageDialog(final Object message, final Object[] options)
            throws HeadlessException {
        final JOptionPane pane = new JOptionPane(message, QUESTION_MESSAGE,
                                                 OK_CANCEL_OPTION, null,
                                                 options, null);
    
        pane.setWantsInput(true);
        pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
        pane.setMessageType(QUESTION_MESSAGE);
        pane.selectInitialValue();
        final String title = UIManager.getString("OptionPane.inputDialogTitle", null);
        final JDialog dialog = pane.createDialog(null, title);
        dialog.setVisible(true);
        dialog.dispose();
        final Object value = pane.getInputValue();
        return (value == UNINITIALIZED_VALUE) ? null : (String) value;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
