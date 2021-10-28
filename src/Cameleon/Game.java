package Cameleon;

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
		J1 = new Human(Color.RED, "J1");
		J2 = new Human(Color.BLUE, "J2");
		current = J1;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Taille de la grid : ");
		int size = scanner.nextInt();

		board = new Board(size);
	}

	public void play() {
		init();
		while (!board.isFull()) {
			System.out.println(current.getName());
			current.move(board);
			changeCurrent();
			System.out.println();
		}
		end();
	}

	public void end() {
		//affichage gagnat + score
	}

	public void changeCurrent() {
		if(current.getColor() == Color.BLUE) {
			current = J1;
		} else if(current.getColor() == Color.RED) {
			current = J2;
		}
	}

}