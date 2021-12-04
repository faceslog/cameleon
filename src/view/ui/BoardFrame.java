package view.ui;

import cameleon.Game;
import view.controller.BoardFrameController;
import view.controller.JTableController;
import view.model.GridModel;
import view.utils.FrameUtils;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class BoardFrame {

    public BoardFrame(StartFrame sf, Game game) {
        this.sf = sf;
        this.game = game;

        frameSizeWithoutBorder = FrameUtils.frameSizeWithoutBorder(sf.getFrame());
        squareSize = (int) (frameSizeWithoutBorder.getHeight() / game.getBoard().getSize() * 0.90);
    }

    private void init() {
        logo = new JLabel(FrameUtils.resizeIconPercentage(new ImageIcon("images/logoR.png"),0.5,0.5));

        score = new JLabel("Score : 0 ", SwingConstants.CENTER);
        score.setFont(FrameUtils.customFont());
        score.setForeground(FrameUtils.colorText());

        scoreP1 = new JLabel("Score P1 : 0 ", SwingConstants.CENTER);
        scoreP1.setFont(FrameUtils.customFont());
        scoreP1.setForeground(FrameUtils.colorP1());

        scoreP2 = new JLabel("Score P2 : 0 ", SwingConstants.CENTER);
        scoreP2.setFont(FrameUtils.customFont());
        scoreP2.setForeground(FrameUtils.colorP2());

        exit = FrameUtils.createButton("images/bouton/exit.png", 1.25);

        BoardFrameController bfc = new BoardFrameController(sf, this);
        exit.addActionListener(bfc);
    }

    public JPanel createPanel() {
        init();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(frameSizeWithoutBorder);

        menu = createMenu();

        panel.add(createBoard(), BorderLayout.CENTER);
        panel.add(menu, BorderLayout.EAST);

        panel.setOpaque(false);

        return panel;
    }

    public JPanel createMenu() {
        int width = frameSizeWithoutBorder.width -  frameSizeWithoutBorder.height;

        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(new Dimension(width, (int) (frameSizeWithoutBorder.height * 0.80)));
        p.setOpaque(false);

        p.add(logo, BorderLayout.NORTH);
        p.add(exit,BorderLayout.SOUTH);

        JPanel panel = new JPanel(new GridLayout(7,1));
        panel.setPreferredSize(new Dimension(width, (int) (frameSizeWithoutBorder.height * 0.80)));

        panel.add(new JLabel());
        panel.add(score);
        panel.add(new JLabel());
        panel.add(scoreP1);
        panel.add(new JLabel());
        panel.add(scoreP2);
        panel.add(new JLabel());

        panel.setBackground(new Color(255,255,255,120));

        p.add(panel, BorderLayout.CENTER);

        return p;
    }

    public JPanel createBoard() {
        gridModel = new GridModel(game.getBoard().getSquares(), squareSize);
        table = new JTable(gridModel);
        table.setShowGrid(true);

        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        changeSize(squareSize, squareSize);

        JTableController jtc = new JTableController(this, sf, table, game);
        table.addMouseListener(jtc);

        JPanel p = new JPanel();
        p.add(table);
        p.setOpaque(false);

        return p;
    }

    public void changeSize(int width, int height){
        TableColumn col;
        for(int i = 0; i < table.getColumnCount(); i++){
            col = table.getColumnModel().getColumn(i);
            col.setPreferredWidth(width);
        }
        for(int i = 0; i < table.getRowCount(); i++){
            table.setRowHeight(i, height);
        }
    }

    public JLabel getScore() {
        return score;
    }

    public JLabel getScoreP1() {
        return scoreP1;
    }

    public JLabel getScoreP2() {
        return scoreP2;
    }

    public JLabel getLogo() {
        return logo;
    }

    public JButton getExit() {
        return exit;
    }

    public JTable getTable() {
        return table;
    }

    public GridModel getGridModel() {
        return gridModel;
    }

    private JTable table;
    private GridModel gridModel;

    private JLabel logo;
    private JLabel scoreP1;
    private JLabel scoreP2;
    private JLabel score;
    private JButton exit;

    public JPanel menu;

    private int squareSize;
    private Game game;
    private StartFrame sf;
    private Dimension frameSizeWithoutBorder;
}
