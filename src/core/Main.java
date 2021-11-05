package core;

import cameleon.Game;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

public class Main {

    public static void main(String[] args)
    {
        QuadTree<Integer> quad = new QuadTree<>(new QuadPoint(0,0), new QuadPoint(5,5));

        quad.insert(new QuadPoint(0,0), 1);
        quad.insert(new QuadPoint(1,1), 2);
        quad.insert(new QuadPoint(0,2), 3);
        quad.insert(new QuadPoint(1,2), 4);

        System.out.println("Node 1:" + quad.search(new QuadPoint(0,0)).getData());
        System.out.println("Node 2:" + quad.search(new QuadPoint(1,1)).getData());
        System.out.println("Node 3:" + quad.search(new QuadPoint(0,2)).getData());
        System.out.println("Node 4:" + quad.search(new QuadPoint(1,2)).getData());

        for(QuadTree<Integer> q : quad.queryRange(new QuadPoint(0,0), new QuadPoint(1,1)))
        {
            System.out.printf("Position: %s Data: %s\n", q.getPos(), q.getData());
        }
        System.out.println("Is in boundaries (no): " + quad.inBoundaries(new QuadPoint(12,12)));

       Game party = new Game();
       party.start();
    }
}
