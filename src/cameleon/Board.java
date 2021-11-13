package cameleon;

import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

public class Board {

	private final int size;
	private final int[][] squares;
	private final Game gameRef;

	private QuadTree<Region> regionQuadTree;

	public Board(int n, Game _gameRef)
	{
		size = (int) (Globals.ZONE_SIZE * Math.pow(2, n));
		squares = new int[size][size];
		gameRef = _gameRef;
		if(gameRef.getGameMode() == GameMode.RECKLESS)
			initQuadTree();
	}

	public Board(int _size, int[][] _squares, Game _gameRef)
	{
		size = _size;
		squares = _squares;
		gameRef = _gameRef;
		if(gameRef.getGameMode() == GameMode.RECKLESS)
			initQuadTree();
	}

	public boolean isFull()
	{
		return (gameRef.getPlayer1().getNumberSquare() + gameRef.getPlayer2().getNumberSquare()) == (size * size);
	}

	public void showGrid()
	{
		System.out.print("\t\t");
		for (int k = 0; k < size; k++)
			System.out.print("\t" + k + "\t");

		System.out.println();
		for(int i = 0; i < size; i++) //y
		{
			System.out.print("\t" + i + "\t");

			for(int j = 0; j < size; j++) //x
			{
				int squareId = squares[j][i];

				if(gameRef.getPlayer1().getPlayerId() == squareId)
					System.out.print(Globals.ANSI_RED +"\tR\t" + Globals.ANSI_RESET);
				else if(gameRef.getPlayer2().getPlayerId() == squareId)
					System.out.print(Globals.ANSI_BLUE + "\tB\t" + Globals.ANSI_RESET);
				else
					System.out.print("\t⊡\t");
			}
			System.out.println();
		}
	}

	public int getSize() {
		return size;
	}

	public int[][] getSquares() {	return squares;	}

	public boolean doesSquareExist(int x, int y)
	{
		return ((x >= 0) && (x < size) && (y >= 0) && (y < size));
	}

	// TO FIX: Maybe move it to another class ?? We'll decide later
	public void nextMove(int x, int y)//update color si la case est deja d'une couleur - check 8 cases autour
	{
		if (squares[x][y] == Globals.FREE_SQUARE)
		{
			squares[x][y] = gameRef.getCurrent().getPlayerId();
			gameRef.getCurrent().increaseNbSquare();
			if(gameRef.getGameMode() == GameMode.RECKLESS)
				updateColorReckless(x, y);
			else
				updateColorBrave(x, y);
		}
	}

	public Player getCurrentPlayer()
	{
		return gameRef.getCurrent();
	}

	public Player getNotCurrentPlayer()
	{
		return gameRef.getNotCurrent();
	}

	// ----------------- BRAVE -----------------
	private void updateColorBrave(int x, int y)
	{
		for (int i = x - 1; i <= x + 1; i++)
		{
			if(i < 0 || i >= size) continue;

			for (int j = y - 1; j <= y+ 1; j++)
			{
				if(j < 0 || j >= size) continue;

				if(squares[i][j] != Globals.FREE_SQUARE)
				{
					if(squares[i][j] == gameRef.getNotCurrent().getPlayerId())
					{
						gameRef.getNotCurrent().decreaseNbSquare();
						gameRef.getCurrent().increaseNbSquare();
						squares[i][j] = gameRef.getCurrent().getPlayerId();
					}
				}
			}
		}
	}

	// ----------------- RECKLESS -----------------
	private void initQuadTree()
	{
		int regionAmount = (size / Globals.ZONE_SIZE) - 1;
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
		int minX = i*Globals.ZONE_SIZE;
		int minY = j*Globals.ZONE_SIZE;
		return new Region(new QuadPoint(minX,minY),
				new QuadPoint(minX + Globals.ZONE_SIZE - 1, minY + Globals.ZONE_SIZE - 1), this);
	}

	// x et y correspondent à la position du point pour lequel on veut savoir la région
	private QuadPoint getRegionPos(int x, int y)
	{
		return new QuadPoint(x / Globals.ZONE_SIZE, y / Globals.ZONE_SIZE);
	}

	private void updateColorReckless(int x, int y)
	{
		Region region = regionQuadTree.search(getRegionPos(x, y)).getData();
		updateColor(x,y, region);

		region.increaseSquareTaken(); // will also check if the region is full

		if(region.isOwnedBy() == getCurrentPlayer().getPlayerId())
			checkRegionAcquired(getRegionPos(x, y), regionQuadTree);
	}

	// Non Recursive Version
	private void updateColor(int x, int y, Region region)
	{
		for (int i = x - 1; i <= x + 1; i++)
		{
			if(i < 0 || i >= size) continue;

			for (int j = y - 1; j <= y+ 1; j++)
			{
				if(j < 0 || j >= size) continue;

				if(squares[i][j] != Globals.FREE_SQUARE)
				{
					if(squares[i][j] == gameRef.getNotCurrent().getPlayerId())
					{
						if(region.isIn(i,j)) {
							gameRef.getNotCurrent().decreaseNbSquare();
							gameRef.getCurrent().increaseNbSquare();
							squares[i][j] = gameRef.getCurrent().getPlayerId();
						} else {
							Region region1 = regionQuadTree.search(getRegionPos(i, j)).getData();
							if(!region1.isFull()) {
								gameRef.getNotCurrent().decreaseNbSquare();
								gameRef.getCurrent().increaseNbSquare();
								squares[i][j] = gameRef.getCurrent().getPlayerId();
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
				if(nodes.getData().isOwnedBy() == gameRef.getCurrent().getPlayerId())
					countAcquiredByPlayer++;
			}
		}

		System.out.printf("Acquired: %d Acquired By The Player: %d\n", countAcquired, countAcquiredByPlayer);
		// Si 4 alors ça veut dire qu'il y a 2/2 sinon il gagne tout avec 3
		if((countAcquiredByPlayer >= 2 && countAcquired >= 4) || countAcquiredByPlayer >= 3)
		{
			qt.setData(createUpperRegion(qt));
			qt.getData().setSquareTaken(qt.getData().getMaxSquareInside());
			System.out.printf("UPPER REGION TL: %s BR: %s\n", qt.getData().getTopLeft(), qt.getData().getBottomRight());

			//qt.getData().changeRegionColor();
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

}