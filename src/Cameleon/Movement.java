package Cameleon;

import Core.DataStructures.QuadNode;
import Core.DataStructures.QuadPoint;
import Core.DataStructures.QuadTree;
import Core.Enums;

public class Movement {

	private int x;
	private int y;
	private Board board;
	private Color color;
	private QuadNode current;
	private QuadTree quadTree;
	private Enums.CaseColor tmp;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param board
	 */
	public Movement(int x, int y, Color color, Board board) {
		if(board != null) {
			if (x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize()) {
				this.x = x;
				this.y = y;
				this.color = color;
				this.board = board;
				this.quadTree = board.getQuadTree();
				getCurrentNode(x,y);
			} else {
				System.out.println("ERREUR MOVEMENT");
			}
		}
	}

	/**
	 *
	 * @param x
	 * @param y
	 */
	public void getCurrentNode(int x, int y) { //get le noeud de la position pour changer sa couleur
		if(quadTree != null) {
			QuadPoint pos = new QuadPoint(x,y);
			current = quadTree.search(pos);
			if(current.getColor() == null) {

				//temporaire on changera quand on aura une structure du src fixe la je test des trucs
				if(color == Color.BLUE) {
					tmp = Enums.CaseColor.Blue;
				} else if(color == Color.RED) {
					tmp = Enums.CaseColor.Red;
				} //fin tmp

				//quadTree.insert(new QuadNode(pos, tmp));
				System.out.println(color);
				current.setColor(tmp);
				updateColor();
				board.showGrid();
			}
		}
	}

	public void updateColor() { //update color si la case est deja d'une couleur
		//erreur si pos x = 1 et y = 1 donne 0 - 0 ?
		//check 8 cases autour
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				QuadPoint pos = new QuadPoint(i, j);
				QuadNode node = quadTree.search(pos);
				if(!(node.getColor() == null)) {
					node.setColor(tmp);
				}
			}
		}
	}

	public boolean verifRules(Rule rule) {
		// TODO - implement Movement.verifRules
		throw new UnsupportedOperationException();
	}

}