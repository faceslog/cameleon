package view.model;

import view.utils.FrameUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class  GridModel extends AbstractTableModel {

    private static final String PATH = "images/";
    private int noOfRows, noOfCols;
    private int[][] grid;
    private int size;

    public GridModel(int[][] grid, int size) {
        this.grid = grid;
        noOfRows = this.grid.length;
        noOfCols = this.grid[0].length;
        this.size = size;
    }

    public int getRowCount() {
        return(noOfRows);
    }

    public int getColumnCount() {
        return(noOfCols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object result;
        int square = grid[columnIndex][rowIndex];

        String squareFree = "tmpEmpty.png";
        String squareP1 = "pionR.png";
        String squareP2 = "pionB.png";

        ImageIcon p1 = new ImageIcon(PATH + squareP1);
        ImageIcon p2 = new ImageIcon(PATH + squareP2);
        ImageIcon free = new ImageIcon(PATH + squareFree);

        if (square == 1)
            result = FrameUtils.resizeIcon(p1, size, size);
        else if(square == 2)
            result = FrameUtils.resizeIcon(p2, size, size);
        else
            result = FrameUtils.resizeIcon(free, size, size);

//        if (square == 1)
//            result = new ImageIcon(PATH + squareP1);
//         else if (square == 2)
//            result = new ImageIcon(PATH + squareP2);
//         else
//             result = new ImageIcon(PATH + squareFree);

         return result;
    }

    /**
     * get the name of the column
     * @param c : the number of the column
     * @return a String for the name of the column
     */
    public String getColumnName(int c){
        return (Integer.toString(c));
    }

    /**
     * get the class of the object at column c
     * @param c : the number of the column
     * @return the class of the object at column c
     */
    public Class getColumnClass(int c) {
        return this.getValueAt(0, c).getClass();
    }

}
