package cameleon.entities;

import cameleon.Board;
import cameleon.Player;
import cameleon.enums.CaseColor;
import cameleon.enums.GameMode;

public class Bot extends Player {

	/**
	 * @param _name
	 * @param _color
	 */
	public Bot(String _name, CaseColor _color, GameMode _gameMode) {
		super(_name, _color, _gameMode);
	}

	@Override
	public void move(Board board) {
		// TODO - implement Bot.move
		throw new UnsupportedOperationException();
	}

	public void jouerGloutonBrave() {
		// TODO - implement Bot.jouerGloutonBrave
		throw new UnsupportedOperationException();
	}

	public void jouerGloutonTemeraire() {
		// TODO - implement Bot.jouerGloutonTemeraire
		throw new UnsupportedOperationException();
	}

	public void jouerIATemeraire() {
		// TODO - implement Bot.jouerIATemeraire
		throw new UnsupportedOperationException();
	}

	public void evalCaseBrave() {
		// TODO - implement Bot.evalCaseBrave
		throw new UnsupportedOperationException();
	}

	public void evalCaseTemeraire() {
		// TODO - implement Bot.evalCaseTemeraire
		throw new UnsupportedOperationException();
	}

}