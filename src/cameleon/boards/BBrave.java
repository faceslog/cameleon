package cameleon.boards;

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
    public void updateColor(int x, int y)
    {
        for (int i = x - 1; i <= x + 1; i++)
        {
            if(i < 0 || i >= getSize()) continue;

            for (int j = y - 1; j <= y+ 1; j++)
            {
                if(j < 0 || j >= getSize()) continue;

                QuadPoint point = new QuadPoint(i, j);

                if(getSquares()[i][j] != Config.FREE_SQUARE)
                {
                    //remove le point s'il existe dans la liste
                    getFreePoints().remove(point);
                    if(getSquares()[i][j] == getGameRef().getNotCurrent().getPlayerId())
                    {
                        //check 8 case autour si pas de case vide on n'ajoute pas (pas la mais rappel)
                        getGameRef().getNotCurrent().decreaseNbSquare();
                        getGameRef().getCurrent().increaseNbSquare();
                        getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                    }
                } else {
                    //ajoute le point si case vide
                    getGameRef().getBoard().getFreePoints().add(point);
                }
            }
        }
    }
}
