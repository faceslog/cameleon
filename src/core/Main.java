package core;

import cameleon.Game;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;
import cameleon.enums.CaseColor;
import core.datastruct.QuadNode;

public class Main {

    public static void main(String[] args)
    {
        QuadTree<CaseColor> quad = new QuadTree<>(new QuadPoint(0,0), new QuadPoint(11,11));

        QuadNode<CaseColor> node1 = new QuadNode<>(new QuadPoint(0,0), CaseColor.BLUE);
        QuadNode<CaseColor> node2 = new QuadNode<>(new QuadPoint(1,1), CaseColor.RED);
        QuadNode<CaseColor> node3 = new QuadNode<>(new QuadPoint(11,11), CaseColor.BLUE);

        quad.insert(node1);
        quad.insert(node2);
        quad.insert(node3);

        System.out.println("Node 1:" + quad.search(new QuadPoint(0,0)).getData());
        System.out.println("Node 2:" + quad.search(new QuadPoint(1,1)).getData());
        System.out.println("Node 3:" + quad.search(new QuadPoint(11,11)).getData());
        System.out.println("Is in boundaries (no): " + quad.inBoundaries(new QuadPoint(12,12)));

       // new Game();
    }
}
