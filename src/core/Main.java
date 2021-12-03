package core;

import view.ui.StartFrame;

public class Main {

    public static void main(String[] args)
    {
        /* DO NOT REMOVE
        Game party = new Game();
        party.start();*/

        java.awt.EventQueue.invokeLater(() -> new StartFrame().getFrame().setVisible(true));
    }
}
