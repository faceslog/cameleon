package core;

import cameleon.Config;
import cameleon.Game;
import cameleon.enums.GameMode;
import view.ui.StartFrame;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        String[] choixAffichage = new String[]{"Terminal", "Interface"};
        int x = JOptionPane.showOptionDialog(null, "Choisissez un affichage", "Affichage",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choixAffichage, choixAffichage[0]);

        if(x==0) {
            Game party;
            Scanner scanner = new Scanner(System.in);

            int choice;
            do {
                System.out.println("GameMode : \n(1) BRAVE\n(2) RECKLESS\n(3) RECKLESS SMART");
                choice = scanner.nextInt();
            } while (choice != 1 && choice != 2 && choice != 3);
            GameMode gameMode;
            if(choice == 1) {
                gameMode = GameMode.BRAVE;
            } else if(choice == 2) {
                gameMode = GameMode.RECKLESS;
            } else {
                gameMode = GameMode.RECKLESS_SMART;
            }

            System.out.println("\nUse file ? ");
            boolean file = scanner.nextBoolean();
            if(file) {
                System.out.println("File path : ");
                String path = scanner.nextLine();
                party = new Game(path, gameMode);
            } else {
                int size;
                do {
                    System.out.println("\nSize : ");
                    size = scanner.nextInt();
                } while (size <= 0);
                System.out.println(size);
                party = new Game(size, gameMode);
            }

            party.start();

        } else if (x==1) {
            java.awt.EventQueue.invokeLater(() -> new StartFrame().getFrame().setVisible(true));
        }
    }
}
