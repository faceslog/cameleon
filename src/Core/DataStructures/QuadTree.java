package Core.DataStructures;

public class QuadTree
{
    /** ici topLeft Point(0.0)
     *    0  1  2    3  4  5    6  7  8    9  10 11  x/y
     *  | X  -- -- | -- -- -- | -- -- -- | -- -- -- | 0   Ce QuadTree Représente le plateau de jeu de l'ennoncé.
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 1   Nos points seront donc modifiés dans ces limites et on
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 2   devra diviser ce plateau en utilisant des PR-QuadTrees.
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 3
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 4
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 5
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 6
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 7
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 8
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 9
     *  | -- -- -- | -- -- -- | -- -- -- | -- -- -- | 10
     *  | -- -- -- | -- -- -- | -- -- -- | -- --  X | 11
     *                              ici bottomRight Point(11.11)
     */
    private final QuadPoint topLeft;
    private final QuadPoint bottomRight;

    // Details du node courant
    private QuadNode currentNode;

    // Enfants de cet arbre
    private QuadTree topLeftTree;
    private QuadTree topRightTree;
    private QuadTree bottomLeftTree;
    private QuadTree bottomRightTree;

    public QuadTree(QuadPoint _topLeft, QuadPoint _bottomRight)
    {
        topLeft = _topLeft;
        bottomRight = _bottomRight;
        currentNode = null;
        topLeftTree = null;
        topRightTree = null;
        bottomLeftTree = null;
        bottomRightTree = null;
    }

    public boolean inBoundaries(QuadPoint p)
    {
        if(p == null)
            return false;
        else
            return (p.getX() >= topLeft.getX() && p.getX() <= bottomRight.getX() &&
                    p.getY() >= topLeft.getY() && p.getY() <= bottomRight.getY());
    }

    public void insert(QuadNode node)
    {
        if(node == null)
            return;

        // Le quadtree ne peut contenir ce point
        if(!inBoundaries(node.getPos()))
            return;

        // Ce quad tree est divisé au maximum on ne peut plus le diviser
        if (Math.abs(topLeft.getX() - bottomRight.getX()) <= 1 && Math.abs(topLeft.getY() - bottomRight.getY()) <= 1)
        {
            if(currentNode == null)
                currentNode = node;
            return;
        }

        // On cherche à déterminer si l'on va à gauche grâce à x:
        if((topLeft.getX() + bottomRight.getX()) / 2 >= node.getPos().getX())
        {
            // On détermine selon y si l'on est en haut ou en bas
            // Arbre Top Gauche
            if((topLeft.getY() + bottomRight.getY()) / 2 >= node.getPos().getY())
            {
                if(topLeftTree == null)
                {
                    topLeftTree = new QuadTree(
                            new QuadPoint(topLeft.getX(), topLeft.getY()),
                            new QuadPoint((topLeft.getX() + bottomRight.getX()) / 2, (topLeft.getY() + bottomRight.getY()) / 2)
                    );
                }
                topLeftTree.insert(node);
            }
            else // Arbre Bottom Gauche
            {
                if(bottomLeftTree == null)
                {
                    bottomLeftTree = new QuadTree(
                            new QuadPoint(topLeft.getX(), (topLeft.getY() + bottomRight.getY()) / 2),
                            new QuadPoint((topLeft.getX() + bottomRight.getX()) / 2, bottomRight.getY())
                    );
                }
                bottomLeftTree.insert(node);
            }
        }
        else // On va à droite
        {
            // Arbre Top Right
            if((topLeft.getY() + bottomRight.getY()) / 2 >= node.getPos().getY())
            {
                if(topRightTree == null)
                {
                    topRightTree = new QuadTree(
                            new QuadPoint((topLeft.getX() + bottomRight.getX()) / 2, topLeft.getY()),
                            new QuadPoint(bottomRight.getX(), (topLeft.getY() + bottomRight.getY()) / 2)
                    );
                }
                topRightTree.insert(node);
            }
            else // Arbre Bottom Right
            {
                if(bottomRightTree == null)
                {
                    bottomRightTree = new QuadTree(
                            new QuadPoint((topLeft.getX() + bottomRight.getX()) / 2, (topLeft.getY() + bottomRight.getY()) / 2),
                            new QuadPoint(bottomRight.getX(), bottomRight.getY())
                    );
                }
                bottomRightTree.insert(node);
            }
        }
    }

    public QuadNode search(QuadPoint p)
    {
        // Le quadtree courant ne peux le contenir
        if (!inBoundaries(p))
            return null;

        if (currentNode != null)
            return currentNode;

        // Va t'on à gauche ou à droite ?
        // A gauche:
        if ((topLeft.getX() + bottomRight.getX()) / 2 >= p.getX())
        {
            // Top Gauche
            if ((topLeft.getY() + bottomRight.getY()) / 2 >= p.getY())
            {
                if (topLeftTree == null)
                    return null;
                else
                    return topLeftTree.search(p);
            }
            // Bottom Gauche
            else
            {
                if (bottomLeftTree == null)
                    return null;
                return bottomLeftTree.search(p);
            }
        }
        else // A droite:
        {
            // En haut à droite
            if ((topLeft.getY() + bottomRight.getY()) / 2 >= p.getY())
            {
                if (topRightTree == null)
                    return null;
                else
                    return topRightTree.search(p);
            }
            // En bas à droite
            else
            {
                if (bottomRightTree == null)
                    return null;
                return bottomRightTree.search(p);
            }
        }
    }
}
