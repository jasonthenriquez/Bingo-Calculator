import java.awt.*;

public class BingoFreeSpace extends BingoSpace {

    //inherits the basic qualities of a Bingo space
    public BingoFreeSpace(int num) {
        super(num, 2, 2);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval((getWidth() - 90)/2, (getHeight() - 90)/2, 90, 90);
        g.fillOval((getWidth() - 90)/2, (getHeight() - 90)/2, 90, 90);
    }
}
