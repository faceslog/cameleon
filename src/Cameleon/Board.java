package Cameleon;

import Cameleon.Enums.CaseColor;
import Core.DataStructures.QuadNode;
import Core.DataStructures.QuadPoint;
import Core.DataStructures.QuadTree;

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

	public void initColor() {
		// TODO - implement Board.initColor
		throw new UnsupportedOperationException();
	}

	public void readFile() {
		// TODO - implement Board.readFile
		throw new UnsupportedOperationException();
	}

	public boolean isFull() {
		return false;
	}

	/**
	 * 
	 * @param colour
	 */
	public int nbCellColor(CaseColor colour) {
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
					if (node.getData() == CaseColor.Blue) {
						System.out.print(" B ");
					} else if (node.getData() == CaseColor.Red) {
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