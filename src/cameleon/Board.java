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
		//initEmpty();
	}

	public void initEmpty() { //juste pour test
		for(int i = 0; i < size; i++) {
			for(int j= 0; j < size; j++) {
				quadTree.insert(new QuadNode(new QuadPoint(j,i),null));
				System.out.print(" ⊡ ");
			}
			System.out.println();
		}
	}

	public void initFull() { //juste pour test bis
		for(int i = 0; i < size; i++) {
			for(int j= 0; j < size-2; j++) {
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

	public boolean isFull(QuadTree qt) {
		return false;
	}

	/**
	 * 
	 * @param color
	 */
	public int nbCellColor(CaseColor color) {
		// TODO - implement Board.nbCell
		throw new UnsupportedOperationException();
	}

	public void calculScore() {
		// TODO - implement Board.calculScore
		throw new UnsupportedOperationException();
	}

	public void showGrid() { //test
		for(int i = 0; i < size; i++) {
			for(int j= 0; j < size; j++) {
				QuadNode<CaseColor> node = quadTree.search(new QuadPoint(j,i));
				if(node != null) {
					if (node.getData() == CaseColor.BLUE) {
						System.out.print(" B ");
					} else if (node.getData() == CaseColor.RED) {
						System.out.print(" R ");
					}
				} else {
					System.out.print(" ⊡ ");
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