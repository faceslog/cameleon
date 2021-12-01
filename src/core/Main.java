package core;

import cameleon.Game;
import core.datastruct.DataManager;
import core.datastruct.QuadPoint;

public class Main {

    public static void main(String[] args)
    {
        DataManager<QuadPoint> ds = new DataManager<>();

        ds.add(new QuadPoint(0,1));
        ds.add(new QuadPoint(1,0));
        ds.add(new QuadPoint(2,1));

        System.out.println(ds.search(new QuadPoint(0,1)));
        System.out.println(ds.search(new QuadPoint(1,0)));
        System.out.println(ds.search(new QuadPoint(2,1)));


        Game party = new Game();
        party.start();
    }
}
