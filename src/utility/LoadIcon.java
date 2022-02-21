package utility;

import javax.swing.*;
import java.net.URL;

public class LoadIcon {

    public final ClassLoader classLoader = getClass().getClassLoader();

    public ImageIcon setIcons(String image) {
        URL url = null;
        try {
            url = classLoader.getResource("Images/" + image);
        } finally {
            // setJavaIcon(image.substring(0,image.indexOf(".")));
            if (url == null)
                JOptionPane.showMessageDialog(null,
                        "Грешка при зареждане на изображенията!", "Error",
                        JOptionPane.ERROR_MESSAGE);
        }
        return new ImageIcon(url);
    }
}
