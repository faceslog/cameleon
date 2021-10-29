package Cameleon;

import Cameleon.Board;
import Cameleon.Enums.CaseColor;

public class Player {

	private int score;
	private String name;
	private CaseColor color;

	/**
	 * 
	 * @param c
	 * @param name
	 */
	public Player(CaseColor c, String name) {
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

	public CaseColor getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}