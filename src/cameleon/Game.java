package cameleon;

import cameleon.boards.BBrave;
import cameleon.boards.BReckless;
import cameleon.entities.Bot;
import cameleon.entities.Human;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	private final Player player1;
	private final Player player2;
	private final GameMode gameMode;

	private Player current;
	private Board board;

	public Game(int size, GameMode _gameMode)
	{
		player1 = new Human(1, this);
		player2 = new Bot(2, this);
		current = player1;
		gameMode = _gameMode;
		if(gameMode == GameMode.BRAVE)
			board = new BBrave(size, this);
		else
			board = new BReckless(size, this);
	}

	public Game(String path, GameMode _gameMode)
	{
		player1 = new Human(1, this);
		player2 = new Bot(2, this);
		current = player1;
		gameMode = _gameMode;
		loadBoardFromFile(path);
	}

	public Player getCurrent() {
		return current;
	}

	public Player getEnemy()
	{
		return current.equals(player1) ? player2 : player1;
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public Player getPlayer2()
	{
		return player2;
	}

	public void setCurrent(int playerId) {

		if(playerId == player1.getPlayerId())
			current = player1;
		else
			current = player2;
	}

	public void changeCurrent()
	{
		current = getEnemy();
	}

	public Board getBoard() {
		return board;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public boolean isThereBotPlayers()
	{
		return (player1 instanceof Bot) || (player2 instanceof Bot);
	}

	public void start()
	{
		board.showGrid();
		System.out.println();

		while (!board.isFull())
		{
			System.out.println(Config.GetANSI(current.getPlayerId()) + "########################## PlayerId: " + current.getPlayerId() + " ##########################" + Config.ANSI_RESET);
			current.move();
			board.showGrid();
			System.out.println(Config.GetANSI(current.getPlayerId()) + "COULEUR : "  + current.getNumberSquare() + Config.ANSI_RESET);
			System.out.println("TOTAL : "  + getScore());
			changeCurrent();
			System.out.println();
		}

		stop();
	}

	public void stop()
	{
		Player winner = getWinner();

		if(winner.equals(player1))
			System.out.printf(Config.GetANSI(player1.getPlayerId()) + "Player %s wins! ", player1.getPlayerId() + Config.ANSI_RESET);
		else if (winner.equals(player2))
			System.out.printf(Config.GetANSI(player2.getPlayerId())+ "Player %s wins! ", player2.getPlayerId() + Config.ANSI_RESET);
		else
			System.out.println("NO WINNER");
	}

	public Player getWinner() {
		if(player1.getNumberSquare() > player2.getNumberSquare())
			return player1;
		else if (player1.getNumberSquare() < player2.getNumberSquare())
			return player2;
		else
			return null;
	}

	public int getScore() {
		return player1.getNumberSquare() + player2.getNumberSquare();
	}

	private void loadBoardFromFile(String path)
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
		ArrayList<QuadPoint> taken = new ArrayList<>();

		int i = 0;
		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			char[] ch = str.toCharArray();
			if (ch.length > 0)
			{
				for(int j = 0; j < size; j++) {
					switch (ch[j])
					{
						case 'R' -> {
							squares[j][i] = player1.getPlayerId();
							player1.increaseNbSquare();
							taken.add(new QuadPoint(j, i));
						}
						case 'B' -> {
							squares[j][i] = player2.getPlayerId();
							player2.increaseNbSquare();
							taken.add(new QuadPoint(j, i));
						}
						default -> squares[j][i] = Config.FREE_SQUARE;
					}
				}
			}
			i++;
		}

		if(gameMode == GameMode.BRAVE)
			board = new BBrave(size, squares, this, taken);
		else
			board = new BReckless(size, squares, this, taken);
	}
}