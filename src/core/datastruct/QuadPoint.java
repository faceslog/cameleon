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

        final int prime = 31;
        int result = 17;
        // use the toString to avoid problem with combination like (1, 0) (0, 1) etc ...
        result = prime * result + toString().hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "{x=" + x +
                ", y=" + y +
                '}';
    }
}
