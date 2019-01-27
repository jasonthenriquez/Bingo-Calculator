import java.util.ArrayList;

public class WinCondition {
    private ArrayList<Integer> columns, rows;

    // each space in a win condition is viewed as a column and row combination
    public WinCondition() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public void addSpace(int column, int row) {
        columns.add(column);
        rows.add(row);
    }

    public ArrayList<Integer> getColumns() {
        return new ArrayList<>(columns);
    }

    public ArrayList<Integer> getRows() {
        return new ArrayList<>(rows);
    }

    public void remove(int index) {
        columns.remove(index);
        rows.remove(index);
    }

    // uses recursion to see if each position is 0
    public boolean isSatisfied(int [][] numbers, int index) {
        boolean conditionsMet = true;
        if (columns.size() != index) {
            if (numbers[columns.get(index)][rows.get(index)] == 0) {
                conditionsMet = isSatisfied(numbers, ++index);
            } else {
                conditionsMet = false;
            }
        }

        return conditionsMet;
    }

    // shares the win condition to the rest of the program
    public WinCondition copy() {
        WinCondition copy = new WinCondition();
        copy.columns = this.getColumns();
        copy.rows = this.getRows();

        return copy;
    }
}
