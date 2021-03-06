package cameleon;

import cameleon.entities.Bot;
import core.datastruct.QuadPoint;

abstract public class Board {

	private final int size;
	private final int[][] squares;
	private final Game gameRef;

	public Board(int n, Game _gameRef)
	{
		size = (int) (Config.ZONE_SIZE * Math.pow(2, n));
		squares = new int[size][size];
		gameRef = _gameRef;
	}

	public Board(int _size, int[][] _squares, Game _gameRef)
	{
		size = _size;
		squares = _squares;
		gameRef = _gameRef;
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

	public Game getGameRef() { return gameRef; }

	public boolean doesSquareExist(int x, int y)
	{
		return ((x >= 0) && (x < size) && (y >= 0) && (y < size));
	}

	public void nextMove(int x, int y)//update color si la case est deja d'une couleur - check 8 cases autour
	{
		if (squares[x][y] == Config.FREE_SQUARE)
		{
			squares[x][y] = getCurrent().getPlayerId();
			getCurrent().increaseNbSquare();

			// Si ce point faisait partie de la liste des points libre pour l'ennemi également
			if(getEnemy() instanceof Bot enemy)
				enemy.getFreePoints().remove(new QuadPoint(x, y));

			updateColor(x, y);
		}
	}

	public Player getCurrent()
	{
		return gameRef.getCurrent();
	}

	public Player getEnemy()
	{
		return gameRef.getEnemy();
	}

	abstract public void updateColor(int x, int y);

}