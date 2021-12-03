package view.utils;

import javax.swing.*;
import java.awt.*;

public class BackgroundImage extends JPanel {
    Image fond;

    public BackgroundImage(int i) {
        fond = getToolkit().getImage(image(i));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
    }

    public String image(int i) {
        if(i == 1 ) {
            return "images/karina-formanova-rainforest-animation.gif";
        } else if(i == 2) {
            return "images/fond-board.png";
        } else if(i == 3) {
            return "images/fond-board2.png";
        } else {
            return "";
        }
    }
}
