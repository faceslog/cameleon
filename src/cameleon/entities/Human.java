package cameleon.entities;

import java.util.Scanner;

import cameleon.*;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;

public class Human extends Player {

    public Human(int _playerId, Game _game) {
        super(_playerId, _game);
    }

    @Override
    public QuadPoint move()
    {
        int x,y = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Coords x : ( < 0 ==> evaluateMove)");
            x = scanner.nextInt();

            if(x < 0) {
                int xEval, yEval;
                do {
                    System.out.println("Coords x to check : ");
                    xEval = scanner.nextInt();
                    System.out.println("Coords y to check : ");
                    yEval = scanner.nextInt();
                } while (!checkMove(xEval,yEval, getGameRef().getBoard()));

                if(getGameRef().getBoard().getNotCurrentPlayer() instanceof Bot bot) {
                    int eval;
                    if(getGameRef().getGameMode() == GameMode.BRAVE) {
                        eval = bot.evaluateMoveBrave(new QuadPoint(xEval,yEval));
                    } else {
                        eval = bot.evaluateMoveReckless(new QuadPoint(xEval,yEval));
                    }
                    System.out.println(eval);
                }
            } else {
                System.out.println("Coords y : ");
                y = scanner.nextInt();
            }

        } while (!checkMove(x,y, getGameRef().getBoard()));

        getGameRef().getBoard().nextMove(x,y);
        return new QuadPoint(x, y);
    }

    private boolean checkMove(int x, int y, Board board)
    {
        return board.doesSquareExist(x, y) && board.getSquares()[x][y] == Config.FREE_SQUARE;
    }
}