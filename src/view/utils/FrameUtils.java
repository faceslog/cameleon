package view.utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FrameUtils {

    // This method take a JFrame and set its position in the center of the screen depending on the resolution
    public static void setFrameCenter(JFrame jf) {
        if(jf == null) return;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        int x = (screen.width - jf.getWidth()) / 2;
        int y = (screen.height - jf.getHeight()) / 2;
        jf.setLocation(x, y);
    }

    // This method take a JFrame and set its size depending on the screen resolution
    public static void setFrameSizeFromScreenResolution(JFrame jf) {
        if(jf == null) return;
        Dimension dim = getDimension();
        // Width is approx 4/6 of the actual screen resolution and height is 2/3
        jf.setBounds(0,0, dim.width, dim.height);
    }

    public static Dimension getDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((screenSize.width / 6) * 4, (screenSize.height / 4) * 3);
    }

    public static Dimension frameSizeWithoutBorder(JFrame frame) {
        return new Dimension((int)frame.getContentPane().getSize().getWidth(),
                (int)frame.getContentPane().getSize().getHeight());
    }

    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static ImageIcon resizeIconPercentage(ImageIcon icon, double resizedWidth, double resizedHeight) {
        Image img = icon.getImage();

        return (ImageIcon) resizeIcon(new ImageIcon(img),
                (int) (img.getWidth(null) * resizedWidth),
                (int) (img.getHeight(null) * resizedHeight));
    }

    public static JButton createButton(String path, double size) {

        ImageIcon buttonIcon = resizeIconPercentage(new ImageIcon(path), size, size);
        JButton button = new JButton(buttonIcon);

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        return button;
    }

    public static Font customFont() {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("images/font/retro_computer_personal_use.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Color colorText() {
        return Color.decode("#182426");
    }

    public static Color colorP1() {
        return Color.decode("#930029");
    }

    public static Color colorP2() {
        return Color.decode("#003c90");
    }
}
