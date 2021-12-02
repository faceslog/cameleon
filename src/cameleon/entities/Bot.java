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

	// TO DO: FIX Complexité en regardant les cas ou l'on peut break
//	private void GluttonPlayStyleBrave() {
//		QuadPoint point = null;
//		int max = 0;
//
//		for(int i = 0; i < getGameRef().getBoard().getSize(); i++)
//		{
//			for(int j = 0; j < getGameRef().getBoard().getSize(); j++)
//			{
//				QuadPoint quadPoint = new QuadPoint(j,i);
//				if(getGameRef().getBoard().getSquares()[j][i] == Config.FREE_SQUARE)
//				{
//					int evalCase = evaluateMoveBrave(quadPoint);
//					if(max < evalCase)
//					{
//						point = quadPoint;
//						max = evalCase;
//						if(max == getGameRef().getNotCurrent().getNumberSquare() || max == Config.BRAVE_MAX_CASE_EARN) break;
//					}
//				}
//			}
//		}
//
//		if(point != null)
//			getGameRef().getBoard().nextMove(point.getX(), point.getY());
//		else
//			throw new NullPointerException("Point cannot be NULL");
//	}
	private void GluttonPlayStyleBrave() {
		ArrayList<QuadPoint> deletePoint = new ArrayList<>();
		int max = 0;
		QuadPoint quadPoint = null;
		boolean freeSquare = false;
		//parcours point de la liste
		for (QuadPoint point : getGameRef().getNotCurrent().getListPoints().getList()) {
			freeSquare = false;
			//regarde les cases libre autour
			for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
				if (i < 0 || i >= getGameRef().getBoard().getSize()) continue;
				for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
					if (j < 0 || j >= getGameRef().getBoard().getSize()) continue;

					//si une case libre
					if (getGameRef().getBoard().getSquares()[i][j] == Config.FREE_SQUARE) {

						//evaluate le move du point
						QuadPoint tmp = new QuadPoint(i,j);
						freeSquare = true;
						int evalCase = evaluateMoveBrave(tmp);

						//garde le point avec la plus grande valeur
						if (max < evalCase) {
							quadPoint = tmp;
							max = evalCase;
							if (max == getGameRef().getNotCurrent().getNumberSquare() || max == Config.BRAVE_MAX_CASE_EARN)
								break;
						}
					}
				}
			}
			//si pas de case libre delete le point
			if(!freeSquare) {
				deletePoint.add(point);
			}
		}

		for(QuadPoint point: deletePoint) {
			getGameRef().getNotCurrent().getListPoints().remove(point);
		}

		if(quadPoint != null) {
			getListPoints().add(quadPoint);
			getGameRef().getBoard().nextMove(quadPoint.getX(), quadPoint.getY());
		} else {
			getListPoints().add(new QuadPoint(0, 0));
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