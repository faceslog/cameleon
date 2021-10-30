package view.utils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Width is approx 4/6 of the actual screen resolution and height is 2/3
        jf.setBounds(0,0, (screenSize.width / 6) * 4, (screenSize.height / 3) * 2);
    }

    // This method take a JFrame and an image to display as the JFrame Background
    public static void setFrameImgBg(JFrame jf, Image img) {

        if(jf == null) return;

        jf.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                if(img != null)
                    g.drawImage(img, 0, 0, jf.getWidth(), jf.getHeight(), jf);
            }
        });
    }
}
