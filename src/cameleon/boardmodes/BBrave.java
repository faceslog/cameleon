package cameleon.boardmodes;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;

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
                        getGameRef().getNotCurrent().decreaseNbSquare();
                        getGameRef().getCurrent().increaseNbSquare();
                        getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                    }
                }
            }
        }
    }
}
