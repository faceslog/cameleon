package cameleon;

import cameleon.enums.CaseColor;
import core.datastruct.QuadNode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

public class Board {

	private int size;
	private QuadTree<CaseColor> quadTree;

	/**
	 * 
	 * @param n
	 */
	public Board(int n) {
		size = (int) (3 * Math.pow(2, n));
		quadTree = new QuadTree<>(new QuadPoint(0, 0), new QuadPoint(size - 1, size - 1));
		//initFull();
	}

	public void initEmpty() { //juste pour test
		for(int i = 0; i < size; i++) {
			for(int j= 0; j < size; j++) {
				quadTree.insert(new QuadNode(new QuadPoint(j,i),null));
			}
		}
	}

	public void initFull() { //juste pour test bis
		for(int i = 0; i < size-3; i++) {
			for(int j= 0; j < size-3; j++) {
					quadTree.insert(new QuadNode(new QuadPoint(j, i), CaseColor.RED));

			}
		}
	}

	public void initColor() {
		// TODO - implement Board.initColor
		throw new UnsupportedOperationException();
	}

	public void readFile() {
		// TODO - implement Board.readFile
		throw new UnsupportedOperationException();
	}

	public boolean isFull() { //on va dire que pour le moment ça passe mais pas sur que ce soit la meilleure méthode niveau efficacité
		return nbCell(quadTree) == (size * size);
	}

	/**
	 * 
	 * @param color
	 */
	public int nbCellColor(CaseColor color, QuadTree qt) { //du coup erreur -> cause update des cases qui en ajoute une de trop
		int ret = 0;
		if (qt.getTopLeftTree() != null) {
			if (qt.getTopLeftTree().getCurrentNode() != null &&
					qt.getTopLeftTree().getCurrentNode().getData() == color) {
				ret++;
			}
			ret = ret + nbCellColor(color, qt.getTopLeftTree());
		}
		if (qt.getTopRightTree() != null) {
			if (qt.getTopRightTree().getCurrentNode() != null &&
					qt.getTopRightTree().getCurrentNode().getData() == color) {
				ret++;
			}
			ret = ret + nbCellColor(color, qt.getTopRightTree());
		}
		if(qt.getBottomRightTree() != null){
			if (qt.getBottomRightTree().getCurrentNode() != null &&
					qt.getBottomRightTree().getCurrentNode().getData() == color) {
				ret++;
			}
			ret = ret + nbCellColor(color, qt.getBottomRightTree());
		}
		if(qt.getBottomLeftTree() != null){
			if (qt.getBottomLeftTree().getCurrentNode() != null &&
					qt.getBottomLeftTree().getCurrentNode().getData() == color) {
				ret++;
			}
			ret = ret + nbCellColor(color, qt.getBottomLeftTree());
		}
		return ret;
	}

	public int nbCell(QuadTree qt) { //idem erreur
		int ret = 0;
		if (qt.getTopLeftTree() != null) {
			if (qt.getTopLeftTree().getCurrentNode() != null) {
				ret++;
			}
			ret = ret + nbCell(qt.getTopLeftTree());
		}
		if (qt.getTopRightTree() != null) {
			if (qt.getTopRightTree().getCurrentNode() != null) {
				ret++;
			}
			ret = ret + nbCell(qt.getTopRightTree());
		}
		if(qt.getBottomRightTree() != null){
			if (qt.getBottomRightTree().getCurrentNode() != null) {
				ret++;
			}
			ret = ret + nbCell(qt.getBottomRightTree());
		}
		if(qt.getBottomLeftTree() != null){
			if (qt.getBottomLeftTree().getCurrentNode() != null) {
				ret++;
			}
			ret = ret + nbCell(qt.getBottomLeftTree());
		}
		return ret;
	}

	public void calculScore(Player player) { //score(J2) = nombre de cases bleues - nombre de cases rouges.
		CaseColor color;
		switch (player.getColor()) {
			case RED -> color = CaseColor.BLUE;
			case BLUE -> color = CaseColor.RED;
			default -> color = null;
		}

		int nbPlayerColor = nbCellColor(player.getColor(), quadTree);
		int nbOtherColor = nbCellColor(color, quadTree);

		player.setScore(nbPlayerColor - nbOtherColor); //peut etre pas besoin de le mettre en attribut de player mais juste retourner le int ??
	}

	public void showGrid() {
		System.out.print("\t\t");
		for(int k = 0; k < size; k++) {
			System.out.print("\t" + k + "\t");
		}
		System.out.println();
		for(int i = 0; i < size; i++)
		{
			System.out.print("\t" + i + "\t");

			for(int j= 0; j < size; j++)
			{
				QuadNode<CaseColor> node = quadTree.search(new QuadPoint(j,i));
				if(node != null)
				{
						switch (node.getData())
						{
							case BLUE -> System.out.print("\tB\t");
							case RED -> System.out.print("\tR\t");
							default -> System.out.print("\t⊡\t");
						}
				}
				else
				{
					System.out.print("\t⊡\t");
				}
			}
			System.out.println();
		}
	}

	public int getSize() {
		return size;
	}

	public QuadTree<CaseColor> getQuadTree() {
		return quadTree;
	}

}