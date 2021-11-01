package core.datastruct;

import java.util.ArrayList;

public class QuadTree<T>
{
    static private final int MAX_CAPACITY = 4;
    ArrayList<QuadNode<T>> nodes;

    // Boundaries
    private final QuadPoint minTopLeftCorner; // xMin, yMin
    private final QuadPoint maxBottomRightCorner; // xMax, yMax

    // Enfants de cet arbre
    private QuadTree<T> topLeftTree;
    private QuadTree<T> topRightTree;
    private QuadTree<T> bottomLeftTree;
    private QuadTree<T> bottomRightTree;

    public QuadTree(QuadPoint _topLeft, QuadPoint _bottomRight)
    {
        minTopLeftCorner = _topLeft;
        maxBottomRightCorner = _bottomRight;
        nodes = new ArrayList<>();
        topLeftTree = null;
        topRightTree = null;
        bottomLeftTree = null;
        bottomRightTree = null;
    }

    public QuadTree<T> getTopLeftTree() {
        return topLeftTree;
    }

    public QuadTree<T> getTopRightTree() {
        return topRightTree;
    }

    public QuadTree<T> getBottomLeftTree() {
        return bottomLeftTree;
    }

    public QuadTree<T> getBottomRightTree() {
        return bottomRightTree;
    }

    public ArrayList<QuadNode<T>> getNodes() {
        return nodes;
    }

    // Theta(1)
    public boolean inBoundaries(QuadPoint point)
    {
        if(point == null)
            return false;
        else
            return (point.getX() >= minTopLeftCorner.getX() && point.getX() <= maxBottomRightCorner.getX() &&
                    point.getY() >= minTopLeftCorner.getY() && point.getY() <= maxBottomRightCorner.getY());
    }

    // O(log n)
    public void insert(QuadNode<T> node)
    {
        if(node == null)
            throw new NullPointerException("ERROR: node cannot be NULL");

       if(!inBoundaries(node.getPos()))
           throw new IndexOutOfBoundsException("ERROR: Node position not in the quadtree boundaries !");

       if(nodes.size() < MAX_CAPACITY)
       {
           nodes.add(node);
           return;
       }

       if(topLeftTree == null)
           divide();

       if(topLeftTree.inBoundaries(node.getPos()))
           topLeftTree.insert(node);
       else if(topRightTree.inBoundaries(node.getPos()))
           topRightTree.insert(node);
       else if(bottomLeftTree.inBoundaries(node.getPos()))
           bottomLeftTree.insert(node);
       else if(bottomRightTree.inBoundaries(node.getPos()))
           bottomRightTree.insert(node);
       else
           throw new RuntimeException("ERROR: Unhandled Partition !");
    }

    // O(log n)
    public QuadNode<T> search(QuadPoint p)
    {
        // Le quadtree courant ne peux le contenir
        if (!inBoundaries(p))
            return null;

        for(QuadNode<T> node : nodes)
        {
            if(p.compare(node.getPos()))
                return node;
        }

        if(topLeftTree == null)
            return null;

        if(topLeftTree.inBoundaries(p))
           return topLeftTree.search(p);
        else if(topRightTree.inBoundaries(p))
            return topRightTree.search(p);
        else if(bottomLeftTree.inBoundaries(p))
            return bottomLeftTree.search(p);
        else if(bottomRightTree.inBoundaries(p))
            return bottomRightTree.search(p);
        else
            throw new RuntimeException("ERROR: Unhandled Partition !");
    }

    // Theta(1)
    private void divide()
    {
        int xOffset = minTopLeftCorner.getX() + (maxBottomRightCorner.getX() - minTopLeftCorner.getX()) / 2;
        int yOffset = minTopLeftCorner.getY() + (maxBottomRightCorner.getY() - minTopLeftCorner.getY()) / 2;

        topLeftTree = new QuadTree<>(new QuadPoint(minTopLeftCorner.getX(), minTopLeftCorner.getY()), new QuadPoint(xOffset, yOffset));
        bottomLeftTree = new QuadTree<>(new QuadPoint(minTopLeftCorner.getX(), yOffset), new QuadPoint(xOffset, maxBottomRightCorner.getY()));
        topRightTree = new QuadTree<>(new QuadPoint(xOffset, minTopLeftCorner.getY()), new QuadPoint(maxBottomRightCorner.getX(), yOffset));
        bottomRightTree = new QuadTree<>(new QuadPoint(xOffset, yOffset), new QuadPoint(maxBottomRightCorner.getX(), maxBottomRightCorner.getY()));
    }
}
