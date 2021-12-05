package view.controller;

import cameleon.Board;
import cameleon.Config;
import cameleon.Game;
import cameleon.entities.Bot;
import cameleon.enums.GameMode;
import core.datastruct.QuadPoint;
import view.utils.FrameUtils;
import view.ui.BoardFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTableController extends MouseAdapter {

    private final JTable table;
    private final Game game;
    private final BoardFrame bf;
    private final Board board;

    public JTableController(BoardFrame bf, JTable table, Game game) {
        this.table = table;
        this.game = game;
        this.bf = bf;
        this.board = game.getBoard();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            int row = table.rowAtPoint(e.getPoint());
            int column = table.columnAtPoint(e.getPoint());

            if (game.getEnemy() instanceof Bot bot) {
                int eval;
                if (game.getGameMode() == GameMode.BRAVE) {
                    eval = bot.evaluateMoveBrave(new QuadPoint(column, row));
                } else {
                    eval = bot.evaluateMoveReckless(new QuadPoint(column, row));
                }
                JOptionPane.showMessageDialog(null, "EvaluateMove : " + eval);
            }
        } else {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();

            if (checkMove(column, row)) {
                //CURRENT
                game.getBoard().nextMove(column, row);
                if (game.getGameMode() == GameMode.BRAVE) {
                    repaintCell(column, row);
                } else if (game.getGameMode() == GameMode.RECKLESS) {
                    bf.getTable().repaint();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Movement not valid!");
            }
            checkFull();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //BOT
        game.changeCurrent();
        QuadPoint point = game.getCurrent().move();

        repaintCell(point.getX(), point.getY());

        //RE CURRENT
        game.changeCurrent();

        //update menu
        bf.getScoreP1().setText("Score P1 : " + game.getBoard().getCurrent().getNumberSquare());
        bf.getScoreP2().setText("Score P2 : " + game.getBoard().getEnemy().getNumberSquare());
        bf.getScore().setText("Score : " + game.getScore());
        changeLogoImg();
        bf.menu.updateUI();

        checkFull();
    }

    private boolean checkMove(int x, int y)
    {
        return board.getSquares()[x][y] == Config.FREE_SQUARE;
    }

    private void checkFull() {
        if (game.getBoard().isFull()) {
            if (game.getWinner() != null)
                JOptionPane.showMessageDialog(null, "Player " + game.getWinner().getPlayerId() + " wins!"); //retour accueil + autre affichage
            else
                JOptionPane.showMessageDialog(null, "NO WINNER");
        }
    }

    private void changeLogoImg() {
        if(game.getBoard().getCurrent().getNumberSquare() > game.getBoard().getEnemy().getNumberSquare())
            bf.getLogo().setIcon(FrameUtils.resizeIconPercentage(new ImageIcon("images/logoR.png"),0.5,0.5));
        else if (game.getBoard().getCurrent().getNumberSquare() < game.getBoard().getEnemy().getNumberSquare())
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
