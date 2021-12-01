package core;

import cameleon.Game;
import core.datastruct.DataManager;
import core.datastruct.QuadPoint;

public class Main {

    public static void main(String[] args)
    {
        DataManager<QuadPoint> ds = new DataManager<>();

        ds.add(new QuadPoint(0,1));
        ds.add(new QuadPoint(0,2));
        ds.add(new QuadPoint(0,3));

        System.out.println(ds.search(new QuadPoint(0,1)));

        ds.remove(new QuadPoint(0,2));

        QuadPoint point = new QuadPoint(0,4);
        ds.add(point);
        System.out.println(ds.search(point));

        System.out.println(ds.getRandom());
    }
}
