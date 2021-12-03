package view.controller;

import view.utils.BackgroundImage;
import view.ui.InitGameFrame;
import view.ui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartController  implements ActionListener {

    StartFrame sf;

    public StartController(StartFrame sf) {
        this.sf = sf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sf.getPlay()) {
            InitGameFrame initGameFrame = new InitGameFrame(sf);
            sf.getFrame().setContentPane(new BackgroundImage(3));
            sf.getFrame().getContentPane().add(initGameFrame.createPanel());
            sf.getFrame().repaint();
            sf.getFrame().revalidate();
        } else if (e.getSource() == sf.getExit()) {
            sf.getFrame().dispose();
            System.exit(0);
        }
    }
}
