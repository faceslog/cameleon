package cameleon;

import cameleon.enums.CaseColor;

public class Player {

	private int score;
	private String name;
	private CaseColor color;

	/**
	 *
	 * @param _name
	 * @param _color
	 */
	public Player(String _name, CaseColor _color) {
		score = 0;
		name = _name;
		color = _color;
	}

	public void move(Board board) {
		// TODO - implement Player.move
		throw new UnsupportedOperationException();
	}

	public int getScore() {
		return score;
	}

	public CaseColor getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}