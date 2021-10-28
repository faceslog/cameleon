package Core;

import Core.DataStructures.QuadNode;
import Core.DataStructures.QuadPoint;
import Core.DataStructures.QuadTree;
import Core.Enums.CaseColor;

public class Main {

    public static void main(String[] args)
    {
        QuadTree quad = new QuadTree(new QuadPoint(0,0), new QuadPoint(11,11));

        QuadNode node1 = new QuadNode(new QuadPoint(0,1), CaseColor.Blue);
        QuadNode node2 = new QuadNode(new QuadPoint(8,10), CaseColor.Red);
        QuadNode node3 = new QuadNode(new QuadPoint(11,11), CaseColor.Blue);

        quad.insert(node1);
        quad.insert(node2);
        quad.insert(node3);

        System.out.println("Node 1:" + quad.search(new QuadPoint(0,1)).getColor());
        System.out.println("Node 2:" + quad.search(new QuadPoint(8,10)).getColor());
        System.out.println("Node 3:" + quad.search(new QuadPoint(11,11)).getColor());
        System.out.println("Is in boundaries (no): " + quad.inBoundaries(new QuadPoint(12,12)));
    }
}
