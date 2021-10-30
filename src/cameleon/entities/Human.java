package cameleon.entities;

import java.util.Scanner;

import cameleon.Board;
import cameleon.Player;
import cameleon.enums.CaseColor;
import cameleon.Movement;

public class Human extends Player {
    /**
     * @param _name
     * @param _color
     */
    public Human(String _name, CaseColor _color) {
        super(_name, _color);
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