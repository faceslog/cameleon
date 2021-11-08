package cameleon;

import cameleon.entities.Bot;
import cameleon.entities.Human;
import cameleon.enums.GameMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

	private Player Player1;
	private Player Player2;
	private Player current;
	private Board board;
	private GameMode gameMode;

	public Game()
	{
		init();
	}

	public Player getCurrent() {
		return current;
	}

	public Player getNotCurrent()
	{
		return current.equals(Player1) ? Player2 : Player1;
	}

	public Board getBoard() {
		return board;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public Player getPlayer1()
	{
		return Player1;
	}

	public Player getPlayer2()
	{
		return Player2;
	}

	public void start()
	{
		board.showGrid();
		System.out.println();

		while (!board.isFull())
		{
			System.out.println(Globals.GetANSI(current.getPlayerId()) + "########################## PlayerId: " + current.getPlayerId() + " ##########################" + Globals.ANSI_RESET);
			current.move();
			board.showGrid();
			System.out.println(Globals.GetANSI(current.getPlayerId()) + "COULEUR : "  + current.getNumberSquare() + Globals.ANSI_RESET);
			System.out.println("TOTAL : "  + (current.getNumberSquare() + getNotCurrent().getNumberSquare()));
			changeCurrent();
			System.out.println();
		}

		stop();
	}

	public void stop()
	{
		if(Player1.getNumberSquare() > Player2.getNumberSquare())
			System.out.printf(Globals.GetANSI(Player1.getPlayerId()) + "Player %d wins! ", Player1.getPlayerId() + Globals.ANSI_RESET);
		else if (Player1.getNumberSquare() < Player2.getNumberSquare())
			System.out.printf(Globals.GetANSI(Player2.getPlayerId())+ "Player %d wins! ", Player2.getPlayerId() + Globals.ANSI_RESET);
		else
			System.out.println("NO WINNER");
	}

	// Private methods
	private void init()
	{
		gameMode = GameMode.BRAVE;
		Player1 = new Human(1, this);
		Player2 = new Bot(2, this);
		current = Player1;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Use file ? : ");
		boolean isUsingFile = scanner.nextBoolean(); //true or false dans la saisie

		if(isUsingFile)
		{
			board = loadBoardFromFile("./docs/test.txt");
		}
		else
		{
			System.out.println("Taille de la grid (n) : ");
			int size = scanner.nextInt();
			board = new Board(size, this);
		}
	}

	private void changeCurrent()
	{
		current = getNotCurrent();
	}

	private Board loadBoardFromFile(String path)
	{
		File file;
		Scanner sc;
		try {
			file = new File(path);
			sc = new Scanner(file);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
			throw new RuntimeException("Temporary Runtime Error File not Found Game.loadBoardFromFile()!!!");
		}

		int size = Integer.parseInt(sc.nextLine());
		int[][] squares = new int[size][size];

		int i = 0;
		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			char[] ch = str.toCharArray();

			for(int j = 0; j < size; j++) {
				switch (ch[j])
				{
					case 'R' -> {
						squares[j][i] = Player1.getPlayerId();
						Player1.increaseNbSquare();
					}
					case 'B' -> {
						squares[j][i] = Player2.getPlayerId();
						Player2.increaseNbSquare();
					}
					default -> squares[j][i] = Globals.FREE_SQUARE;
				}
			}
			i++;
		}

		return new Board(size, squares, this);
	}
}