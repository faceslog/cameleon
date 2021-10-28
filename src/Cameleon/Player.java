package Cameleon;

public class Player {

	private int score;
	private String name;
	private Color color;

	/**
	 * 
	 * @param c
	 * @param name
	 */
	public Player(Color c, String name) {
		this.score = 0;
		this.color = c;
		this.name = name;
	}

	public void move(Board board) {
		// TODO - implement Player.move
		throw new UnsupportedOperationException();
	}

	public int getScore() {
		return score;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}