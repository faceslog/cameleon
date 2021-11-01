package core.datastruct;

import java.util.ArrayList;

public class QuadTree<T>
{
    final int MAX_CAPACITY = 4;
    ArrayList<QuadNode<T>> nodes;

    // Boundaries
    private final QuadPoint topLeft; // xMin, yMin
    private final QuadPoint bottomRight; // xMax, yMax

    // Enfants de cet arbre
    private QuadTree<T> topLeftTree;
    private QuadTree<T> topRightTree;
    private QuadTree<T> bottomLeftTree;
    private QuadTree<T> bottomRightTree;

    public QuadTree(QuadPoint _topLeft, QuadPoint _bottomRight)
    {
        topLeft = _topLeft;
        bottomRight = _bottomRight;
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
            return (point.getX() >= topLeft.getX() && point.getX() <= bottomRight.getX() &&
                    point.getY() >= topLeft.getY() && point.getY() <= bottomRight.getY());
    }

    // O(log n)
    public void insert(QuadNode<T> node)
    {
        if(node == null)
            return;

       if(!inBoundaries(node.getPos()))
           return;

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
       // else ERROR Unhandled Partition
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
            topLeftTree.search(p);
        else if(topRightTree.inBoundaries(p))
            topRightTree.search(p);
        else if(bottomLeftTree.inBoundaries(p))
            bottomLeftTree.search(p);
        else if(bottomRightTree.inBoundaries(p))
            bottomRightTree.search(p);
        //else ERROR Unhandled Partition
        return null;
    }

    // Theta(1)
    private void divide()
    {
        int xOffset = topLeft.getX() + (bottomRight.getX() - topLeft.getX()) / 2;
        int yOffset = topLeft.getY() + (bottomRight.getY() - topLeft.getY()) / 2;

        topLeftTree = new QuadTree<>(new QuadPoint(topLeft.getX(), topLeft.getY()), new QuadPoint(xOffset, yOffset));
        bottomLeftTree = new QuadTree<>(new QuadPoint(topLeft.getX(), yOffset), new QuadPoint(xOffset, bottomRight.getY()));
        topRightTree = new QuadTree<>(new QuadPoint(xOffset, topLeft.getY()), new QuadPoint(xOffset, yOffset));
        bottomRightTree = new QuadTree<>(new QuadPoint(xOffset, yOffset), new QuadPoint(bottomRight.getX(), bottomRight.getY()));
    }
}
