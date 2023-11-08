package stud.g01.solver;

import stud.g01.patterndatabase.PatternDatabase;
import stud.g01.problem.npuzzle.NPuzzleProblem;

import java.util.*;

public class AStar {
    public static List<PuzzleState> getNeighbors(PuzzleState current, int[][] goal) {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int zeroRow = -1;
        int zeroCol = -1;

        outerloop:
        for (int i = 0; i < current.puzzle.length; i++) {
            for (int j = 0; j < current.puzzle[i].length; j++) {
                if (current.puzzle[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break outerloop;
                }
            }
        }

        for (int[] move : moves) {
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];

            if (newRow >= 0 && newRow < current.puzzle.length && newCol >= 0 && newCol < current.puzzle[0].length) {
                int[][] newPuzzle = new int[current.puzzle.length][current.puzzle[0].length];
                for (int i = 0; i < current.puzzle.length; i++) {
                    for (int j = 0; j < current.puzzle[i].length; j++) {
                        newPuzzle[i][j] = current.puzzle[i][j];
                    }
                }

                newPuzzle[zeroRow][zeroCol] = current.puzzle[newRow][newCol];
                newPuzzle[newRow][newCol] = 0;

                int gCost = current.gCost + 1;
                int hCostManhattan = Heuristics.manhattanDistance(newPuzzle, goal);
                int hCostHamming = Heuristics.hammingDistance(newPuzzle, goal);
                int hCostEmptySpace = Heuristics.emptySpaceDistance(newPuzzle, goal);

                PuzzleState newState = new PuzzleState(newPuzzle, gCost, hCostManhattan, hCostHamming, hCostEmptySpace);
                newState.parent = current;
                neighbors.add(newState);
            }
        }

        return neighbors;
    }

    public static PuzzleState aStar(int[][] initial, int[][] goal, boolean pattern ,boolean useManhattan, boolean useHamming, boolean useEmptySpace,boolean useDijkstra , PatternDatabase patternDatabase) {
        PriorityQueue<PuzzleState> openList = new PriorityQueue<>(Comparator.comparingInt(
                useManhattan ? PuzzleState::getFCostManhattan :
                        useHamming ? PuzzleState::getFCostHamming :
                                useEmptySpace ? PuzzleState::getFCostEmptySpace : null));

        Set<String> closedSet = new HashSet<>();
        int hCostManhattan = 0;
        int hCostHamming =  0;
        int hCostEmptySpace = 0;
        if(pattern){
            hCostManhattan = useManhattan ? Heuristics.manhattanDistance_15(initial,patternDatabase) : 0;
            hCostHamming = useHamming ? Heuristics.hammingDistance_15(initial, patternDatabase) : 0;
            hCostEmptySpace = useEmptySpace ? Heuristics.zeroDistance_15(initial, patternDatabase) : 0;
        }else{
            hCostManhattan = useManhattan ? Heuristics.manhattanDistance(initial,goal) : 0;
            hCostHamming = useHamming ? Heuristics.hammingDistance(initial, goal) : 0;
            hCostEmptySpace = useEmptySpace ? Heuristics.emptySpaceDistance(initial, goal) : 0;
        }
        if(useDijkstra){
            hCostManhattan = Heuristics.dijkstraDistance_15(initial,patternDatabase);
            hCostHamming = 0;
            hCostEmptySpace = 0;
        }
        PuzzleState initialState = new PuzzleState(initial, 0, hCostManhattan, hCostHamming, hCostEmptySpace);
        openList.add(initialState);

        while (!openList.isEmpty()) {
            PuzzleState currentState = openList.poll();

            if (Arrays.deepEquals(currentState.puzzle, goal)) {
                return currentState;
            }

            closedSet.add(Arrays.deepToString(currentState.puzzle));

            List<PuzzleState> neighbors = getNeighbors(currentState, goal);
            for (PuzzleState neighbor : neighbors) {
                String neighborString = Arrays.deepToString(neighbor.puzzle);
                if (!closedSet.contains(neighborString)) {
                    openList.add(neighbor);
                }
            }
        }

        return null;
    }
}



