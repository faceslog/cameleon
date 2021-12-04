package view.controller;

import view.utils.BackgroundImage;
import view.ui.BoardFrame;
import view.ui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record BoardFrameController(StartFrame sf, BoardFrame bf) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bf.getExit()) {
            sf.getFrame().setContentPane(new BackgroundImage(1));
            sf.getFrame().getContentPane().add(sf.createPanel());
            sf.getFrame().repaint();
            sf.getFrame().revalidate();
        }
    }
}
