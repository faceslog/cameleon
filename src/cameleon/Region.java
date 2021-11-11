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

    public QuadPoint getTopLeft() { return topLeft; }

    public QuadPoint getBottomRight() { return bottomRight; }

    public void setSquareTaken(int _squareTaken)
    {
        squareTaken = _squareTaken;
    }

    public void increaseSquareTaken() {
        this.squareTaken++;
    }

    public boolean isFull() { return squareTaken == Globals.ZONE_SIZE * Globals.ZONE_SIZE; }

    public boolean isIn(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
    }

    @Override
    public String toString() {
        return "Region{" +
                "squareTaken=" + squareTaken +
                ", topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                ", boardRef=" + boardRef +
                '}';
    }
}
