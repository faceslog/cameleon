package view.ui;

import view.controller.StartController;
import view.utils.BackgroundImage;
import view.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

public class StartFrame {

    private JButton play;
    private JButton exit;
    private JLabel title;
    private final JFrame frame = new JFrame();

    public StartFrame() {
        init();
    }

    private void init() {
        Image textLogo = new ImageIcon("images/Sans titre-1.png").getImage();
        title = new JLabel(FrameUtils.resizeIconPercentage(new ImageIcon(textLogo), 0.75, 0.75));

        play = FrameUtils.createButton("images/button/start.png", 1.25);
        exit = FrameUtils.createButton("images/button/exit.png", 1.25);

        StartController sc = new StartController(this);
        play.addActionListener(sc);
        exit.addActionListener(sc);

        // Set the title
        ImageIcon logo = new ImageIcon("images/logo.png");
        frame.setIconImage(logo.getImage());
        frame.setTitle("Cameleon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(new BackgroundImage(1));
        FrameUtils.setFrameSizeFromScreenResolution(frame);
        FrameUtils.setFrameCenter(frame);
        frame.getContentPane().add(createPanel());
    }

    public JPanel createPanel() {
        //JPanel p = new JPanel(new GridLayout(2,1));
        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(FrameUtils.getDimension());

        p.add(title, BorderLayout.NORTH);

        p.setOpaque(false);

        JPanel panelB = new JPanel(new GridLayout(3,3, 0, 50));
        panelB.add(new JLabel());
        panelB.add(play);
        panelB.add(new JLabel());

        panelB.add(new JLabel());
        panelB.add(exit);
        panelB.add(new JLabel());

        panelB.add(new JLabel());
        panelB.add(new JLabel());
        panelB.add(new JLabel());

        panelB.setOpaque(false);

        p.add(panelB, BorderLayout.CENTER);

        return p;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getPlay() {
        return play;
    }

    public JButton getExit() {
        return exit;
    }
}
