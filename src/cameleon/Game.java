package cameleon;

import cameleon.entities.Human;
import cameleon.enums.CaseColor;

import java.util.Scanner;

public class Game {

	private Player J1;
	private Player J2;
	private Player current;
	Board board;

	public Game() {
		play();
	}

	public void init() {
		J1 = new Human("J1", CaseColor.RED);
		J2 = new Human("J2", CaseColor.BLUE);
		current = J1;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Taille de la grid (n) : ");
		int size = scanner.nextInt();

		board = new Board(size);
	}

	public void play() {
		init();
		board.showGrid();
		while (!board.isFull(board.getQuadTree())) {
			System.out.println(current.getName());
			current.move(board);
			changeCurrent();
			System.out.println();
		}
		System.out.println("STOP");
		end();
	}

	public void end() {
		//affichage gagnant + score
	}

	public void changeCurrent() {
		if(current.getColor() == CaseColor.BLUE) {
			current = J1;
		} else if(current.getColor() == CaseColor.RED) {
			current = J2;
		}
	}

}