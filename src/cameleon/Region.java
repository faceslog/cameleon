package cameleon;

import cameleon.entities.Bot;
import core.datastruct.QuadPoint;

public class Region
{
    private int squareTaken;
    private final QuadPoint topLeft;
    private final QuadPoint bottomRight;
    private final Board boardRef;

    public Region(QuadPoint _topLeft, QuadPoint _bottomRight, Board _boardRef)
    {
        topLeft = _topLeft;
        bottomRight = _bottomRight;
        squareTaken = 0;
        boardRef = _boardRef;
    }

    public QuadPoint getTopLeft() { return topLeft; }

    public QuadPoint getBottomRight() { return bottomRight; }

    public void setSquareTaken(int _squareTaken)
    {
        squareTaken = _squareTaken;
    }

    public int getSquareTaken() { return squareTaken; }

    public int getMaxSquareInside()
    {
        return ((bottomRight.getX() - topLeft.getX()) + 1) * ((bottomRight.getY() - topLeft.getY()) + 1);
    }

    public void increaseSquareTaken()
    {
        this.squareTaken++;

        if(isFull())
            changeRegionColor();
    }

    public void increaseSquareTakenOnly()
    {
        this.squareTaken++;
    }

    public void changeRegionColor()
    {
        int[][] squares = boardRef.getSquares();
        Player current = boardRef.getCurrent();
        Player notCurrent = boardRef.getEnemy();

        for(int i = topLeft.getX(); i <= bottomRight.getX(); i++)
        {
            for(int j = topLeft.getY(); j <= bottomRight.getY(); j++)
            {
                if(squares[i][j] == notCurrent.getPlayerId())
                {
                    squares[i][j] = current.getPlayerId();
                    notCurrent.decreaseNbSquare();
                    current.increaseNbSquare();
                }
                else if (squares[i][j] == Config.FREE_SQUARE)
                {
                    if(current instanceof Bot player)
                        player.getFreePoints().remove(new QuadPoint(i, j));

                    if(notCurrent instanceof Bot enemy)
                        enemy.getFreePoints().remove(new QuadPoint(i, j));

                    squares[i][j] = current.getPlayerId();
                    current.increaseNbSquare();
                    squareTaken++;
                }
            }
        }
    }

    public int countChangeRegionColor()
    {
        int[][] squares = boardRef.getSquares();
        Player enemy = boardRef.getEnemy();
        int count = 0;

        for(int i = topLeft.getX(); i <= bottomRight.getX(); i++)
        {
            for(int j = topLeft.getY(); j <= bottomRight.getY(); j++)
            {
                if(squares[i][j] == enemy.getPlayerId() || squares[i][j] == Config.FREE_SQUARE)
                    count++;
            }
        }

        return count;
    }

    public boolean isFull() { return squareTaken >= getMaxSquareInside(); }

    public boolean include(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
    }

    public int isOwnedBy()
    {
        // Si une case est pleine alors sa couleur est automatiquement chang?? par la personne ayant plac?? le dernier pion
        // par cons??quent n'importe quelle case de cette r??gion devrait ??tre de la couleur la personne poss??dant la r??gion
        // ici on prend la case du coin gauche par soucis de facilit??.
        if(!isFull())
            return Config.FREE_SQUARE; // Personne ne poss??de la r??gion
        else
            return boardRef.getSquares()[topLeft.getX()][topLeft.getY()];
    }
}
