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

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return  false;

        if(!(obj instanceof final QuadPoint point))
            return false;

        return (x == point.getX() && y == point.getY());
    }

    //https://medium.com/codelog/overriding-hashcode-method-effective-java-notes-723c1fedf51c
    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * result + getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public String toString() {
        return "QuadPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
