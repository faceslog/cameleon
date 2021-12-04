package cameleon.boards;

import cameleon.*;
import cameleon.entities.Bot;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

import java.util.ArrayList;

public class BReckless extends Board
{
    private QuadTree<Region> regionQuadTree;

    public BReckless(int n, Game _gameRef)
    {
        super(n, _gameRef);
        initQuadTree();
    }

    public BReckless(int _size, int[][] _squares, Game _gameRef, ArrayList<QuadPoint> taken)
    {
        super(_size, _squares, _gameRef);
        initQuadTree();

        if(_gameRef.isThereBotPlayers())
        {
            for(QuadPoint point : taken)
            {
                // On actualise la région contenant ce point
                Region region = regionQuadTree.search(getRegionPosIncluding(point.getX(), point.getY())).getData();
                region.increaseSquareTakenOnly();

                // Pour chaque point pris on évalue les points libres autours
                for (int i = point.getX() - 1; i <= point.getX() + 1; i++)
                {
                    if (i < 0 || i >= getSize())
                        continue;

                    for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {

                        if (j < 0 || j >= getSize())
                            continue;

                        if(getSquares()[i][j] == Config.FREE_SQUARE)
                        {
                            if(getGameRef().getPlayer1() instanceof Bot player1)
                                player1.getFreePoints().add(new QuadPoint(i, j));

                            if(getGameRef().getPlayer2() instanceof Bot player2)
                                player2.getFreePoints().add(new QuadPoint(i, j));
                        }
                    }
                }

                if(region.isFull())
                {
                    _gameRef.setCurrent(getSquares()[point.getX()][point.getY()]);
                    checkRegionAcquired(getRegionPosIncluding(point.getX(), point.getY()), regionQuadTree);
                    _gameRef.changeCurrent();
                }
            }
        }
    }

    @Override
    public void updateColor(int x, int y)
    {
        Region region = regionQuadTree.search(getRegionPosIncluding(x, y)).getData();
        updateColorReckless(x,y, region);

        region.increaseSquareTaken(); // will also check if the region is full

        if(region.isOwnedBy() == getCurrent().getPlayerId())
            checkRegionAcquired(getRegionPosIncluding(x, y), regionQuadTree);
    }

    public int countColorReckless(int x, int y)
    {
        int inside = 0;
        int around = 0;

        Region region = regionQuadTree.search(getRegionPosIncluding(x, y)).getData();

        for (int i = x - 1; i <= x + 1; i++)
        {
            if(i < 0 || i >= getSize()) continue;
            for (int j = y - 1; j <= y + 1; j++)
            {
                if(j < 0 || j >= getSize()) continue;

                if(getSquares()[i][j] != Config.FREE_SQUARE)
                {
                    if(getSquares()[i][j] == getEnemy().getPlayerId())
                    {
                        if(region.include(i,j))
                            inside++;
                        else
                        {
                            Region regionBis = regionQuadTree.search(getRegionPosIncluding(i, j)).getData();
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
            // le point du joueur est inclus dans la zone que l'on vient de capturer
            int upperRegion = countRegionAcquired(getRegionPosIncluding(x, y), regionQuadTree); // grande zone A forcément supérieur a B
            int smallRegion = region.countChangeRegionColor();

            if(upperRegion > 0)
                return upperRegion + around; // On a capturé la grande zone (la grande region inclus la petite)
            else
                return smallRegion + around; // On a seulement capturé la petite zone
        }
        else
        {
            // On aura capturé aucune zone
            return (inside + around) + 1; // + 1, car on compte le point du joueur en plus dans ce cas
        }
    }

    // x et y correspondent à la position du point pour lequel on veut savoir la région
    public QuadPoint getRegionPosIncluding(int x, int y)
    {
        return new QuadPoint(x / Config.ZONE_SIZE, y / Config.ZONE_SIZE);
    }

    public Region getRegionOfPoint(int x, int y) {
        return regionQuadTree.search(getRegionPosIncluding(x, y)).getData();
    }

    private void initQuadTree()
    {
        int regionAmount = (getSize() / Config.ZONE_SIZE) - 1;
        regionQuadTree = new QuadTree<>( new QuadPoint(0,0), new QuadPoint(regionAmount,regionAmount));
        for(int i = 0; i <= regionAmount; i++)
        {
            for(int j = 0; j <= regionAmount; j++)
            {
                QuadPoint pos = new QuadPoint(i, j);
                regionQuadTree.insert(pos, createRegion(i, j));
            }
        }
    }

    private Region createRegion(int i, int j)
    {
        int minX = i* Config.ZONE_SIZE;
        int minY = j* Config.ZONE_SIZE;
        return new Region(new QuadPoint(minX,minY),
                new QuadPoint(minX + Config.ZONE_SIZE - 1, minY + Config.ZONE_SIZE - 1), this);
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
                    if(getSquares()[i][j] == getEnemy().getPlayerId())
                    {
                        if(region.include(i,j))
                        {
                            getEnemy().decreaseNbSquare();
                            getCurrent().increaseNbSquare();
                            getSquares()[i][j] = getCurrent().getPlayerId();
                        }
                        else
                        {
                            Region region1 = regionQuadTree.search(getRegionPosIncluding(i, j)).getData();
                            if(!region1.isFull()) {
                                getEnemy().decreaseNbSquare();
                                getCurrent().increaseNbSquare();
                                getSquares()[i][j] = getCurrent().getPlayerId();
                            }
                        }
                    }
                }
                else
                {
                    // Cette case libre peut très bien permettre de récupérer une région en entière par la suite nous
                    // devons donc la stocker pour le current également
                    if(getCurrent() instanceof Bot current)
                        current.getFreePoints().add(new QuadPoint(i, j));

                    if(getEnemy() instanceof Bot enemy)
                        enemy.getFreePoints().add(new QuadPoint(i, j));
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
            if(quadTree.getData().isOwnedBy() != getCurrent().getPlayerId())
                quadTree.getData().changeRegionColor();
        }
    }

    private Region createUpperRegion(QuadTree<Region> qt)
    {
        if (qt == null)
            throw new NullPointerException("qt parameter cannot be null !");

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
        if(qt == null)
            return false;

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
        if(qt == null)
            return false;

        if(!qt.inBoundaries(point))
            return false;

        int xOffset = (qt.getTopLeft().getX() + qt.getBottomRight().getX()) >> 1;
        int yOffset = (qt.getTopLeft().getY() + qt.getBottomRight().getY()) >> 1;
        short index = regionQuadTree.getRegionIndex(point, new QuadPoint(xOffset, yOffset));

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

    // ---------------------------- REGION COUNTER ----------------------------
    // Pour des raisons de lisibilité, nous avons décidé de ne pas fusionner les fonctions update et de comptage
    // bien que cela pourrait être simplement accompli à l'aide d'un booléen isUpdating.

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
            if(quadTree.getData().isOwnedBy() != getCurrent().getPlayerId())
                return quadTree.getData().countChangeRegionColor();
        }

        return  0;
    }

    private int countRegion(QuadTree<Region> qt)
    {
        if(qt == null)
            return 0;

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
        if(qt == null)
            return 0;

        if(!qt.inBoundaries(point))
            return 0;

        int xOffset = (qt.getTopLeft().getX() + qt.getBottomRight().getX()) >> 1;
        int yOffset = (qt.getTopLeft().getY() + qt.getBottomRight().getY()) >> 1;
        short index = regionQuadTree.getRegionIndex(point, new QuadPoint(xOffset, yOffset));

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
