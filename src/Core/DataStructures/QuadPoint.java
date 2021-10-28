package Core.DataStructures;

public class QuadPoint
{
    private int x;
    private int y;

    public QuadPoint(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    public QuadPoint()
    {
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
