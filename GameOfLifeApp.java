import java.util.*;

class Cell {
    int x, y;
    Cell(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }

    @Override
    public String toString() { return x + ", " + y; }
}

class GameOfLife {
    private final Set<Cell> liveCells;

    public GameOfLife(Set<Cell> initialCells) {
        this.liveCells = new HashSet<>(initialCells);
    }

    public Set<Cell> nextGeneration() {
        Set<Cell> newGen = new HashSet<>();
        Map<Cell, Integer> neighborCount = new HashMap<>();

        for (Cell cell : liveCells) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    Cell neighbor = new Cell(cell.x + dx, cell.y + dy);
                    neighborCount.put(neighbor, neighborCount.getOrDefault(neighbor, 0) + 1);
                }
            }
        }

        for (Map.Entry<Cell, Integer> entry : neighborCount.entrySet()) {
            Cell cell = entry.getKey();
            int count = entry.getValue();
            if (count == 3 || (count == 2 && liveCells.contains(cell))) {
                newGen.add(cell);
            }
        }

        return newGen;
    }
}

public class GameOfLifeApp {
    public static void main(String[] args) {
        Set<Cell> inputC = new HashSet<>(Arrays.asList(
                new Cell(1, 1), new Cell(1, 0), new Cell(1, 2)
        ));

        GameOfLife game = new GameOfLife(inputC);
        Set<Cell> next = game.nextGeneration();

        System.out.println("Next Generation:");
        next.forEach(System.out::println);
    }
}
