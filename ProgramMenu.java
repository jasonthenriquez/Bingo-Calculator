import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramMenu extends JFrame {
    private JPanel bpPanel;
    private JButton defaultButton, blackoutButton, customButton, clearButton, instructionsButton, executeButton;
    private BingoPattern bingoPattern;
    private GraphicalBingoCard graphicalBingoCard;
    private JTextField trialField, playerField;
    private int trials, players;
    private final int THREAD_COUNT = 10;
    private List<Integer> turnCounts;
    private List<Integer> winnerCounts;
    private ArrayList<WinCondition> winConditions;


    public ProgramMenu() {
        Color color = Color.WHITE;
        Color bingoBlue = new Color(0, 148, 217);

        ButtonListener b = new ButtonListener();

        graphicalBingoCard = new GraphicalBingoCard();
        add(graphicalBingoCard);

        defaultButton = new JButton("Regular");
        bingoPattern = BingoPattern.DEFAULT;
        defaultButton.setEnabled(false);

        blackoutButton = new JButton("Blackout");
        customButton = new JButton("Custom");

        defaultButton.addActionListener(b);
        blackoutButton.addActionListener(b);
        customButton.addActionListener(b);

        JPanel rhsPanel = new JPanel();
        rhsPanel.setLayout(new GridLayout(4, 1));

        executeButton = new JButton("Run Simulations");
        executeButton.addActionListener(b);
        rhsPanel.add(executeButton);

        bpPanel = new JPanel();
        bpPanel.setLayout(new GridLayout(2, 3));

        JLabel defaultText = new JLabel("Win with a normal Bingo:");
        defaultText.setBackground(color);
        defaultText.setOpaque(true);
        defaultText.setBorder(BorderFactory.createLineBorder(bingoBlue, 2));
        defaultText.setHorizontalAlignment(JLabel.CENTER);
        bpPanel.add(defaultText);

        JLabel blackoutText = new JLabel("Win with all spaces on the board filled in:");
        blackoutText.setBackground(color);
        blackoutText.setOpaque(true);
        blackoutText.setBorder(BorderFactory.createLineBorder(bingoBlue, 1));
        blackoutText.setHorizontalAlignment(JLabel.CENTER);
        bpPanel.add(blackoutText);

        JLabel customText = new JLabel("Choose your own way to win:");
        customText.setBackground(color);
        customText.setOpaque(true);
        customText.setBorder(BorderFactory.createLineBorder(bingoBlue, 1));
        customText.setHorizontalAlignment(JLabel.CENTER);
        bpPanel.add(customText);

        bpPanel.add(defaultButton);
        bpPanel.add(blackoutButton);
        bpPanel.add(customButton);
        rhsPanel.add(bpPanel);

        trialField = new JTextField("10000");
        trialField.setHorizontalAlignment(JLabel.CENTER);
        playerField = new JTextField("10");
        playerField.setHorizontalAlignment(JLabel.CENTER);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2, 2));

        JLabel trialLabel = new JLabel("Number of Trials (no more than one million):");
        trialLabel.setBackground(color);
        trialLabel.setOpaque(true);
        trialLabel.setBorder(BorderFactory.createLineBorder(bingoBlue, 1));
        JLabel playerLabel = new JLabel("Number of Concurrent Players:");
        playerLabel.setBackground(color);
        playerLabel.setOpaque(true);
        playerLabel.setBorder(BorderFactory.createLineBorder(bingoBlue, 1));
        trialLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setHorizontalAlignment(JLabel.CENTER);

        fieldPanel.add(trialLabel);
        fieldPanel.add(playerLabel);
        fieldPanel.add(trialField);
        fieldPanel.add(playerField);
        rhsPanel.add(fieldPanel);

        instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(b);
        rhsPanel.add(instructionsButton);

        clearButton = new JButton("Clear Board");
        clearButton.addActionListener(b);

        add(rhsPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLayout(new GridLayout(1, 2));

        turnCounts = Collections.synchronizedList(new ArrayList<>());
        winnerCounts = Collections.synchronizedList(new ArrayList<>());
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == instructionsButton) {
                JFrame frameInstructions = new JFrame("Instructions");
                frameInstructions.setLayout(new GridLayout(5, 1));

                JLabel instructions1 = new JLabel("This program calculates the statistics for a given number of " +
                        "Bingo games and players.");
                JLabel instructions2 = new JLabel("The game ends on the turn that someone wins and collects data " +
                        "on the amounts of turns and concurrent winners.");
                JLabel instructions3 = new JLabel("You can create your custom win conditions by selecting 'Custom' " +
                        "and interacting with the board on the left side of the program.");
                JLabel instructions4 = new JLabel("Drag your mouse over any spaces that you want to make one " +
                        "win condition. Release and drag again to create another win condition.");
                JLabel instructions5 = new JLabel("Click a space if you want to remove it from the win conditions.");

                instructions1.setHorizontalAlignment(JLabel.CENTER);
                instructions2.setHorizontalAlignment(JLabel.CENTER);
                instructions3.setHorizontalAlignment(JLabel.CENTER);
                instructions4.setHorizontalAlignment(JLabel.CENTER);
                instructions5.setHorizontalAlignment(JLabel.CENTER);

                instructions1.setBackground(Color.WHITE);
                instructions2.setBackground(Color.WHITE);
                instructions3.setBackground(Color.WHITE);
                instructions4.setBackground(Color.WHITE);
                instructions5.setBackground(Color.WHITE);

                instructions1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                instructions2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                instructions3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                instructions4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                instructions5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                frameInstructions.add(instructions1);
                frameInstructions.add(instructions2);
                frameInstructions.add(instructions3);
                frameInstructions.add(instructions4);
                frameInstructions.add(instructions5);

                frameInstructions.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frameInstructions.setVisible(true);
                frameInstructions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            else if (e.getSource() == defaultButton) {
                defaultButton.setEnabled(false);
                graphicalBingoCard.setActive(false);
                graphicalBingoCard.clearBoard();
                bingoPattern = BingoPattern.DEFAULT;
                blackoutButton.setEnabled(true);

                if (clearButton.isEnabled()) {
                    replaceClearButton();
                }
            }

            else if (e.getSource() == blackoutButton) {
                blackoutButton.setEnabled(false);
                graphicalBingoCard.setActive(false);
                graphicalBingoCard.clearBoard();
                bingoPattern = BingoPattern.BLACKOUT;
                defaultButton.setEnabled(true);

                if (clearButton.isEnabled()) {
                    replaceClearButton();
                }
            }

            else if (e.getSource() == customButton) {
                customButton.setEnabled(false);
                graphicalBingoCard.setActive(true);
                bingoPattern = BingoPattern.CUSTOM;
                defaultButton.setEnabled(true);
                blackoutButton.setEnabled(true);
                bpPanel.remove(customButton);
                clearButton.setEnabled(true);
                bpPanel.add(clearButton);
                revalidate();
                repaint();
            }

            else if (e.getSource() == clearButton) {
                graphicalBingoCard.clearBoard();
            }

            else if (e.getSource() == executeButton) {
                if (convertToInteger(trialField.getText()) != -1 && convertToInteger(playerField.getText()) != -1) {
                     if (winConditions == null || (winConditions.size() == 0 && bingoPattern == BingoPattern.CUSTOM)) {
                        return;
                    }
                    
                    graphicalBingoCard.setActive(false);
                    trials = convertToInteger(trialField.getText());
                    players = convertToInteger(playerField.getText());

                    turnCounts.clear();
                    winnerCounts.clear();

                    long time = System.currentTimeMillis();

                    Thread [] threads = new Thread[THREAD_COUNT];
                    for (int i = 0; i < THREAD_COUNT; ++i) {
                        threads[i] = new Thread(new BingoStarter());
                        threads[i].start();
                    }

                    winConditions = graphicalBingoCard.getWinConditions();

                    if (bingoPattern == BingoPattern.CUSTOM) {
                        if (winConditions.size() != 0) {
                            try {
                                for (int i = 0; i < THREAD_COUNT; ++i) {
                                    threads[i].join();
                                }
                            } catch (InterruptedException e1) {
                                System.out.println("InterruptedException caught.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Please provide at least one " +
                                    "win condition.");
                        }
                    }
                    else {
                        try {
                            for (int i = 0; i < THREAD_COUNT; ++i) {
                                threads[i].join();
                            }
                        } catch (InterruptedException e1) {
                            System.out.println("InterruptedException caught.");
                        }
                    }
                    findMinMaxMean(turnCounts, winnerCounts);
                    graphicalBingoCard.setActive(true);
                }
            }
        }

        private void replaceClearButton() {
            clearButton.setEnabled(false);
            bpPanel.remove(clearButton);
            customButton.setEnabled(true);
            bpPanel.add(customButton);
            revalidate();
            repaint();
        }

        private int convertToInteger(String text) {
            text = text.replaceAll("[, ]", "");
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill out all fields completely.");
                return -1;
            }
            int temp;
            try {
                temp = Integer.parseInt(text);
                if (temp > 1E6) {
                    JOptionPane.showMessageDialog(null, "Fields must only contain integers below " +
                            "1,000,000 (one million) and greater than zero.");
                    return -1;
                } else if (temp < 1) {
                    JOptionPane.showMessageDialog(null, "Fields must only contain integers below " +
                            "1,000,000 (one million) and greater than zero.");
                    return -1;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Fields must only contain integers below " +
                        "1,000,000 (one million) and greater than zero.");
                return -1;
            }

            return temp;
        }
    }

    private void findMinMaxMean(List<Integer> turns, List<Integer> winners) {
        Collections.sort(turns);
        Collections.sort(winners);

        int turnMin = turns.get(0);
        int turnMax = turns.get(turns.size() - 1);
        int winnerMax = winners.get(winners.size() - 1);

        int turnSum = 0;
        int winnerSum = 0;
        for (int i = 0; i < turns.size(); ++i) {
            turnSum += turns.get(i);
            winnerSum += winners.get(i);
        }

        double turnMean = (double) turnSum / turns.size();
        double winnerMean = (double) winnerSum / winners.size();
        String results = "Turn Min: " + turnMin + " Turn Max: " + turnMax + " Winner Max: " + winnerMax +
                "\nTurn Mean: " + turnMean + " Winner Mean: " + winnerMean;
        JOptionPane.showMessageDialog(null, results);
    }

    private class BingoStarter implements Runnable {

        public void run() {
            for (int i = 0; i < trials / THREAD_COUNT; ++i) {
                Bingo bingo = new Bingo(players, bingoPattern, winConditions);
                turnCounts.add(bingo.getTurnCount());
                winnerCounts.add(bingo.getWinnerCount());
            }
        }
    }
}
