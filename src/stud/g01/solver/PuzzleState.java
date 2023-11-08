package stud.g01.solver;

import java.util.Arrays;
import java.util.*;
public class PuzzleState {
    public int[][] puzzle;
    public int gCost;
    public int hCostManhattan;
    public int hCostHamming;
    public int hCostEmptySpace;
    public PuzzleState parent;

    public PuzzleState(int[][] puzzle, int gCost, int hCostManhattan, int hCostHamming, int hCostEmptySpace) {
        this.puzzle = puzzle;
        this.gCost = gCost;
        this.hCostManhattan = hCostManhattan;
        this.hCostHamming = hCostHamming;
        this.hCostEmptySpace = hCostEmptySpace;
        this.parent = null;
    }

    public int getFCostManhattan() {
        return gCost + hCostManhattan;
    }

    public int getFCostHamming() {
        return gCost + hCostHamming;
    }

    public int getFCostEmptySpace() {
        return gCost + hCostEmptySpace;
    }

    public void setParent(PuzzleState parent) {
        this.parent = parent;
    }

    public void printSolution() {
        List<PuzzleState> steps = new ArrayList<>();
        PuzzleState currentState = this;
        while (currentState != null) {
            steps.add(currentState);
            currentState = currentState.parent;
        }

        for (int i = steps.size() - 1; i >= 0; i--) {
            PuzzleState step = steps.get(i);
            System.out.println("Step " + (steps.size() - i - 1) + ":");
            printPuzzle(step.puzzle);
        }
    }

    private void printPuzzle(int[][] puzzle) {
        for (int[] row : puzzle) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleState that = (PuzzleState) obj;
        return Arrays.deepEquals(puzzle, that.puzzle);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(puzzle);
    }
}


