package view.controller;

import cameleon.Game;
import cameleon.enums.GameMode;
import view.utils.BackgroundImage;
import view.utils.FrameUtils;
import view.ui.BoardFrame;
import view.ui.InitGameFrame;
import view.ui.StartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitGameController implements ActionListener {

    private StartFrame sf;
    private InitGameFrame ig;
    private GameMode gameMode = GameMode.BRAVE;
    private int size = 1;

    public InitGameController(StartFrame sf, InitGameFrame ig) {
        this.sf = sf;
        this.ig = ig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ig.getConfirm()) {
            BoardFrame boardFrame = new BoardFrame(sf, new Game(size, gameMode));
            sf.getFrame().setContentPane(new BackgroundImage(3));
            sf.getFrame().getContentPane().add(boardFrame.createPanel());
            sf.getFrame().repaint();
            sf.getFrame().revalidate();
        } else if (e.getSource() == ig.getLoadGame()) {
            JFileChooser dialogue = new JFileChooser("./docs");
            String nameFile = null;
            if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
                nameFile = dialogue.getSelectedFile().getAbsolutePath();
            }

            if(nameFile!=null) {
                BoardFrame boardFrame = new BoardFrame(sf, new Game(nameFile, gameMode));
                sf.getFrame().setContentPane(new BackgroundImage(3));
                sf.getFrame().getContentPane().add(boardFrame.createPanel());
                sf.getFrame().repaint();
                sf.getFrame().revalidate();
            }
        } else if (e.getSource() == ig.getBrave()) {
            gameMode = GameMode.BRAVE;
            ig.getBrave().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/bouton/brave-activate.png"), 1.25, 1.25));
            ig.getReckless().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/bouton/reckless-disactivate.png"), 1.25, 1.25));
        } else if (e.getSource() == ig.getReckless()) {
            ig.getBrave().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/bouton/brave-disactivate.png"), 1.25, 1.25));
            ig.getReckless().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/bouton/reckless-activate.png"), 1.25, 1.25));

            String[] recklessMode = new String[]{"Glutton", "Smart"};
            int x = JOptionPane.showOptionDialog(null, "Choose Reckless Mode", "Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, recklessMode, recklessMode[0]);
            if(x == 0) {
                gameMode = GameMode.RECKLESS;
            } else {
                gameMode = GameMode.RECKLESS_SMART;
            }

        } else if (e.getSource() == ig.getArrowL()) {
            size--;
            if(size < 1) {
                size = 1;
                ig.getSizeNum().setText(String.valueOf(size));
            } else {
                ig.getSizeNum().setText(String.valueOf(size));
            }
        } else if (e.getSource() == ig.getArrowR()) {
            size++;
            ig.getSizeNum().setText(String.valueOf(size));
        }
    }
}

