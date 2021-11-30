package cameleon.boardmodes;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;
import cameleon.Region;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

public class BReckless extends Board
{
    public BReckless(int n, Game _gameRef)
    {
        super(n, _gameRef);
    }

    public BReckless(int _size, int[][] _squares, Game _gameRef)
    {
        super(_size, _squares, _gameRef);
    }

    @Override
    protected void updateColor(int x, int y)
    {
        Region region = getRegionQuadTree().search(getRegionPosIncluding(x, y)).getData();
        updateColorReckless(x,y, region);

        region.increaseSquareTaken(); // will also check if the region is full

        if(region.isOwnedBy() == getCurrentPlayer().getPlayerId())
            checkRegionAcquired(getRegionPosIncluding(x, y), getRegionQuadTree());
    }

    // Non Recursive Version
    private void updateColorReckless(int x, int y, Region region)
    {
        for (int i = x - 1; i <= x + 1; i++)
        {
            if(i < 0 || i >= getSize()) continue;

            for (int j = y - 1; j <= y+ 1; j++)
            {
                if(j < 0 || j >= getSize()) continue;

                if(getSquares()[i][j] != Config.FREE_SQUARE)
                {
                    if(getSquares()[i][j] == getGameRef().getNotCurrent().getPlayerId())
                    {
                        if(region.include(i,j)) {
                            getGameRef().getNotCurrent().decreaseNbSquare();
                            getGameRef().getCurrent().increaseNbSquare();
                            getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                        } else {
                            Region region1 = getRegionQuadTree().search(getRegionPosIncluding(i, j)).getData();
                            if(!region1.isFull()) {
                                getGameRef().getNotCurrent().decreaseNbSquare();
                                getGameRef().getCurrent().increaseNbSquare();
                                getSquares()[i][j] = getGameRef().getCurrent().getPlayerId();
                            }
                        }
                    }
                }
            }
        }
    }

    // ---------------------------- REGION COLOR MANAGEMENT ----------------------------
    private void changeRegionColor(QuadTree<Region> quadTree)
    {
        if(quadTree == null)
            return;

        if(quadTree.getNodes() != null)
        {
            for (QuadTree<Region> qt : quadTree.getNodes())
                changeRegionColor(qt);

            return;
        }

        if(quadTree.getData() != null)
        {
            if(quadTree.getData().isOwnedBy() != getCurrentPlayer().getPlayerId())
                quadTree.getData().changeRegionColor();
        }
    }

    private Region createUpperRegion(QuadTree<Region> qt)
    {
        if(qt.getNodes().get(QuadTree.TOP_LEFT).getData() == null || qt.getNodes().get(QuadTree.BOTTOM_RIGHT).getData() == null)
        {
            Region tL = createUpperRegion(qt.getNodes().get(QuadTree.TOP_LEFT));
            Region bR = createUpperRegion(qt.getNodes().get(QuadTree.BOTTOM_RIGHT));
            return new Region(tL.getTopLeft(), bR.getBottomRight(), this);
        }
        else
        {
            return new Region(qt.getNodes().get(QuadTree.TOP_LEFT).getData().getTopLeft(), qt.getNodes().get(QuadTree.BOTTOM_RIGHT).getData().getBottomRight(), this);
        }
    }

    private boolean isLastSubZone(int countAcquiredByPlayer, int countAcquired)
    {
        // 2 = (QuadTree.MAX_CAPACITY / 2)
        return (countAcquiredByPlayer >= 2 && countAcquired >= QuadTree.MAX_CAPACITY);
    }

    private boolean colorRegion(QuadTree<Region> qt)
    {
        if(qt.isEmpty())
            return false;

        int countAcquired = 0;
        int countAcquiredByPlayer = 0;

        for(QuadTree<Region> nodes : qt.getNodes())
        {
            if(nodes.getData() != null)
            {
                if(nodes.getData().isFull())
                    countAcquired++;
                if(nodes.getData().isOwnedBy() == getGameRef().getCurrent().getPlayerId())
                    countAcquiredByPlayer++;
            }
        }

        System.out.printf("Acquired: %d Acquired By The Player: %d\n", countAcquired, countAcquiredByPlayer);

        if(isLastSubZone(countAcquiredByPlayer, countAcquired)|| countAcquiredByPlayer >= Config.RKL_SUB_ZONE_TO_EARN)
        {
            qt.setData(createUpperRegion(qt));
            qt.getData().setSquareTaken(qt.getData().getMaxSquareInside());
            System.out.printf("Upper Region tL: %s bR: %s\n", qt.getData().getTopLeft(), qt.getData().getBottomRight());

            changeRegionColor(qt);

            return true;
        }

        return false;
    }

    private boolean checkRegionAcquired(QuadPoint point, QuadTree<Region> qt)
    {
        if(!qt.inBoundaries(point))
            return false;

        int xOffset = (qt.getTopLeft().getX() + qt.getBottomRight().getX()) >> 1;
        int yOffset = (qt.getTopLeft().getY() + qt.getBottomRight().getY()) >> 1;
        short index = getRegionQuadTree().getRegionIndex(point, new QuadPoint(xOffset, yOffset));

        // If Region Node
        if(qt.getNodes().get(index).getTopLeft() != null)
        {
            if(checkRegionAcquired(point, qt.getNodes().get(index)))
                return colorRegion(qt);

            return false;
        }

        if(qt.getNodes().get(index).isEmpty())
            return false;

        return colorRegion(qt);
    }

    //
    // HELP ME
    // IM GONNA
    // DIE FROM THIS
    // SHIT
    // :(

    public int countColorRK(int x, int y)
    {
        int inside = 0;
        int around = 0;

        Region region = getRegionQuadTree().search(getRegionPosIncluding(x, y)).getData();

        for (int i = x - 1; i <= x + 1; i++)
        {
            if(i < 0 || i >= getSize()) continue;
            for (int j = y - 1; j <= y+ 1; j++)
            {
                if(j < 0 || j >= getSize()) continue;

                if(getSquares()[i][j] != Config.FREE_SQUARE)
                {
                    if(getSquares()[i][j] == getGameRef().getNotCurrent().getPlayerId())
                    {
                        if(region.include(i,j))
                            inside++;
                        else
                        {
                            Region regionBis = getRegionQuadTree().search(getRegionPosIncluding(i, j)).getData();
                            if(!regionBis.isFull())
                                around++;
                        }
                    }
                }
            }
        }

        // Si la région devient pleine
        if((region.getSquareTaken() + 1) == region.getMaxSquareInside())
        {
            int a = countRegionAcquired(getRegionPosIncluding(x, y), getRegionQuadTree());
            int b = region.countChangeRegionColor();

            return Math.max(a, b) + around; // le point du joueur est inclus dans la zone à acquérir
        }
        else
            return (inside + around) + 1; // + 1 car on compte le point du joueur en plus dans ce cas
    }

    private int countChangeRegionColor(QuadTree<Region> quadTree)
    {
        if(quadTree == null)
            return 0;

        if(quadTree.getNodes() != null)
        {
            int count = 0;
            for (QuadTree<Region> qt : quadTree.getNodes())
                count +=  countChangeRegionColor(qt);

            return count;
        }

        if(quadTree.getData() != null)
        {
            if(quadTree.getData().isOwnedBy() != getCurrentPlayer().getPlayerId())
                return quadTree.getData().countChangeRegionColor();
        }

        return  0;
    }

    private int countRegion(QuadTree<Region> qt)
    {
        if(qt.isEmpty())
            return 0;

        int countAcquired = 1; // Initialisé à "1" car on part du principe qu'une zone appartient deja au joueur, mais ses données n'ont pas été changé on ne regarde donc que les 3 autres restantes
        int countAcquiredByPlayer = 1;

        for(QuadTree<Region> nodes : qt.getNodes())
        {
            if(nodes.getData() != null)
            {
                if(nodes.getData().isFull())
                    countAcquired++;
                if(nodes.getData().isOwnedBy() == getGameRef().getCurrent().getPlayerId())
                    countAcquiredByPlayer++;
            }
        }

        if(isLastSubZone(countAcquiredByPlayer, countAcquired)|| (countAcquiredByPlayer >= Config.RKL_SUB_ZONE_TO_EARN))
        {
            int count = 0;
            count += countChangeRegionColor(qt);

            return count;
        }

        return 0;
    }

    private int countRegionAcquired(QuadPoint point, QuadTree<Region> qt)
    {
        if(!qt.inBoundaries(point))
            return 0;

        int xOffset = (qt.getTopLeft().getX() + qt.getBottomRight().getX()) >> 1;
        int yOffset = (qt.getTopLeft().getY() + qt.getBottomRight().getY()) >> 1;
        short index = getRegionQuadTree().getRegionIndex(point, new QuadPoint(xOffset, yOffset));

        // If Region Node
        if(qt.getNodes().get(index).getTopLeft() != null)
        {
            int count = countRegionAcquired(point, qt.getNodes().get(index));
            if(count > 0)
                return Math.max(count, countRegion(qt));

            return 0;
        }

        if(qt.getNodes().get(index).isEmpty())
            return 0;

        // Si dans la feuille
        return countRegion(qt);
    }
}
