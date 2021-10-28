package Cameleon;

import java.util.Scanner;

public class Human extends Player {
    /**
     * @param c
     * @param name
     */
    public Human(Color c, String name) {
        super(c, name);
    }

    @Override
    public void move(Board board) {
        //super.move();
        int x,y;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Coord x : ");
        x = scanner.nextInt();
        System.out.println("Coord y : ");
        y = scanner.nextInt();

        new Movement(x,y, this.getColor(),board);
    }
}