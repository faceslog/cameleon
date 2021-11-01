package core;

import cameleon.Game;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;
import cameleon.enums.CaseColor;
import core.datastruct.QuadNode;

public class Main {

    public static void main(String[] args)
    {
        QuadTree<CaseColor> quad = new QuadTree<>(new QuadPoint(0,0), new QuadPoint(5,5));

        QuadNode<CaseColor> node0 = new QuadNode<>(new QuadPoint(0,0), CaseColor.BLUE);
        QuadNode<CaseColor> node1 = new QuadNode<>(new QuadPoint(1,0), CaseColor.RED);
        QuadNode<CaseColor> node2 = new QuadNode<>(new QuadPoint(2,0), CaseColor.BLUE);
        QuadNode<CaseColor> node3 = new QuadNode<>(new QuadPoint(3,0), CaseColor.BLUE);
        QuadNode<CaseColor> node4 = new QuadNode<>(new QuadPoint(4,0), CaseColor.RED);
        QuadNode<CaseColor> node5 = new QuadNode<>(new QuadPoint(5,0), CaseColor.BLUE);

        quad.insert(node0);
        quad.insert(node1);
        quad.insert(node2);
        quad.insert(node3);
        quad.insert(node4);
        quad.insert(node5);

        System.out.println("Node 0:" + quad.search(new QuadPoint(0,0)).getData());
        System.out.println("Node 1:" + quad.search(new QuadPoint(1,0)).getData());
        System.out.println("Node 2:" + quad.search(new QuadPoint(2,0)).getData());
        System.out.println("Node 3:" + quad.search(new QuadPoint(3,0)).getData());
        System.out.println("Node 4:" + quad.search(new QuadPoint(4,0)).getData());
        System.out.println("Node 5:" + quad.search(new QuadPoint(5,0)).getData());
        System.out.println("Is in boundaries (no): " + quad.inBoundaries(new QuadPoint(12,12)));

        new Game();
    }
}
