package cameleon.entities;

import cameleon.Config;
import cameleon.Game;
import cameleon.Player;
import cameleon.Region;
import cameleon.boards.BReckless;
import cameleon.enums.GameMode;
import core.datastruct.DataManager;
import core.datastruct.QuadPoint;

public class Bot extends Player {

	private final DataManager<QuadPoint> freePoints;

	public Bot(int _playerId, Game _gameRef)
	{
		super(_playerId, _gameRef);
		freePoints = new DataManager<>();
	}

	public DataManager<QuadPoint> getFreePoints() {
		return freePoints;
	}

	@Override
	public void move()
	{
		if(getGameRef().getGameMode() == GameMode.BRAVE)
			GluttonPlayStyleBrave();
		else
			SmartPlayStyleReckless();
	}

	private void GluttonPlayStyleBrave() {
		int max = 0;
		QuadPoint quadPoint = null;
		//parcours point de la liste
		for (QuadPoint point : freePoints.getList()) {
			int evalCase = evaluateMoveBrave(point);
			//garde le point avec la plus grande valeur
			if (max < evalCase) {
				quadPoint = point;
				max = evalCase;
				if (max == Config.BRAVE_MAX_CASE_EARN)
					break;
			}
		}
		
		if (quadPoint != null)
		{
			freePoints.remove(quadPoint); // On supprime le point de la liste des points libre
			getGameRef().getBoard().nextMove(quadPoint.getX(), quadPoint.getY());
		}
		else
			getGameRef().getBoard().nextMove(0, 0);
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
	
	private void GluttonPlayStyleReckless()
	{
		int max = 0;
		QuadPoint quadPoint = null;
		//parcours point de la liste
		for (QuadPoint point : freePoints.getList()) {
			int evalCase = evaluateMoveReckless(point);
			//garde le point avec la plus grande valeur
			if (max < evalCase) {
				quadPoint = point;
				max = evalCase;
			}
		}

		if (quadPoint != null)
		{
			System.out.printf("Point évalué: %s, gain: %d\n", quadPoint, max);
			freePoints.remove(quadPoint); // On supprime le point de la liste des points libre
			getGameRef().getBoard().nextMove(quadPoint.getX(), quadPoint.getY());
		}
		else
			getGameRef().getBoard().nextMove(0, 0);
	}

	// 1) Ne jamais être avant-dernier à placer un point dans une zone.
	// 2) Essayer de gagner toujours une zone en plaçant le pion en dernier
	private void SmartPlayStyleReckless()
	{
		int max = 0;
		QuadPoint quadPoint = null;
		QuadPoint lastHope = new QuadPoint(0,0);
		//parcours point de la liste
		for (QuadPoint point : freePoints.getList())
		{
			int evalCase = evaluateMoveReckless(point);
			BReckless board = (BReckless) getGameRef().getBoard();
			Region currentReg = board.getRegionOfPoint(point.getX(), point.getY());
			// Si le point n'est pas l'avant-dernier on le met
			if(currentReg.getSquareTaken() == (currentReg.getMaxSquareInside() - 2))
			{
				lastHope = point;
			}
			else
			{
				if (max < evalCase)
				{
					quadPoint = point;
					max = evalCase;
				}
			}

		}

		if (quadPoint != null)
		{
			System.out.printf("Point évalué: %s, gain: %d\n", quadPoint, max);
			freePoints.remove(quadPoint); // On supprime le point de la liste des points libre
			getGameRef().getBoard().nextMove(quadPoint.getX(), quadPoint.getY());
		}
		else
			getGameRef().getBoard().nextMove(lastHope.getX(), lastHope.getY());
	}

	// Idem updateColor en comptant le nombre de cases prises.
	private int evaluateMoveReckless(QuadPoint point)
	{
		return ((BReckless) getGameRef().getBoard()).countColorReckless(point.getX(), point.getY());
	}

}