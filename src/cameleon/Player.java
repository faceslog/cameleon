package cameleon;

import core.datastruct.QuadPoint;

public abstract class Player {

	private final int playerId;
	private final Game gameRef;
	private int numberSquare;

	public Player(int _playerId, Game _game)
	{
		playerId = _playerId;
		gameRef = _game;
		numberSquare = 0;
	}

	public abstract QuadPoint move();

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

}