package stud.g01.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;
import java.util.Deque;

import stud.g01.patterndatabase.Key;
import stud.g01.patterndatabase.PatternDatabase;

public class NPuzzleProblem extends Problem {
    public NPuzzleProblem(State initialState, State goal) {
        super(initialState, goal);
    }

    public NPuzzleProblem(State initialState, State goal, int size) {
        super(initialState, goal, size);
    }

    /**
     * 判断问题是否可解
     * @return
     */
    @Override
    public boolean solvable() {
        int a = totalInverseCount(((PuzzleBoard)initialState).puzzle)%2;
        int b = totalInverseCount(((PuzzleBoard)goal).puzzle)%2;
        if(a == b) {
            System.out.println("有解");
            return true;
        }
        System.out.println("无解");
        return false;//总逆序数奇偶性不同无解
    }

    private int totalInverseCount(int[] arr) {
        int n = arr.length;
        int count = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[i] && arr[i] != 0 && arr[j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }//求总逆序数

    /**
     * 汉明距离:对应位置不同数字的个数
     * @return
     */
    public int hammingDistance(){
        int Distance = 0;
        for(int i=0;i<size*size;i++){
            if(((PuzzleBoard)initialState).puzzle[i] != ((PuzzleBoard)goal).puzzle[i]) Distance++;
        }
        System.out.println("汉明距离："+Distance);
        return Distance;
    }
    // 15数码问题
    public int hammingDistance_15(PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = ((PuzzleBoard)initialState).puzzle[4*i+j];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[0];//15数码问题第i个模式的汉明距离
        }
        return count;
    }
    /**
     * 曼哈顿距离:当前位置和目标位置之间的横向和纵向距离之和
     */
    public int manhattanDistance(){
        int Distance=0;
        for(int i=0;i<size*size;i++){
            for(int j=0;j<size*size;j++){
                if(((PuzzleBoard)initialState).puzzle[i]==((PuzzleBoard)goal).puzzle[j]&&((PuzzleBoard)initialState).puzzle[i]!=0){
                    Distance = Distance + (Math.abs(i/size-j/size))+(Math.abs(i%size-j%size));;
                    break;
                }
            }
        }
        System.out.println("曼哈顿距离："+Distance);
        return Distance;
    }
    // 15数码问题
    public int manhattanDistance_15(PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = ((PuzzleBoard)initialState).puzzle[4*i+j];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[1];
        }
        return count;
    }
    /**
     * 空位距离：空格的当前位置与目标位置之间的横向和纵向距离之和
     */
    public int zeroDistance(){
        int Distance=0;
        for(int i=0;i<size*size;i++){
            if(((PuzzleBoard)initialState).puzzle[i]==0){
                for(int j=0;j<size*size;j++){
                    if(((PuzzleBoard)goal).puzzle[j]==0){
                        Distance = Distance+(Math.abs(i-j)/size)+(Math.abs(i-j)%size);
                        break;
                    }
                }
            }
        }
        System.out.println("空位距离："+Distance);
        return Distance;
    }
    // 15数码问题
    public int zeroDistance_15(PatternDatabase patternDatabase){
        int count = 0;
        int[] part = new int[4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                part[j] = ((PuzzleBoard)initialState).puzzle[4*i+j];
            }
            Key k = new Key(part);
            count = count + patternDatabase.puzzle_15[i].get(k.key)[2];
        }
        return count;
    }
    @Override
    public int stepCost(State state, Action action) {
        return 0;
    }

    @Override
    public boolean applicable(State state, Action action) {
        return false;
    }

    @Override
    public void showSolution(Deque<Node> path) {

    }
}

