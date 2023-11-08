package stud.g01.solver;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PuzzleVisualizer extends JFrame {
    private int[][] puzzle;
    private int tileSize = 100;

    public PuzzleVisualizer(int[][] puzzle) {
        this.puzzle = puzzle;

        setTitle("Puzzle Visualizer");
        setSize(puzzle[0].length * tileSize, puzzle.length * tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updatePuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        repaint();
    }

    public void visualizeSteps(List<PuzzleState> steps) {
        Thread visualizationThread = new Thread(() -> {
            for (int i = 0; i < steps.size(); i++) {
                PuzzleState step = steps.get(i);
                updatePuzzle(step.puzzle);
                repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        visualizationThread.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                int value = puzzle[i][j];
                if (value != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    g.drawString(String.valueOf(value), j * tileSize + tileSize / 2 - 10, i * tileSize + tileSize / 2 + 10);
                }
            }
        }
    }
}
