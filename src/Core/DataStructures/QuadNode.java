package Core.DataStructures;

public class QuadNode<T>
{
    private QuadPoint pos;
    private T data; // la donnée stockée ici la couleur

    public QuadNode(QuadPoint _pos, T _data)
    {
        pos = _pos;
        data = _data;
    }

    public QuadPoint getPos() {
        return pos;
    }

    public T getData() {
        return data;
    }

    public void setData(T _data) {
        data = _data;
    }

    public String toString() {
        return "X = " + pos.getX() + " : Y = " + pos.getY() + " - Data = " + data;
    }

}
