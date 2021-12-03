package view.controller;

import view.utils.BackgroundImage;
import view.ui.BoardFrame;
import view.ui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardFrameController implements ActionListener {

    private StartFrame sf;
    private BoardFrame bf;

    public BoardFrameController(StartFrame sf, BoardFrame bf) {
        this.sf = sf;
        this.bf = bf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bf.getExit()) {
            sf.getFrame().setContentPane(new BackgroundImage(1));
            sf.getFrame().getContentPane().add(sf.createPanel());
            sf.getFrame().repaint();
            sf.getFrame().revalidate();
        }
    }
}
