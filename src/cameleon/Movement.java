package cameleon;

import cameleon.enums.Rules;
import core.datastruct.QuadNode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;
import cameleon.enums.CaseColor;

public class Movement {

	private QuadPoint point;
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
				getCurrentNode();
			}
			else
			{
				throw new NullPointerException("Board cannot be NULL");
			}
		}
	}


	public void getCurrentNode() { //get le noeud de la position pour changer sa couleur
		if(quadTree != null)
		{
			current = quadTree.search(point); //recherche le point s'il n'existe pas on l'insert
			if(current == null)
			{
				quadTree.insert(new QuadNode<>(point, color));
				updateColor();
			}
		}
	}

	public void updateColor() { //update color si la case est deja d'une couleur
		//erreur si pos x = 1 et y = 1 donne 0 - 0 ?
		//check 8 cases autour
		for (int i = point.getX() - 1; i <= point.getX() + 1; i++)
		{
			if(i < 0) continue;
			for (int j = point.getY() - 1; j <= point.getY()+ 1; j++)
			{
				if(j < 0) continue;
				QuadPoint pos = new QuadPoint(i, j);
				QuadNode<CaseColor> node = quadTree.search(pos);

				if(node != null)
				{
					if (!(node.getData() == null))
					{
						//System.out.println("I - " + i + " : J - " + j + " -- " + node);
						node.setData(color);
					}
				}
			}
		}
	}

	public boolean verifRules() {
		return false;
	}

}