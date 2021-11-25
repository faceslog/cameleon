package cameleon;

import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

abstract public class Board {

	private final int size;
	private final int[][] squares;
	private QuadTree<Region> regionQuadTree;
	private final Game gameRef;

	public Board(int n, Game _gameRef)
	{
		size = (int) (Config.ZONE_SIZE * Math.pow(2, n));
		squares = new int[size][size];
		gameRef = _gameRef;
		initQuadTree();
	}

	public Board(int _size, int[][] _squares, Game _gameRef)
	{
		size = _size;
		squares = _squares;
		gameRef = _gameRef;
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
					System.out.print(Config.ANSI_RED +"\tR\t" + Config.ANSI_RESET);
				else if(gameRef.getPlayer2().getPlayerId() == squareId)
					System.out.print(Config.ANSI_BLUE + "\tB\t" + Config.ANSI_RESET);
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

	public QuadTree<Region> getRegionQuadTree() { return regionQuadTree; }

	public Game getGameRef() { return gameRef; }

	public boolean doesSquareExist(int x, int y)
	{
		return ((x >= 0) && (x < size) && (y >= 0) && (y < size));
	}

	public void nextMove(int x, int y)//update color si la case est deja d'une couleur - check 8 cases autour
	{
		if (squares[x][y] == Config.FREE_SQUARE)
		{
			squares[x][y] = gameRef.getCurrent().getPlayerId();
			gameRef.getCurrent().increaseNbSquare();
			updateColor(x, y);
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

	// ------ QUADTREE ------
	private void initQuadTree()
	{
		int regionAmount = (size / Config.ZONE_SIZE) - 1;
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

	// x et y correspondent à la position du point pour lequel on veut savoir la région
	protected QuadPoint getRegionPosIncluding(int x, int y)
	{
		return new QuadPoint(x / Config.ZONE_SIZE, y / Config.ZONE_SIZE);
	}

	abstract protected void updateColor(int x, int y);

}