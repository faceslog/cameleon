package cameleon.entities;

import cameleon.Config;
import cameleon.Game;
import cameleon.Player;
import core.datastruct.QuadPoint;

public class Bot extends Player {

	public Bot(int _playerId, Game _gameRef)
	{
		super(_playerId, _gameRef);
	}

	@Override
	public void move() {
		ChaserPlayStyleBrave();
	}

	// TO DO: FIX Complexité en regardant les cas ou l'on peut break
	private void ChaserPlayStyleBrave() {
		QuadPoint point = null;
		int max = 0;

		for(int i = 0; i < getGameRef().getBoard().getSize(); i++)
		{
			for(int j = 0; j < getGameRef().getBoard().getSize(); j++)
			{
				QuadPoint quadPoint = new QuadPoint(j,i);
				if(getGameRef().getBoard().getSquares()[j][i] == Config.FREE_SQUARE)
				{
					int evalCase = evaluateMoveBrave(quadPoint);
					if(max < evalCase)
					{
						point = quadPoint;
						max = evalCase;
						if(max == getGameRef().getNotCurrent().getNumberSquare() || max == Config.BRAVE_MAX_CASE_EARN) break;
					}
				}
			}
		}

		if(point != null)
			getGameRef().getBoard().nextMove(point.getX(), point.getY());
		else
			throw new NullPointerException("Point cannot be NULL");
	}

	private int evaluateMoveBrave(QuadPoint point)
	{

		int ret = 1;
		int[][] tmp = getGameRef().getBoard().getSquares();

		//parcours les cases autour
		for (int i = point.getX() - 1; i <= point.getX() + 1; i++)
		{
			if (i < 0 || i >= getGameRef().getBoard().getSize()) continue;

			for (int j = point.getY() - 1; j <= point.getY() + 1; j++)
			{
				if (j < 0 || j >= getGameRef().getBoard().getSize()) continue;

				if(tmp[i][j] == getGameRef().getNotCurrent().getPlayerId())
					ret++;
			}
		}
		return ret;
	}

	/* Reckless game mode
	private void ChaserPlayStyleReckless() {
		// TODO - implement Bot.jouerGloutonTéméraire
		throw new UnsupportedOperationException();
	}
	private void SmartPlayStyleReckless() {
		// TODO - implement Bot.jouerIATéméraire
		throw new UnsupportedOperationException();
	}
	private void evaluateMoveReckless() {
		// TODO - implement Bot.evalCaseTéméraire
		throw new UnsupportedOperationException();
	}*/
}