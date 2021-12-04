package view.model;

import view.utils.FrameUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class  GridModel extends AbstractTableModel {

    private final int noOfRows;
    private final int noOfCols;
    private final int[][] grid;
    private final int size;

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

        if (square == 1)
            result = createImageIcon(FrameUtils.colorP1(), size, size);
         else if (square == 2)
            result = createImageIcon(FrameUtils.colorP2(), size, size);
         else
            result = createImageIcon(Color.WHITE, size, size);

         return result;
    }

    public static ImageIcon createImageIcon(Color color, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect (0, 0, width, height);
        return new ImageIcon(image);
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
