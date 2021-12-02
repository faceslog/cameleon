package cameleon;

import core.datastruct.DataManager;
import core.datastruct.QuadPoint;

public abstract class Player {

	private final int playerId;
	private final Game gameRef;
	private int numberSquare;

	private DataManager<QuadPoint> listPoints;

	// TO DO: throw if id = Config.FREE_SQUARE
	public Player(int _playerId, Game _game)
	{
		playerId = _playerId;
		gameRef = _game;
		numberSquare = 0;
		listPoints = new DataManager<>();
	}

	public abstract void move();

	public int getNumberSquare() {
		return numberSquare;
	}

	public void increaseNbSquare() { numberSquare++; }
	public void decreaseNbSquare() { numberSquare--; }

	public int getPlayerId() {
		return playerId;
	}

	public Game getGameRef() {
		return gameRef;
	}

	public boolean equals(Player player)
	{
		return playerId == player.getPlayerId();
	}

	public DataManager<QuadPoint> getListPoints() {
		return listPoints;
	}
}