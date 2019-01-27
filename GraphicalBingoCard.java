import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GraphicalBingoCard extends JPanel implements BingoCard {
    private BingoSpace [][] bingoSpaces;
    private ArrayList<WinCondition> winConditions;
    private boolean active;
    private boolean mouseBeingDragged;
    private long timeClicked;

    public GraphicalBingoCard() {
        setVisible(true);
        setLayout(new GridLayout(6, 5));

        add(new BingoSpace('B'));
        add(new BingoSpace('I'));
        add(new BingoSpace('N'));
        add(new BingoSpace('G'));
        add(new BingoSpace('O'));

        int [][] cardNumbers = new int[5][5];
        generateNumbers(cardNumbers);

        bingoSpaces = new BingoSpace[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 2 || j != 2) {
                    bingoSpaces[i][j] = new BingoSpace(cardNumbers[j][i], i, j);
                    bingoSpaces[i][j].addMouseListener(new BingoListener());
                    add(bingoSpaces[i][j]);
                } else {
                    bingoSpaces[i][j] = new BingoFreeSpace(0);
                    add(bingoSpaces[i][j]);
                }
            }
        }

        winConditions = new ArrayList<>();
    }

    public void generateNumbers(int [][] numbers) {
        SplittableRandom rand = new SplittableRandom();
        Set<Integer> numberSet = new HashSet<>();
        int letter = 1;
        for (int column = 0; column < 5; ++column) {
            for (int row = 0; row < 5; ++row) {
                if (column != 2 || row != 2) {
                    int randomNumber;
                    do {
                        randomNumber = rand.nextInt(15) + letter;
                    } while (numberSet.contains(randomNumber));
                    numberSet.add(randomNumber);
                    numbers[column][row] = randomNumber;
                }
            } letter += 15;
        }
    }

    public void clearBoard() {
        for (int column = 0; column < 5; ++column) {
            for (int row = 0; row < 5; ++row) {
                unselect(bingoSpaces[column][row]);
            }
        } repaint();

        winConditions.clear();
    }

    // shares ArrayList in fast OO manner
    public ArrayList<WinCondition> getWinConditions() {
        ArrayList<WinCondition> copy = new ArrayList<>();
        for (WinCondition winCondition : winConditions) {
            copy.add(winCondition.copy());
        }

        return copy;
    }

    // determines whether GraphicalBingoCard is active
    public void setActive(boolean active) {
        this.active = active;
    }

    // removes all graphical additions to a Bingo space
    private void unselect(BingoSpace bingoSpace) {
        bingoSpace.setCenterTopRight(false);
        bingoSpace.setCenterTopLeft(false);
        bingoSpace.setCenterBottomLeft(false);
        bingoSpace.setCenterBottomRight(false);
        bingoSpace.setCenterRight(false);
        bingoSpace.setCenterLeft(false);
        bingoSpace.setCenterTop(false);
        bingoSpace.setCenterBottom(false);
        bingoSpace.setSelected(false);
    }

    private class BingoListener extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (active) {
                mouseBeingDragged = false;
                BingoSpace bingoSpace = (BingoSpace) e.getSource();
                long timeReleased = System.currentTimeMillis();
                if (timeReleased - timeClicked < 250) {
                    bingoSpace.setSelected(false);
                    update(bingoSpace);

                    for (WinCondition winCondition: winConditions) {
                        boolean pointRemoved = false;
                        for (int i = 0; i < winCondition.getColumns().size(); ++i) {
                            int winColumn = winCondition.getColumns().get(i);
                            int winRow = winCondition.getRows().get(i);

                            if (bingoSpace.getColumn() == winColumn && bingoSpace.getRow() == winRow) {
                                winCondition.remove(i);
                                if (winCondition.getColumns().size() == 0) {
                                    winConditions.remove(winCondition);
                                }

                                pointRemoved = true;
                                break;
                            }
                        } if (pointRemoved) {
                            break;
                        }
                    }
                } else {
                    WinCondition winCondition = new WinCondition();
                    for (int column = 0; column < 5; column++) {
                        for (int row = 0; row < 5; row++) {
                            if (bingoSpaces[column][row].isSelected()) {
                                update(bingoSpaces[column][row]);
                                winCondition.addSpace(column, row);
                            }
                        }
                    }
                    winConditions.add(winCondition);
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            if (active) {
                timeClicked = System.currentTimeMillis();
                mouseBeingDragged = true;

                for (int column = 0; column < 5; ++column) {
                    for (int row = 0; row < 5; ++row) {
                        unselect(bingoSpaces[column][row]);
                    }
                }

                BingoSpace bingoSpace = (BingoSpace) e.getSource();
                bingoSpace.setSelected(true);
                update(bingoSpace);
            }
        }

        public void mouseEntered(MouseEvent e) {
            if (active && mouseBeingDragged) {
                BingoSpace bingoSpace = (BingoSpace) e.getSource();
                bingoSpace.setSelected(true);
                update(bingoSpace);
            }
        }

        // refreshes board state
        // draws lines between Bingo spaces
        private void update(BingoSpace bingoSpace) {
            if (bingoSpace.getColumn() < 4 && bingoSpace.getRow() < 4 &&
                    bingoSpaces[bingoSpace.getColumn() + 1][bingoSpace.getRow() + 1].isSelected()) {
                bingoSpace.setCenterBottomRight(true);
            }

            if (bingoSpace.getColumn() > 0 && bingoSpace.getRow() > 0 &&
                    bingoSpaces[bingoSpace.getColumn() - 1][bingoSpace.getRow() - 1].isSelected()) {
                bingoSpace.setCenterBottomLeft(true);
            }

            if (bingoSpace.getColumn() < 4 && bingoSpace.getRow() > 0 &&
                    bingoSpaces[bingoSpace.getColumn() + 1][bingoSpace.getRow() - 1].isSelected()) {
                bingoSpace.setCenterTopLeft(true);
            }

            if (bingoSpace.getColumn() > 0 && bingoSpace.getRow() < 4 &&
                    bingoSpaces[bingoSpace.getColumn() - 1][bingoSpace.getRow() + 1].isSelected()) {
                bingoSpace.setCenterTopRight(true);
            }

            if (bingoSpace.getRow() < 4 &&
                    bingoSpaces[bingoSpace.getColumn()][bingoSpace.getRow() + 1].isSelected()) {
                bingoSpace.setCenterRight(true);
            }

            if (bingoSpace.getRow() > 0 &&
                    bingoSpaces[bingoSpace.getColumn()][bingoSpace.getRow() - 1].isSelected()) {
                bingoSpace.setCenterLeft(true);
            }

            if (bingoSpace.getColumn() < 4 &&
                    bingoSpaces[bingoSpace.getColumn() + 1][bingoSpace.getRow()].isSelected()) {
                bingoSpace.setCenterTop(true);
            }

            if (bingoSpace.getColumn() > 0 &&
                    bingoSpaces[bingoSpace.getColumn() - 1][bingoSpace.getRow()].isSelected()) {
                bingoSpace.setCenterBottom(true);
            }

            bingoSpace.repaint();
        }
    }

}