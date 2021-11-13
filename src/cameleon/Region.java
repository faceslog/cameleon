package cameleon;

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

    public void changeRegionColor()
    {
        int[][] squares = boardRef.getSquares();
        Player current = boardRef.getCurrentPlayer();
        Player notCurrent = boardRef.getNotCurrentPlayer();

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
                    squares[i][j] = current.getPlayerId();
                    current.increaseNbSquare();
                    squareTaken++;
                }
            }
        }
    }

    public boolean isFull() { return squareTaken >= ((bottomRight.getX() - topLeft.getX()) + 1) * ((bottomRight.getY() - topLeft.getY()) + 1); }

    public boolean include(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
    }

    public int isOwnedBy()
    {
        // Si une case est pleine alors sa couleur est automatiquement changé par la personne ayant placé le dernier pion
        // par conséquent n'importe quelle case de cette région devrait être de la couleur la personne possédant la région
        // ici on prend la case du coin gauche par soucis de facilité.
        if(!isFull())
            return Config.FREE_SQUARE; // Personne ne possède la région
        else
            return boardRef.getSquares()[topLeft.getX()][topLeft.getY()];
    }

    @Override
    public String toString() {
        return "Region{" +
                "squareTaken=" + squareTaken +
                ", topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                '}';
    }
}
