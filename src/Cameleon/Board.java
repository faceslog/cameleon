package Cameleon;

import Core.DataStructures.QuadNode;
import Core.DataStructures.QuadPoint;
import Core.DataStructures.QuadTree;
import Core.Enums;

public class Board {

	private int size;
	private QuadTree quadTree;

	/**
	 * 
	 * @param n
	 */
	public Board(int n) {
		this.size = (int) (3 * Math.pow(2, n));
		this.quadTree = new QuadTree(new QuadPoint(0,0), new QuadPoint(size-1, size-1));
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
	 * @param c
	 */
	public int nbCellColor(Color c) {
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
				QuadNode node = quadTree.search(new QuadPoint(j,i));
				if(node != null) {
					if (node.getColor() == Enums.CaseColor.Blue) {
						System.out.print(" B ");
					} else if (node.getColor() == Enums.CaseColor.Red) {
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

	public QuadTree getQuadTree() {
		return quadTree;
	}

}