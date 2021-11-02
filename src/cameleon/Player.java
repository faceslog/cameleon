package cameleon;

import cameleon.enums.CaseColor;
import cameleon.enums.GameMode;

public abstract class Player {

	private int score;
	private String name;
	private CaseColor color;
	private GameMode gameMode;

	/**
	 *
	 * @param _name
	 * @param _color
	 */
	public Player(String _name, CaseColor _color, GameMode _gameMode)
	{
		score = 0;
		name = _name;
		color = _color;
		gameMode = _gameMode;
	}

	public abstract void move(Board board);

	public void setScore(int score) {
		this.score = score;
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