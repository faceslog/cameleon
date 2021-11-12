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

    public void increaseSquareTaken() {
        this.squareTaken++;
    }

    public boolean isFull() { return squareTaken >= Globals.ZONE_SIZE * Globals.ZONE_SIZE; }

    public boolean isIn(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
    }

    public int isOwnedBy()
    {
        if(!isFull())
            return Globals.FREE_SQUARE; // Possédez par personne
        else
            return boardRef.getSquares()[topLeft.getX()][topLeft.getY()];
    }

    public void changeRegionColor()
    {
        for(int i = topLeft.getX(); i <= bottomRight.getX(); i++)
        {
            for(int j = topLeft.getY(); j <= bottomRight.getY(); j++)
            {
                if(boardRef.getSquares()[i][j] == boardRef.getNotCurrentPlayer().getPlayerId()) {
                    boardRef.getSquares()[i][j] = boardRef.getCurrentPlayer().getPlayerId();
                    boardRef.getNotCurrentPlayer().decreaseNbSquare();
                    boardRef.getCurrentPlayer().increaseNbSquare();
                } else if (boardRef.getSquares()[i][j] == Globals.FREE_SQUARE) {
                    boardRef.getSquares()[i][j] = boardRef.getCurrentPlayer().getPlayerId();
                    boardRef.getCurrentPlayer().increaseNbSquare();
                    increaseSquareTaken();
                }
            }
        }
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
