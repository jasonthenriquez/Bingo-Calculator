import java.util.HashSet;
import java.util.SplittableRandom;
import java.util.Set;

public class SimulatedBingoCard implements BingoCard {
    private int [][] cardNumbers;

    // simulates a Bingo card quickly
    public SimulatedBingoCard() {
        cardNumbers = new int[5][5];
        generateNumbers(cardNumbers);
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

    // searches the card for the Bingo ball that was called
    public void markSpace(int key) {
        if (key <= 15) {
            markSpace(0, key);
        } else if (key <= 30) {
            markSpace(1, key);
        } else if (key <= 45) {
            markSpace(2, key);
        } else if (key <= 60) {
            markSpace(3, key);
        } else if (key <= 75) {
            markSpace(4, key);
        }
    }

    private void markSpace(int whichArray, int key) {
        for (int i = 0; i < 5; ++i) {
            if (cardNumbers[whichArray][i] == key) {
                cardNumbers[whichArray][i] = 0;
                break;
            }
        }
    }

    public int [][] getCardNumbers() {
        return cardNumbers;
//        int [][] temp = new int [5][5];
//        for (int i = 0; i < 5; ++i) {
//            System.arraycopy(cardNumbers[i], 0, temp[i], 0, 5);
//        }
//        return temp; better OOP but worsens performance significantly
    }
}
