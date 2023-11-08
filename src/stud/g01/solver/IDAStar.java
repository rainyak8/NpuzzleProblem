package stud.g01.solver;
import stud.g01.patterndatabase.PatternDatabase;

import java.util.*;

public class IDAStar {
    private static List<PuzzleState> steps;

    public static PuzzleState idaStar(int[][] initial, int[][] goal, boolean pattern, boolean useManhattan, boolean useHamming, boolean useEmptySpace,boolean useDijkstra ,PatternDatabase patternDatabase) {
        int maxDepth = 0;
        steps = new ArrayList<>();

        while (true) {
            Set<String> closedSet = new HashSet<>();
            PuzzleState result = search(initial, goal, 0, maxDepth, pattern,useManhattan, useHamming, useEmptySpace, closedSet, useDijkstra, patternDatabase);

            if (result != null) {
                return result;
            }

            if (maxDepth == Integer.MAX_VALUE) {
                return null; // No solution found
            }

            maxDepth++;
        }
    }

    public static PuzzleState search(int[][] current, int[][] goal, int gCost, int maxDepth, boolean pattern, boolean useManhattan, boolean useHamming, boolean useEmptySpace, Set<String> closedSet,boolean useDijkstra, PatternDatabase patternDatabase) {
        int fCost = gCost;

        if(pattern){
            if(useDijkstra){
                fCost += Heuristics.dijkstraDistance_15(current,patternDatabase);
            }else{
                if (useManhattan) {
                    fCost += Heuristics.manhattanDistance_15(current,patternDatabase);
                } else if (useHamming) {
                    fCost += Heuristics.hammingDistance_15(current, patternDatabase);
                } else if (useEmptySpace) {
                    fCost += Heuristics.zeroDistance_15(current, patternDatabase);
                }
            }
        }else{
            if (useManhattan) {
                fCost += Heuristics.manhattanDistance(current, goal);
            } else if (useHamming) {
                fCost += Heuristics.hammingDistance(current, goal);
            } else if (useEmptySpace) {
                fCost += Heuristics.emptySpaceDistance(current, goal);
            }
        }


        if (fCost > maxDepth) {
            return null;
        }

        if (Arrays.deepEquals(current, goal)) {
            return new PuzzleState(current, gCost, 0, 0, 0); // Solution found
        }

        int min = Integer.MAX_VALUE;

        List<PuzzleState> neighbors = AStar.getNeighbors(new PuzzleState(current, gCost, 0, 0, 0), goal);

        for (PuzzleState neighbor : neighbors) {
            String neighborString = Arrays.deepToString(neighbor.puzzle);
            if (!closedSet.contains(neighborString)) {
                closedSet.add(neighborString);
                PuzzleState result = search(neighbor.puzzle, goal, neighbor.gCost, maxDepth, pattern,useManhattan, useHamming, useEmptySpace, closedSet, useDijkstra,patternDatabase);

                if (result != null && Arrays.deepEquals(result.puzzle, goal)) {
                    return result; // Solution found
                }

                if (result != null) {
                    int hCost = 0;
                    if(pattern){
                        if(useDijkstra){
                            hCost = Heuristics.dijkstraDistance_15(current,patternDatabase);
                        }else{
                            hCost = useManhattan ? Heuristics.manhattanDistance_15(neighbor.puzzle, patternDatabase) :
                                    useHamming ? Heuristics.manhattanDistance_15(neighbor.puzzle, patternDatabase) :
                                            useEmptySpace ? Heuristics.zeroDistance_15(neighbor.puzzle, patternDatabase) : 0;
                        }
                    }else{
                        hCost = useManhattan ? Heuristics.manhattanDistance(neighbor.puzzle, goal) :
                                useHamming ? Heuristics.hammingDistance(neighbor.puzzle, goal) :
                                        useEmptySpace ? Heuristics.emptySpaceDistance(neighbor.puzzle, goal) : 0;
                    }
                    int fCostResult = result.gCost + hCost;

                    if (fCostResult < min) {
                        min = fCostResult;
                    }
                }
            }
        }

        return min == Integer.MAX_VALUE ? null : new PuzzleState(current, gCost, 0, 0, 0);
    }

    public static List<PuzzleState> getSteps() {
        return steps;
    }
}
