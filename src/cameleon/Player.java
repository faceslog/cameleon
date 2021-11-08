package cameleon;

public abstract class Player {

	private final int playerId;
	private final Game gameRef;
	private int numberSquare;

	// TO DO: throw if id = Globals.FREE_SQUARE
	public Player(int _playerId, Game _game)
	{
		playerId = _playerId;
		gameRef = _game;
		numberSquare = 0;
	}

	public abstract void move();

	public int getNumberSquare() {
		return numberSquare;
	}

	public void setNumberSquare(int _numberSquare) {
		numberSquare = _numberSquare;
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