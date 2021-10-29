package Cameleon;

import Cameleon.Entities.Human;
import Cameleon.Enums.CaseColor;

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
		J1 = new Human("J1", CaseColor.Red);
		J2 = new Human("J2", CaseColor.Blue);
		current = J1;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Taille de la grid : ");
		int size = scanner.nextInt();

		board = new Board(size);
	}

	public void play() {
		init();
		board.showGrid();
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
		if(current.getColor() == CaseColor.Blue) {
			current = J1;
		} else if(current.getColor() == CaseColor.Red) {
			current = J2;
		}
	}

}