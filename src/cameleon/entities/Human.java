package cameleon.entities;

import java.util.Scanner;

import cameleon.Board;
import cameleon.Player;
import cameleon.enums.CaseColor;
import cameleon.Movement;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;

public class Human extends Player {
    /**
     * @param _name
     * @param _color
     */
    public Human(String _name, CaseColor _color, GameMode _gameMode) {
        super(_name, _color, _gameMode);
    }

    @Override
    public void move(Board board) {
        //super.move();
        int x,y;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Coord x : ");
            x = scanner.nextInt();
            System.out.println("Coord y : ");
            y = scanner.nextInt();
        } while (!verifMove(x,y,board));
        new Movement(new QuadPoint(x,y), this.getColor(),board);
    }

    public boolean verifMove(int x, int y, Board board) {
        return x >= 0 && x < board.getSize() &&
                y >= 0 && y < board.getSize() &&
                board.getQuadTree().search(new QuadPoint(x,y)) == null;
    }
}