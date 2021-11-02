package core.datastruct;

import java.util.ArrayList;

public class QuadTree<T>
{
    // Region Tree
    public QuadTree(QuadPoint _topLeft, QuadPoint _bottomRight)
    {
        if(_topLeft == null || _bottomRight == null)
            throw new NullPointerException("[ERROR] : QuadPoint _topLeft, _bottomRegion cannot be null");

        if(_topLeft.getX() > _bottomRight.getX() || _topLeft.getY() > _bottomRight.getY())
            throw new ArithmeticException("[ERROR] : Cannot create a negative quad tree");

        nodes = new ArrayList<>();
        for(int i = 0; i < MAX_CAPACITY; i++)
        {
            nodes.add(i, new QuadTree<>());
        }

        topLeft = _topLeft;
        bottomRight = _bottomRight;
        pos = null;
        data = null;
    }

    public QuadPoint getPos() {
        return pos;
    }

    public T getData() { return data; }

    public void setData(T _data) {  data = _data;  }

    // Theta(1)
    public boolean inBoundaries(QuadPoint point)
    {
        if(point == null)
            return false;
        else
            return (point.getX() >= topLeft.getX() && point.getX() <= bottomRight.getX() &&
                    point.getY() >= topLeft.getY() && point.getY() <= bottomRight.getY());
    }

    public void insert(QuadPoint _pos, T _data)
    {
        if(_data == null)
            throw new NullPointerException("[ERROR] : insert(_pos, _data) args cannot be null !");

        if(!inBoundaries(_pos))
            throw new IndexOutOfBoundsException("[ERROR] : Leaf position not in the quadtree boundaries !");

        // Shift bits by 1 to the right >> easier than writing the redundant division
        int xOffset = (topLeft.getX() + bottomRight.getX()) >> 1;
        int yOffset = (topLeft.getY() + bottomRight.getY()) >> 1;

        short index = getRegionIndex(_pos, new QuadPoint(xOffset, yOffset));

        // If it's a region node
        if(nodes.get(index).topLeft != null)
        {
            nodes.get(index).insert(_pos, _data);
            return;
        }

        // If it's en empty node there is no data and no topLeft corner
        if(nodes.get(index).topLeft == null && nodes.get(index).getData() == null)
        {
            nodes.set(index, new QuadTree<>(_pos, _data));
            return;
        }

        QuadPoint posToRelocate =  nodes.get(index).getPos();
        T dataToRelocate = nodes.get(index).getData();

        // Divide the QuadTree
        switch (index)
        {
            case TOP_LEFT -> nodes.set(index, new QuadTree<>(topLeft, new QuadPoint(xOffset, yOffset)));
            case TOP_RIGHT -> nodes.set(index, new QuadTree<>(
                    new QuadPoint(xOffset + 1, topLeft.getY()),
                    new QuadPoint(bottomRight.getX(), yOffset))
            );
            case BOTTOM_RIGHT -> nodes.set(index, new QuadTree<>(new QuadPoint(xOffset + 1, yOffset +1), bottomRight));
            case BOTTOM_LEFT -> nodes.set(index, new QuadTree<>(
                    new QuadPoint(topLeft.getX(), yOffset + 1),
                    new QuadPoint(xOffset, bottomRight.getY()))
            );
            default -> throw new IndexOutOfBoundsException("Unhandled Partition during Insertion");
        }

        nodes.get(index).insert(posToRelocate, dataToRelocate);
        nodes.get(index).insert(_pos, _data);
    }

    public QuadTree<T> search(QuadPoint point)
    {
        if(!inBoundaries(point))
            throw new IndexOutOfBoundsException("Point " + point + " is not in the QuadTree boundaries ...");

        int xOffset = (topLeft.getX() + bottomRight.getX()) >> 1;
        int yOffset = (topLeft.getY() + bottomRight.getY()) >> 1;
        short index = getRegionIndex(point, new QuadPoint(xOffset, yOffset));

        // If Region Node
        if(nodes.get(index).topLeft != null)
            return nodes.get(index).search(point);

        // If Empty Node
        if(nodes.get(index).topLeft == null && nodes.get(index).getData() == null)
            return null;

        if(point.compare(nodes.get(index).getPos()))
            return nodes.get(index);

        return null;
    }

    public ArrayList<QuadTree<T>> getNodes() { return nodes; }

    ////////////// PRIVATE MEMBERS //////////////

    static private final short TOP_LEFT = 0;
    static private final short TOP_RIGHT = 1;
    static private final short BOTTOM_RIGHT = 2;
    static private final short BOTTOM_LEFT = 3;
    static private final short MAX_CAPACITY = 4;

    private final ArrayList<QuadTree<T>> nodes;

    // Region
    private final QuadPoint topLeft; // xMin, yMin
    private final QuadPoint bottomRight; // xMax, yMax

    // Leaf
    private final QuadPoint pos;
    private T data;

    // Empty Node
    private QuadTree()
    {
        nodes = null;
        topLeft = null;
        bottomRight = null;
        pos = null;
        data = null;
    }

    // Point Tree (Represented by a leaf)
    private QuadTree(QuadPoint _pos, T _data)
    {
        nodes = null;
        topLeft = null;
        bottomRight = null;
        pos = _pos;
        data = _data;
    }

    private short getRegionIndex(QuadPoint pos, QuadPoint offsets)
    {
        if(pos == null)
            throw new NullPointerException("[ERROR] : point cannot be null");

        if(pos.getX() <= offsets.getX())
        {
            if(pos.getY() <= offsets.getY())
                return TOP_LEFT; // Top Left
            else
                return BOTTOM_LEFT; // Bottom Left
        }
        else
        {
            if(pos.getY() <= offsets.getY())
                return TOP_RIGHT; // Top Right
            else
                return BOTTOM_RIGHT; // Bottom Right
        }
    }
}
