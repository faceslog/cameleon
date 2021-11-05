package cameleon;

public abstract class Player {

	private final Integer playerId;
	private final Game gameRef;
	private int numberSquare;

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

	public void setNumberSquare(int numberSquare) {
		this.numberSquare = numberSquare;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public Game getGameRef() {
		return gameRef;
	}

	public boolean equals(Player player)
	{
		return playerId.equals(player.getPlayerId());
	}
}