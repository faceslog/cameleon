package cameleon;

import cameleon.enums.Rules;
import core.datastruct.QuadNode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;
import cameleon.enums.CaseColor;

public class Movement {

	private int x;
	private int y;
	private Board board;
	private CaseColor color;
	private QuadNode<CaseColor> current;
	private QuadTree<CaseColor> quadTree;

	/**
	 * 
	 * @param _x position x
	 * @param _y position y
	 * @param _board board
	 */
	public Movement(int _x, int _y, CaseColor _color, Board _board) {
		if(_board != null) {
			if (_x >= 0 && _x < _board.getSize() && _y >= 0 && _y < _board.getSize()) {
				x = _x;
				y = _y;
				color = _color;
				board = _board;
				quadTree = _board.getQuadTree();
				getCurrentNode(x,y);
			} else {
				System.out.println("ERREUR MOVEMENT");
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
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				QuadPoint pos = new QuadPoint(i, j);
				//System.out.println();
				//System.out.println(pos);
				QuadNode<CaseColor> node = quadTree.search(pos);
				//System.out.println(node);
				if(node != null) {
					if (!(node.getData() == null)) {
						//System.out.println("I - " + i + " : J - " + j + " -- " + node);
						node.setData(color);
					}
				}
			}
		}
	}

	public boolean verifRules(Rules rule) {
		// TODO - implement Movement.verifRules
		throw new UnsupportedOperationException();
	}

}