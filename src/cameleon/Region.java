package cameleon;

import core.datastruct.QuadPoint;

public class Region
{
    private int squareTaken;
    private QuadPoint topLeft;
    private QuadPoint bottomRight;

    // En a t'on réellement besoin ???
    private Board boardRef;

    public Region(QuadPoint _topLeft, QuadPoint _bottomRight, Board _boardRef)
    {
        topLeft = _topLeft;
        bottomRight = _bottomRight;
        squareTaken = 0;
        boardRef = _boardRef;
    }

    public void setSquareTaken(int _squareTaken)
    {
        squareTaken = _squareTaken;
    }
    public boolean isFull() { return squareTaken == Globals.ZONE_SIZE * Globals.ZONE_SIZE; }

}
