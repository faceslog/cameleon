package Core;

import Cameleon.Game;
import Core.DataStructures.QuadPoint;
import Core.DataStructures.QuadTree;
import Cameleon.Enums.CaseColor;
import Core.DataStructures.QuadNode;

public class Main {

    public static void main(String[] args)
    {
        QuadTree<CaseColor> quad = new QuadTree<>(new QuadPoint(0,0), new QuadPoint(11,11));

        QuadNode<CaseColor> node1 = new QuadNode<>(new QuadPoint(0,1), CaseColor.Blue);
        QuadNode<CaseColor> node2 = new QuadNode<>(new QuadPoint(8,10), CaseColor.Red);
        QuadNode<CaseColor> node3 = new QuadNode<>(new QuadPoint(11,11), CaseColor.Blue);

        quad.insert(node1);
        quad.insert(node2);
        quad.insert(node3);

        System.out.println("Node 1:" + quad.search(new QuadPoint(0,1)).getData());
        System.out.println("Node 2:" + quad.search(new QuadPoint(8,10)).getData());
        System.out.println("Node 3:" + quad.search(new QuadPoint(11,11)).getData());
        System.out.println("Is in boundaries (no): " + quad.inBoundaries(new QuadPoint(12,12)));

        new Game();
    }
}
