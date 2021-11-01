package cameleon;

import cameleon.enums.CaseColor;
import core.datastruct.QuadNode;
import core.datastruct.QuadPoint;
import core.datastruct.QuadTree;

public class Board {

	private final int size;
	private final QuadTree<CaseColor> quadTree;

	/**
	 * Count the nodes containing a given color in the quad tree
	 * We are traveling the tree using Depth First Search
	 * @param tree QuadTree we are searching in recursively
	 * @param color the color we are counting
	 * @return the number of nodes / cells containing this color
	 */
	static public int countCellColor(QuadTree<CaseColor> tree, CaseColor color)
	{
		int count = 0;

		if(tree == null)
			return 0;

		for(QuadNode<CaseColor> node : tree.getNodes())
		{
			if(node.getData() == color)
				count++;
		}

		count += countCellColor(tree.getTopLeftTree(), color);
		count += countCellColor(tree.getTopRightTree(), color);
		count += countCellColor(tree.getBottomLeftTree(), color);
		count += countCellColor(tree.getBottomRightTree(), color);

		return count;
	}

	/**
	 * Count the amount of node in this quadtree
	 * We are traveling the tree using Depth First Search
	 * @param tree QuadTree we are searching in recursively
	 * @return the number of nodes in the quadtree
	 */
	static public int countCellAmount(QuadTree<CaseColor> tree)
	{
		int count = 0;

		if(tree == null)
			return 0;

		count += tree.getNodes().size();

		count += countCellAmount(tree.getTopLeftTree());
		count += countCellAmount(tree.getTopRightTree());
		count += countCellAmount(tree.getBottomLeftTree());
		count += countCellAmount(tree.getBottomRightTree());

		return count;
	}

	/**
	 * Créer un board selon la formule du cours
	 * @param n taille du board 3 * 2^n
	 */
	public Board(int n)
	{
		size = (int) (3 * Math.pow(2, n));
		quadTree = new QuadTree<>(new QuadPoint(0, 0), new QuadPoint(size - 1, size - 1));
	}


	public int getSize() {
		return size;
	}

	public QuadTree<CaseColor> getQuadTree() {
		return quadTree;
	}

	public boolean isFull() { //on va dire que pour le moment ça passe mais pas sur que ce soit la meilleure méthode niveau efficacité
		return countCellAmount(quadTree) == (size * size);
	}


	//score(J2) = nombre de cases bleues - nombre de cases rouges.
	public void computeScore(Player player)
	{
		if(player == null)
			throw new NullPointerException("Player cannot be NULL");

		// Opposite color of the current player
		CaseColor color = player.getColor() == CaseColor.RED ? CaseColor.BLUE : CaseColor.RED;

		int nbPlayerColor = countCellColor(quadTree, player.getColor());
		int nbOtherColor = countCellColor(quadTree, color);

		player.setScore(nbPlayerColor - nbOtherColor);
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

	public void initColor() {
		// TODO - implement Board.initColor
		throw new UnsupportedOperationException();
	}

	public void readFile() {
		// TODO - implement Board.readFile
		throw new UnsupportedOperationException();
	}
}