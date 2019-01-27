import java.util.ArrayList;
import java.util.HashSet;
import java.util.SplittableRandom;
import java.util.Set;

public class Bingo {
    private SimulatedBingoCard [] boards;
    private int turnCount, winnerCount;

    // separates into default, blackout, and custom for best time and performance
    // simulates a Bingo game abstractly
    public Bingo(int playerCount, BingoPattern bingoPattern, ArrayList<WinCondition> winConditions) {
        boards = new SimulatedBingoCard[playerCount];
        for (int i = 0; i < playerCount; ++i) {
            boards[i] = new SimulatedBingoCard();
        }
        Set<Integer> bingoBallValues = new HashSet<>();
        SplittableRandom rand = new SplittableRandom();
        if (bingoPattern == BingoPattern.DEFAULT) {
            for (turnCount = 0; turnCount < 75; ++turnCount) {
                int bingoBallValue;
                do {
                    bingoBallValue = rand.nextInt(75) + 1;
                } while (bingoBallValues.contains(bingoBallValue));
                bingoBallValues.add(bingoBallValue);

                for (int i = 0; i < playerCount; ++i) {
                    boards[i].markSpace(bingoBallValue);
                    if (turnCount > 2) {
                        if (isWonByDefault(boards[i].getCardNumbers())) {
                            ++winnerCount;
                        }
                    }
                }

                if (winnerCount > 0) {
                    break;
                }
            }
        } else if (bingoPattern == BingoPattern.BLACKOUT) {
            for (turnCount = 0; turnCount < 75; ++turnCount) {
                int bingoBallValue;
                do {
                    bingoBallValue = rand.nextInt(75) + 1;
                } while (bingoBallValues.contains(bingoBallValue));
                bingoBallValues.add(bingoBallValue);

                for (int i = 0; i < playerCount; ++i) {
                    boards[i].markSpace(bingoBallValue);
                    if (turnCount > 24 && isWonByBlackout(boards[i].getCardNumbers())) {
                        ++winnerCount;
                    }
                }

                if (winnerCount > 0) {
                    break;
                }
            }
        } else if (bingoPattern == BingoPattern.CUSTOM) {
            bingoBallValues = new HashSet<>();
            rand = new SplittableRandom();
            for (turnCount = 0; turnCount < 75; ++turnCount) {
                int bingoBallValue;
                do {
                    bingoBallValue = rand.nextInt(75) + 1;
                } while (bingoBallValues.contains(bingoBallValue));
                bingoBallValues.add(bingoBallValue);

                for (int i = 0; i < playerCount; ++i) {
                    boards[i].markSpace(bingoBallValue);
                    if (isWon(boards[i].getCardNumbers(), winConditions)) {
                        ++winnerCount;
                    }
                }

                if (winnerCount > 0) {
                    break;
                }
            }
        }
    }

    // checks to see if Custom game is won
    private boolean isWon(int [][] numbers, ArrayList<WinCondition> winConditions) {
        for (WinCondition winCondition : winConditions) {
            if (winCondition.isSatisfied(numbers, 0)) {
                return true;
            }
        }

        return false;
    }

    // checks to see if default Bingo is won
    private boolean isWonByDefault(int [][] numbers) {
        boolean allZeroes;
        for (int i = 0; i < 5; ++i) {
            allZeroes = true;
            for (int j = 0; j < 5; j++) {
                if (numbers[i][j] != 0) {
                    allZeroes = false;
                    break;
                }
            }

            if (allZeroes)
                return true;
        }

        for (int i = 0; i < 5; ++i) {
            allZeroes = true;
            for (int j = 0; j < 5; j++) {
                if (numbers[j][i] != 0) {
                    allZeroes = false;
                    break;
                }
            }

            if (allZeroes)
                return true;
        }

        return  ((numbers[0][0] == 0 && numbers[1][1] == 0 && numbers[3][3] == 0 && numbers[4][4] == 0) ||
                (numbers[4][0] == 0 && numbers[3][1] == 0 && numbers[1][3] == 0 && numbers[0][4] == 0));
    }

    // checks to see if blackout Bingo is won
    private boolean isWonByBlackout(int [][] numbers) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; j++) {
                if (numbers[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getTurnCount() {
        return turnCount + 1;
    }

    public int getWinnerCount() {
        return winnerCount;
    }
}