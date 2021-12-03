package view.controller;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;
import core.datastruct.QuadPoint;
import view.utils.FrameUtils;
import view.ui.BoardFrame;
import view.ui.StartFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTableController extends MouseAdapter {

    private final JTable table;
    private Game game;
    private StartFrame sf;
    private BoardFrame bf;

    public JTableController(BoardFrame bf, StartFrame sf, JTable table, Game game) {
        this.table = table;
        this.game = game;
        this.sf = sf;
        this.bf = bf;
    }

    @Override public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();

        if (checkMove(column, row, game.getBoard())) {
            //CURRENT
            game.getBoard().nextMove(column, row);

            System.out.println(column);
            System.out.println(row);
            repaintCell(column, row);

            //BOT
            game.changeCurrent();
            QuadPoint point = game.getCurrent().move();

            repaintCell(point.getX(), point.getY());

            //RE CURRENT
            game.changeCurrent();

            //update menu
            bf.getScoreP1().setText("Score P1 : " + game.getBoard().getCurrentPlayer().getNumberSquare());
            bf.getScoreP2().setText("Score P2 : " + game.getBoard().getNotCurrentPlayer().getNumberSquare());
            changeLogoImg();
            bf.menu.updateUI();

        } else {
            JOptionPane.showMessageDialog(null, "Movement not valid!");
        }

        if (game.getBoard().isFull()) {
            if (game.getWinner() != null)
                JOptionPane.showMessageDialog(null, "Player " + game.getWinner().getPlayerId() + " wins!"); //retour accueil + autre affichage
            else
                JOptionPane.showMessageDialog(null, "NO WINNER");
        }
    }

    private boolean checkMove(int x, int y, Board board)
    {
        return board.getSquares()[x][y] == Config.FREE_SQUARE;
    }

    private void changeLogoImg() {
        if(game.getBoard().getCurrentPlayer().getNumberSquare() > game.getBoard().getNotCurrentPlayer().getNumberSquare())
            bf.getLogo().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/logoR.png"),0.5,0.5));
        else if (game.getBoard().getCurrentPlayer().getNumberSquare() < game.getBoard().getNotCurrentPlayer().getNumberSquare())
            bf.getLogo().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/logoB.png"),0.5,0.5));
    }


    private void repaintCell(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++)
        {
            if (i < 0 || i >= game.getBoard().getSize()) continue;

            for (int j = y - 1; j <= y + 1; j++)
            {
                if (j < 0 || j >= game.getBoard().getSize()) continue;
                bf.getGridModel().fireTableCellUpdated(x, y);
            }
        }
    }

}
