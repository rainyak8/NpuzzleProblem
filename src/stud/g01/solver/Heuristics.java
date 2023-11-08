package stud.g01.solver;

import stud.g01.patterndatabase.Key;
import stud.g01.patterndatabase.PatternDatabase;
import stud.g01.problem.npuzzle.PuzzleBoard;

public class Heuristics {
    public static int manhattanDistance(int[][] current, int[][] goal) {
        int distance = 0;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != 0) {
                    int[] goalPosition = findPosition(goal, current[i][j]);
                    distance += Math.abs(i - goalPosition[0]) + Math.abs(j - goalPosition[1]);
                }
            }
        }
        return distance;
    }
    public static int hammingDistance_15(int[][] current, PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        int rows = current.length;
        int cols = current[0].length;
        int[] flattenedArray = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flattenedArray[index++] = current[i][j];
            }
        }
        index = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = flattenedArray[index++];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[0];//15数码问题第i个模式的汉明距离
        }
        return count;
    }

    public static int zeroDistance_15(int[][] current, PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        int rows = current.length;
        int cols = current[0].length;
        int[] flattenedArray = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flattenedArray[index++] = current[i][j];
            }
        }
        index = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = flattenedArray[index++];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[2];
        }
        return count;
    }

    public static int dijkstraDistance_15(int[][] current, PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        int rows = current.length;
        int cols = current[0].length;
        int[] flattenedArray = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flattenedArray[index++] = current[i][j];
            }
        }
        index = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = flattenedArray[index++];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[3];
        }
        return count;
    }

    public static int manhattanDistance_15(int[][] current, PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        int rows = current.length;
        int cols = current[0].length;
        int[] flattenedArray = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flattenedArray[index++] = current[i][j];
            }
        }
        index = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = flattenedArray[index++];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[1];
        }
        return count;
    }

    public static int hammingDistance(int[][] current, int[][] goal) {
        int distance = 0;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != 0 && current[i][j] != goal[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public static int emptySpaceDistance(int[][] current, int[][] goal) {
        int distance = 0;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != goal[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public static int[] findPosition(int[][] state, int value) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == value) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}

