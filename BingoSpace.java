import javax.swing.*;
import java.awt.*;

public class BingoSpace extends JLabel {
    private Color bingoBlue;
    private boolean selected, centerLeft, centerRight, centerTop, centerBottom,
            centerTopLeft,centerBottomRight, centerTopRight, centerBottomLeft;
    private int column, row;

    // Bingo space traits for 'B', 'I', 'N', 'G', 'O'
    public BingoSpace(char letter) {
        this();
        setFont(new Font("Serif", Font.BOLD, 100));
        setText(String.valueOf(letter));
        setBackground(bingoBlue);
        setForeground(Color.WHITE);
    }

    // Bingo space traits for each of every 25 numbers on GraphicalBingoCard
    public BingoSpace(int num, int col, int r) {
        this();
        column = col;
        row = r;
        setFont(new Font("Serif", Font.BOLD, 50));
        setText(String.valueOf(num));
        setBackground(Color.WHITE);
    }

    // traits shared by all Bingo spaces
    protected BingoSpace() {
        bingoBlue = new Color(0, 148, 217);
        setVisible(true);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(bingoBlue, 2));
        setHorizontalAlignment(JLabel.CENTER);
    }

    //draws lines connecting spaces that are filled in for a win condition
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bingoBlue);
        if (centerTopRight) {
            g.drawLine(getWidth(), 0, getWidth()/2, getHeight()/2);
        }

        if (centerTopLeft) {
            g.drawLine(0, getHeight(), getWidth()/2, getHeight()/2);
        }

        if (centerBottomLeft) {
            g.drawLine(0, 0, getWidth()/2, getHeight()/2);
        }

        if (centerBottomRight) {
            g.drawLine(getWidth(), getHeight(), getWidth()/2, getHeight()/2);
        }

        if (centerRight) {
            g.drawLine(getWidth(), getHeight()/2, getWidth()/2, getHeight()/2);
        }

        if (centerLeft) {
            g.drawLine(0, getHeight()/2, getWidth()/2, getHeight()/2);
        }

        if (centerTop) {
            g.drawLine(getWidth()/2, getHeight(), getWidth()/2, getHeight()/2);
        }

        if (centerBottom) {
            g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight()/2);
        }

        if (selected) {
            g.setColor(Color.RED);
            g.drawOval((getWidth() - 90) / 2, (getHeight() - 90) / 2, 90, 90);
            g.fillOval((getWidth() - 90) / 2, (getHeight() - 90) / 2, 90, 90);
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setCenterLeft(boolean centerLeft) {
        this.centerLeft = centerLeft;
    }

    public void setCenterRight(boolean centerRight) {
        this.centerRight = centerRight;
    }

    public void setCenterTop(boolean centerTop) {
        this.centerTop = centerTop;
    }

    public void setCenterBottom(boolean centerBottom) {
        this.centerBottom = centerBottom;
    }

    public void setCenterTopLeft(boolean centerTopLeft) {
        this.centerTopLeft = centerTopLeft;
    }

    public void setCenterTopRight(boolean centerTopRight) {
        this.centerTopRight = centerTopRight;
    }

    public void setCenterBottomLeft(boolean centerBottomLeft) {
        this.centerBottomLeft = centerBottomLeft;
    }

    public void setCenterBottomRight(boolean centerBottomRight) {
        this.centerBottomRight = centerBottomRight;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
