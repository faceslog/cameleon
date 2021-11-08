package cameleon;

import core.datastruct.QuadPoint;

public class Region
{
    private int squareTaken;
    private Board boardRef;

    private QuadPoint topLeft;

    public Region(QuadPoint topLeft, Board _boardRef)
    {
        squareTaken = 0;
        boardRef = _boardRef;
    }

    public void setSquareTaken(int _squareTaken)
    {
        squareTaken = _squareTaken;
    }
    public boolean isFull() { return squareTaken == Globals.ZONE_SIZE * Globals.ZONE_SIZE; }

}
