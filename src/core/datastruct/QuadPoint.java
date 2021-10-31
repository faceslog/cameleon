package core.datastruct;

public class QuadPoint
{
    private final int x;
    private final int y;

    public QuadPoint(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean compare(QuadPoint point)
    {
        return (x == point.getX() && y == point.getY());
    }

    @Override
    public String toString() {
        return "QuadPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
