package cameleon.entities;

import java.util.Scanner;

import cameleon.*;
import core.datastruct.QuadPoint;

public class Human extends Player {

    public Human(int _playerId, Game _game) {
        super(_playerId, _game);
    }

    @Override
    public QuadPoint move()
    {
        int x,y;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Coords x : ");
            x = scanner.nextInt();
            System.out.println("Coords y : ");
            y = scanner.nextInt();
        } while (!checkMove(x,y, getGameRef().getBoard()));

        getGameRef().getBoard().nextMove(x,y);
        return new QuadPoint(x, y);
    }

    private boolean checkMove(int x, int y, Board board)
    {
        return board.doesSquareExist(x, y) && board.getSquares()[x][y] == Config.FREE_SQUARE;
    }
}