package Cameleon.Entities;

import java.util.Scanner;

import Cameleon.Board;
import Cameleon.Player;
import Cameleon.Enums.CaseColor;
import Cameleon.Movement;

public class Human extends Player {
    /**
     * @param _name
     * @param _colour
     */
    public Human(String _name, CaseColor _colour) {
        super(_name, _colour);
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