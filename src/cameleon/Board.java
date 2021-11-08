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
		for(int k = 0; k < size; k++)
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
				new QuadPoint(minX + Globals.ZONE_SIZE, minY + Globals.ZONE_SIZE), this);
	}


	private void updateColorReckless(int x, int y)
	{

	}

}