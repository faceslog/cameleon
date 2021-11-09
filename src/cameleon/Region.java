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

    public void increaseSquareTaken() {
        this.squareTaken++;
    }

    public boolean isFull() { return squareTaken == Globals.ZONE_SIZE * Globals.ZONE_SIZE; }

    public boolean isIn(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
    }

    public void changeRegionColor(int color) {
        for(int i = topLeft.getX(); i < bottomRight.getX(); i++) {
            for(int j = topLeft.getY(); j < bottomRight.getY(); j++) {
                boardRef.getSquares()[i][j] = color;
            }
        }
    }

}
