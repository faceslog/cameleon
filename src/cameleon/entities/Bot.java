package cameleon.entities;

import cameleon.Config;
import cameleon.Game;
import cameleon.Player;
import cameleon.boardmodes.BReckless;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;

import java.util.ArrayList;

public class Bot extends Player {

	public Bot(int _playerId, Game _gameRef)
	{
		super(_playerId, _gameRef);
	}

	@Override
	public void move()
	{
		if(getGameRef().getGameMode() == GameMode.BRAVE)
			GluttonPlayStyleBrave();
		else
			GluttonPlayStyleReckless();
	}

	private void GluttonPlayStyleBrave() {
		int max = 0;
		QuadPoint quadPoint = null;
		//parcours point de la liste
		for (QuadPoint point : getGameRef().getBoard().getFreePoints().getList()) {
			int evalCase = evaluateMoveBrave(point);

			//garde le point avec la plus grande valeur
//			System.out.println(" Max : " + max + " --- " + evalCase + " - " + point);
			if (max < evalCase) {
				quadPoint = point;
				max = evalCase;
//				if (max == getGameRef().getNotCurrent().getNumberSquare() || max == Config.BRAVE_MAX_CASE_EARN)
//					break;
			}
		}
		if (quadPoint != null) {
			getGameRef().getBoard().getFreePoints().add(quadPoint);
			getGameRef().getBoard().nextMove(quadPoint.getX(), quadPoint.getY());
		} else {
			getGameRef().getBoard().getFreePoints().add(new QuadPoint(0, 0));
			getGameRef().getBoard().nextMove(0, 0);
		}
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

	// 1 - Check s'il peut obtenir une région ? Si oui check si pas meilleure région et on stocke en S1 combien cela rapporte de pts puis --->  2)
	// 2 - Pendant les check régions si une région possède moins de cases prises que RegionSize - 1 regarder combien de cases il peut voler en plaçant un point dans cette dernière & stocker la valeur en S2
	// 3 - Comparer S1 et S2 et choisir celle qui rapporte le + de points
	private void GluttonPlayStyleReckless()
	{
		QuadPoint point = null;
		int max = 0;

		for(int i = 0; i < getGameRef().getBoard().getSize(); i++)
		{
			for(int j = 0; j < getGameRef().getBoard().getSize(); j++)
			{
				QuadPoint quadPoint = new QuadPoint(j,i);
				if(getGameRef().getBoard().getSquares()[j][i] == Config.FREE_SQUARE)
				{
					int evalCase = evaluateMoveReckless(quadPoint);
					if(max < evalCase)
					{
						point = quadPoint;
						max = evalCase;
					}
				}
			}
		}

		if(point != null)
		{
			System.out.printf("Point évalué: %d %d, gain: %d\n", point.getX(), point.getY(), max);
			getGameRef().getBoard().nextMove(point.getX(), point.getY());
		}
		else
			throw new NullPointerException("Point cannot be NULL");
	}

	// 1) Ne jamais être avant-dernier à placer un point dans une zone.
	// 2) Essayer de gagner toujours une zone en plaçant le pion en dernier
	// 3) Jouer les zones et ne pas laisser un adversaire gagner 3 zones.
	// 4) Pour jouer les zones placer ses points dans les zones les plus remplies sauf si cas 1)
	private void SmartPlayStyleReckless() {
		// TODO - implement Bot.jouerIATéméraire
		throw new UnsupportedOperationException();
	}

	// Idem updateColor en comptant le nombre de cases prises.
	private int evaluateMoveReckless(QuadPoint point)
	{
		return ((BReckless) getGameRef().getBoard()).countColorReckless(point.getX(), point.getY());
	}
}