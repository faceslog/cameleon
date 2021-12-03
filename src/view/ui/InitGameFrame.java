package view.ui;

import view.controller.InitGameController;
import view.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

public class InitGameFrame {

    public InitGameFrame(StartFrame sf) {
        this.sf = sf;
        frameSizeWithoutBorder = FrameUtils.frameSizeWithoutBorder(sf.getFrame());
        frameSizeWithoutBorder.width = (int) (frameSizeWithoutBorder.width * 0.8);
    }

    public void init() {
        loadGame = FrameUtils.createButton(PATH + "load-file.png", 1.25);

        confirm = FrameUtils.createButton(PATH + "ok.png", 1.25);

        reckless = FrameUtils.createButton(PATH + "reckless-disactivate.png", 1.25);
        brave = FrameUtils.createButton(PATH + "brave-activate.png", 1.25);

        arrowL = FrameUtils.createButton(PATH + "fleches2.png", 0.8);
        arrowR = FrameUtils.createButton(PATH + "fleches.png", 0.8);


        sizeLabel = new JLabel("SIZE : ", SwingConstants.CENTER);
        sizeLabel.setFont(FrameUtils.customFont());
        sizeLabel.setForeground(FrameUtils.colorText());

        sizeNum = new JLabel("1");
        sizeNum.setFont(FrameUtils.customFont());
        sizeNum.setForeground(FrameUtils.colorText());

        InitGameController igc = new InitGameController(sf, this);
        loadGame.addActionListener(igc);
        confirm.addActionListener(igc);
        reckless.addActionListener(igc);
        brave.addActionListener(igc);
        arrowL.addActionListener(igc);
        arrowR.addActionListener(igc);
    }

    public JPanel createPanel() {
        init();

        JPanel panel = new JPanel(new GridLayout(5,1,0,0));
        panel.setPreferredSize(frameSizeWithoutBorder);

        panel.add(new JLabel());

        JPanel chooseSize = new JPanel(new GridLayout(1,2));
        chooseSize.add(sizeLabel);

        JPanel chooseSize2 = new JPanel(new FlowLayout());
        chooseSize2.add(arrowL);
        chooseSize2.add(sizeNum);
        chooseSize2.add(arrowR);
        chooseSize2.setOpaque(false);

        chooseSize.add(chooseSize2);
        chooseSize.setOpaque(false);
        panel.add(chooseSize);

        JPanel chooseGamemode = new JPanel(new GridLayout(1,2));
        chooseGamemode.add(brave);
        chooseGamemode.add(reckless);
        chooseGamemode.setOpaque(false);
        panel.add(chooseGamemode);

        JPanel validate = new JPanel(new GridLayout(1,2));
        validate.add(confirm);
        validate.add(loadGame);
        validate.setOpaque(false);
        panel.add(validate);

        panel.add(new JLabel());

        panel.setOpaque(false);

        return panel;
    }

    public JButton getLoadGame() {
        return loadGame;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getBrave() {
        return brave;
    }

    public JButton getReckless() {
        return reckless;
    }

    public JButton getArrowL() {
        return arrowL;
    }

    public JButton getArrowR() {
        return arrowR;
    }

    public JLabel getSizeNum() {
        return sizeNum;
    }

    private StartFrame sf;

    private JButton loadGame;
    private JButton confirm;
    private JButton brave;
    private JButton reckless;
    private JButton arrowL;
    private JButton arrowR;

    private JLabel sizeLabel;
    private JLabel sizeNum;

    private final String PATH = "images/bouton/";

    private final Dimension frameSizeWithoutBorder;
}
