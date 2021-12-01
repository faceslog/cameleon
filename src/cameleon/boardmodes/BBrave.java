package cameleon.boardmodes;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;
import core.datastruct.QuadPoint;

public class BBrave extends Board
{
    public BBrave(int n, Game _gameRef)
    {
        super(n, _gameRef);
    }

    public BBrave(int _size, int[][] _squares, Game _gameRef)
    {
        super(_size, _squares, _gameRef);
    }

    @Override
    protected void updateColor(int x, int y)
    {
        for (int i = x - 1; i <= x + 1; i++)
        {
            if(i < 0 || i >= getSize()) continue;

            for (int j = y - 1; j <= y+ 1; j++)
            {
                if(j < 0 || j >= getSize()) continue;

                if(getSquares()[i][j] != Config.FREE_SQUARE)
                {
                    if(getSquares()[i][j] == getGameRef().getNotCurrent().getPlayerId())
                    {
                        //check 8 case autour si pas de case vide on ajoute pas (pas la mais rappel)
                        //get le quad point a la position
                        System.out.println("POINT :");
                        System.out.println(i);
                        System.out.println(j);
                        QuadPoint point = getGameRef().getNotCurrent().getPointCoord(i,j);
                        System.out.println(point);
                        System.out.println();

                        System.out.println("REMOVE :");
                        System.out.println(getGameRef().getNotCurrent().getPlayerId() + " :" + getGameRef().getNotCurrent().getListPoints().getList());
                        getGameRef().getNotCurrent().decreaseNbSquare();
                        //remove point de la liste
                        getGameRef().getNotCurrent().getListPoints().remove(point);
                        System.out.println(getGameRef().getNotCurrent().getPlayerId() + " :" + getGameRef().getNotCurrent().getListPoints().getList());
                        System.out.println();
                        System.out.println();

                        System.out.println("ADD :");
                        System.out.println(getGameRef().getCurrent().getPlayerId() + " :" + getGameRef().getCurrent().getListPoints().getList());
                        getGameRef().getCurrent().increaseNbSquare();
                        //add le point a la liste
                        getGameRef().getCurrent().getListPoints().add(point);
                        System.out.println(getGameRef().getCurrent().getPlayerId() + " :" + getGameRef().getCurrent().getListPoints().getList());
                        System.out.println();


                        getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                    }
                }
            }
        }
    }
}
