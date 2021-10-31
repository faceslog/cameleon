package cameleon;

import cameleon.enums.Rules;
import core.datastruct.QuadNode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;
import cameleon.enums.CaseColor;

import javax.management.RuntimeErrorException;

public class Movement {

	QuadPoint point;
	private Board board;
	private CaseColor color;
	private QuadNode<CaseColor> current;
	private QuadTree<CaseColor> quadTree;

	/**
	 * 
	 * @param _point position x,y
	 * @param _color color
	 * @param _board board
	 */
	public Movement(QuadPoint _point, CaseColor _color, Board _board) {
		if(_board != null) {
			if ((_point.getX() >= 0) && (_point.getX() < _board.getSize()) &&
				(_point.getY() >= 0) && (_point.getY() < _board.getSize())) {

				point = _point;
				color = _color;
				board = _board;
				quadTree = _board.getQuadTree();
				getCurrentNode(point.getX(),point.getY());
			}
			else
			{
				throw new NullPointerException("Board cannot be NULL");
			}
		}
	}

	/**
	 *
	 * @param x position x of the node
	 * @param y position y of the node
	 */
	public void getCurrentNode(int x, int y) { //get le noeud de la position pour changer sa couleur
		if(quadTree != null) {
			QuadPoint pos = new QuadPoint(x,y);
			current = quadTree.search(pos); //recherche le point s'il n'existe pas on l'insert
			if(current == null) {
				//System.out.println("HOP");
				quadTree.insert(new QuadNode<>(pos, color));
				updateColor();
			}
		}
	}

	public void updateColor() { //update color si la case est deja d'une couleur
		//erreur si pos x = 1 et y = 1 donne 0 - 0 ?
		//check 8 cases autour
		for (int i = point.getX() - 1; i <= point.getX() + 1; i++)
		{
			for (int j = point.getY() - 1; j <= point.getY()+ 1; j++)
			{
				QuadPoint pos = new QuadPoint(i, j);
				QuadNode<CaseColor> node = quadTree.search(pos);

				if(node == null)
					continue;

				if(!point.compare(node.getPos()))
					continue;

				if (!(node.getData() == null))
				{
					//System.out.println("I - " + i + " : J - " + j + " -- " + node);
					node.setData(color);
				}

			}
		}
	}

	public boolean verifRules(Rules rule) {
		// TODO - implement Movement.verifRules
		throw new UnsupportedOperationException();
	}

}