package cameleon.boards;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;
import cameleon.entities.Bot;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;

import java.util.ArrayList;

public class BBrave extends Board
{
    public BBrave(int n, Game _gameRef)
    {
        super(n, _gameRef);
    }

    public BBrave(int _size, int[][] _squares, Game _gameRef, ArrayList<QuadPoint> taken)
    {
        super(_size, _squares, _gameRef);

        if(_gameRef.isThereBotPlayers())
        {
            // Pour chaque point pris on évalue les points autours
            for(QuadPoint point : taken)
            {
                for (int i = point.getX() - 1; i <= point.getX() + 1; i++)
                {
                    if (i < 0 || i >= getSize())
                        continue;

                    for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {

                        if (j < 0 || j >= getSize())
                            continue;

                        if(getSquares()[i][j] == Config.FREE_SQUARE)
                        {
                            // Pour Brave on ajoute au joueur inverse
                            // Si le point actuel appartient au joueur 1 alors on donne le point libre au joueur 2
                            if(getSquares()[point.getX()][point.getY()] == _gameRef.getPlayer1().getPlayerId())
                            {
                                if(getGameRef().getPlayer2() instanceof Bot player2)
                                    player2.getFreePoints().add(new QuadPoint(i, j));
                            }
                            else // Si le point actuel appartient au joueur 2 alors on donne le point libre au joueur 1
                            {
                                if(getGameRef().getPlayer1() instanceof Bot player1)
                                    player1.getFreePoints().add(new QuadPoint(i, j));
                            }
                        }
                    }
                }
            }
        }
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
                    if(getSquares()[i][j] == getNotCurrentPlayer().getPlayerId())
                    {
                        //check 8 case autour si pas de case vide on n'ajoute pas (pas la mais rappel)
                        getNotCurrentPlayer().decreaseNbSquare();
                        getCurrentPlayer().increaseNbSquare();
                        getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                    }
                }
                else
                {
                    if(getNotCurrentPlayer() instanceof Bot enemy)
                        enemy.getFreePoints().add(point);
                }
            }
        }
    }
}
