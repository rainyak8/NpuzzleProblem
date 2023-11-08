package core.runner;

import algs4.util.StopwatchCPU;
import core.problem.Problem;
import core.problem.ProblemType;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Node;
import core.solver.algorithm.heuristic.HeuristicType;
import stud.g01.patterndatabase.Key;
import stud.g01.patterndatabase.PatternDatabase;
import stud.g01.problem.npuzzle.PuzzleBoard;
import stud.g01.solver.AStar;
import stud.g01.solver.IDAStar;
import stud.g01.solver.PuzzleState;
import stud.g01.solver.PuzzleVisualizer;
import stud.g01.problem.npuzzle.NPuzzleProblem;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static core.solver.algorithm.heuristic.HeuristicType.*;

/**
 * 对学生的搜索算法进行检测的主程序
 * arg0: 问题输入样例      resources/pathfinding.txt
 * arg1: 问题类型         PATHFINDING
 * arg2: 项目的哪个阶段    1
 * arg3: 各小组的Feeder   stud.runner.WalkerFeeder
 */
public final class SearchTester {
    public static ArrayList<Key> keys = new ArrayList<>();
    //同学们可以根据自己的需要，随意修改。
    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, FileNotFoundException {

        //根据args[3]提供的类名生成学生的EngineFeeder对象
        EngineFeeder feeder = (EngineFeeder)
                Class.forName(args[3])
                        .getDeclaredConstructor().newInstance();

    ////从文件读入所有输入样例的文本； args[0]：输入样例文件的相对路径
        Scanner scanner = new Scanner(new File(args[0]));
        ArrayList<String> problemLines = getProblemLines(scanner);

        //feeder从输入样例文本获取寻路问题的所有实例
        ArrayList<Problem> problems = feeder.getProblems(problemLines);
    ////问题实例读入到ArrayList中

        //当前问题的类型 args[1]    寻路问题，数字推盘，野人传教士过河等
        ProblemType type = ProblemType.valueOf(args[1]);
        //任务第几阶段 args[2]
        int step = Integer.parseInt(args[2]);

        //根据问题类型和当前阶段，获取所有启发函数的类型
        //寻路问题分别使用Grid距离和Euclid距离作为启发函数
        ArrayList<HeuristicType> heuristics = getHeuristicTypes(type, step);

        for (HeuristicType heuristicType : heuristics) {
            //solveProblems方法根据不同启发函数生成不同的searcher
            //从Feeder获取所使用的搜索引擎（AStar，IDAStar等），
            //solveProblems(problems, feeder.getAStar(heuristicType), heuristicType);
            //System.out.println();
        }
        //int[][] initial = {{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};
        //int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        int numTests = 1; // 你想进行的测试次数
        int size = 4;//此问题的size 注意当使用模式数据库时size必须为4
        boolean pattern = false;//是否使用Pattern模式数据库
        boolean Dijkstra = false;//是否使用Dijkstra Pattern 请注意要和pattern同时打开
        boolean robe = false;//是否使用随机测试
        boolean useAstar = true;//是否使用A*算法
        boolean useIDAstar = true;//是否使用IDA*算法

        boolean useManhattan = true;//是否使用曼哈顿距离启发函数
        boolean useHamming = false;//是否使用汉明距离启发函数
        boolean useEmptySpace = false;//是否使用空位距离启发函数


        System.out.println("生成模式数据库时间：");
        // 开始时间
        long stime = System.currentTimeMillis();
        PatternDatabase patternDatabase = new PatternDatabase(keys);//生成15数码模式数据库
        // 结束时间
        long etime = System.currentTimeMillis();

        // 计算执行时间
        System.out.printf("%d 毫秒.", (etime - stime));
        for (int i = 0; i < numTests; i++) {
            int[][] initial = null;
            int[][] goal = null;

            for (int i9 = 0; i9 < numTests; i9++) {
                if (size == 4) {
                    if (robe) {
                        initial = generateRandomState(size);
                        goal = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
                    } else {
                        //initial = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {0, 14, 15, 13}};
                        //initial = new int[][]{{15, 14, 13, 12}, {11, 10, 9, 8}, {7, 6, 5, 4},{3, 1, 2, 0}};
                        //initial = new int[][]{{2, 9, 5, 11}, {8, 3, 4, 14}, {7, 10, 1, 12},{0, 15, 6, 13}};
                        //initial = new int[][]{{4, 7, 0, 9}, {12, 10, 11, 8}, {14, 6, 15, 1},{2, 5, 3, 13}};
                        //initial = new int[][]{{12, 1, 5, 6}, {2, 11, 7, 9}, {14, 10, 0, 4},{15, 3, 13, 8}};
                        initial = new int[][]{{8, 13, 0, 6}, {1, 15, 9, 14}, {3, 4, 5, 11}, {7, 2, 10, 12}};
                        goal = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
                    }
                } else if (size == 3) {
                    if (robe) {
                        initial = generateRandomState(size);
                        goal = generateRandomState(size);
                    } else {
                        initial = new int[][]{{5, 0, 8}, {4, 2, 1}, {7, 3, 6}};
                        goal = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
                    }
                }
                // 在这里可以使用 initial 和 goal 进行后续操作
            }


            //int[][] initial = {{15, 14, 13, 12}, {11, 10, 9, 8}, {7, 6, 5, 4},{3, 1, 2, 0}};


            int[] array1 = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 1, 2, 0};
            int[] array2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
            if (pattern){
                int index = 0;

                for (int i1 = 0; i1 < size; i1++) {
                    for (int j = 0; j < size; j++) {
                        array1[index++] = initial[i][j];
                    }
                }
            }
            PuzzleBoard initialState = new PuzzleBoard(array1, size);//存放初始状态数组到名为initialState的PuzzleBoard类
            PuzzleBoard goal1 = new PuzzleBoard(array2, size);//目标状态
            NPuzzleProblem n1 = new NPuzzleProblem(initialState, goal1, size);

            n1.solvable();

            PuzzleState result1 = null;
            PuzzleState result2 = null;
            PuzzleState result3 = null;
            final PuzzleState[] result4 = {null};
            final PuzzleState[] result5 = {null};
            final PuzzleState[] result6 = {null};
            if (useAstar) {
                if(Dijkstra){
                        // 使用Dijkstra距离作为启发函数
                        long startTime = System.currentTimeMillis();
                        result1 = AStar.aStar(initial, goal, pattern, true, false, false, Dijkstra,patternDatabase);
                        long endTime = System.currentTimeMillis();
                        if (result1 != null) {
                            PuzzleVisualizer visualizer1 = new PuzzleVisualizer(initial);
                            System.out.println("A* Solution found using Dijkstra distance:");
                            visualizer1.updatePuzzle(result1.puzzle);
                            List<PuzzleState> steps1 = new ArrayList<>();
                            PuzzleState currentState = result1;
                            while (currentState != null) {
                                steps1.add(currentState);
                                currentState = currentState.parent;
                            }
                            Collections.reverse(steps1);
                            visualizer1.visualizeSteps(steps1);
                            result1.printSolution();
                        } else {
                            System.out.println("No A* solution found using Manhattan distance.");
                        }
                        long totalTime = endTime - startTime;
                        System.out.println("\nTotal running time (ms): " + totalTime);
                }else{
                    if (useManhattan) {
                        // 使用曼哈顿距离作为启发函数
                        long startTime = System.currentTimeMillis();
                        result1 = AStar.aStar(initial, goal, pattern, true, false, false, Dijkstra,patternDatabase);
                        long endTime = System.currentTimeMillis();
                        if (result1 != null) {
                            PuzzleVisualizer visualizer1 = new PuzzleVisualizer(initial);
                            System.out.println("A* Solution found using Manhattan distance:");
                            visualizer1.updatePuzzle(result1.puzzle);
                            List<PuzzleState> steps1 = new ArrayList<>();
                            PuzzleState currentState = result1;
                            while (currentState != null) {
                                steps1.add(currentState);
                                currentState = currentState.parent;
                            }
                            Collections.reverse(steps1);
                            visualizer1.visualizeSteps(steps1);
                            result1.printSolution();
                        } else {
                            System.out.println("No A* solution found using Manhattan distance.");
                        }
                        long totalTime = endTime - startTime;
                        System.out.println("\nTotal running time (ms): " + totalTime);
                    }

                    if (useHamming) {
                        // 使用汉明距离作为启发函数
                        long startTime = System.currentTimeMillis();
                        result2 = AStar.aStar(initial, goal, pattern, false, true, false, Dijkstra,patternDatabase);
                        long endTime = System.currentTimeMillis();
                        if (result2 != null) {
                            PuzzleVisualizer visualizer2 = new PuzzleVisualizer(initial);
                            System.out.println("A* Solution found using Hamming distance:");
                            visualizer2.updatePuzzle(result2.puzzle);
                            List<PuzzleState> steps2 = new ArrayList<>();
                            PuzzleState currentState = result2;
                            while (currentState != null) {
                                steps2.add(currentState);
                                currentState = currentState.parent;
                            }
                            Collections.reverse(steps2);
                            visualizer2.visualizeSteps(steps2);
                            result2.printSolution();
                        } else {
                            System.out.println("No A* solution found using Hamming distance.");
                        }
                        long totalTime = endTime - startTime;
                        System.out.println("\nTotal running time (ms): " + totalTime);
                    }

                    if (useEmptySpace) {
                        // 使用空位距离作为启发函数
                        long startTime = System.currentTimeMillis();
                        result3 = AStar.aStar(initial, goal, pattern, false, false, true, Dijkstra,patternDatabase);
                        long endTime = System.currentTimeMillis();
                        if (result3 != null) {
                            PuzzleVisualizer visualizer3 = new PuzzleVisualizer(initial);
                            System.out.println("A* Solution found using EmptySpace distance:");
                            visualizer3.updatePuzzle(result3.puzzle);
                            List<PuzzleState> steps3 = new ArrayList<>();
                            PuzzleState currentState = result3;
                            while (currentState != null) {
                                steps3.add(currentState);
                                currentState = currentState.parent;
                            }
                            Collections.reverse(steps3);
                            visualizer3.visualizeSteps(steps3);
                            result3.printSolution();
                        } else {
                            System.out.println("No A* solution found using EmptySpace distance.");
                        }
                        long totalTime = endTime - startTime;
                        System.out.println("\nTotal running time (ms): " + totalTime);
                    }
                }

            }

            if (useIDAstar) {
                if (Dijkstra) {
                        long startTime = System.currentTimeMillis();

                        int[][] finalInitial = initial;
                        int[][] finalGoal = goal;
                        Thread solverThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                result4[0] = IDAStar.idaStar(finalInitial, finalGoal, pattern, true, false, false, Dijkstra,patternDatabase);
                            }
                        });
                        solverThread.start();
                        try {
                            solverThread.join(60000); // 等待10秒钟
                            solverThread.interrupt(); // 中断求解线程
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long endTime = System.currentTimeMillis();
                        if (result4[0] != null) {
                            System.out.println("IDA* Solution found using Dijkstra distance:");
                            result4[0].printSolution();
                        } else {
                            System.out.println("No IDA* solution found using Dijkstra distance.");
                        }
                        long totalTime = endTime - startTime;
                        if (totalTime > 10000) {
                            System.out.println("\nTotal running time (ms) more than  " + totalTime);
                        } else {
                            System.out.println("\nTotal running time (ms): " + totalTime);
                        }
                } else {
                    if (useManhattan) {

                        long startTime = System.currentTimeMillis();
/*
                        int[][] finalInitial = initial;
                        int[][] finalGoal = goal;
                        Thread solverThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                result4[0] = IDAStar.idaStar(finalInitial, finalGoal, pattern, true, false, false, Dijkstra,patternDatabase);
                            }
                        });
                        solverThread.start();
                        try {
                            solverThread.join(600000); // 等待10秒钟
                            solverThread.interrupt(); // 中断求解线程
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
*/
                        IDAStar.idaStar(initial, goal, pattern,true, false, false, Dijkstra, patternDatabase);
                        long endTime = System.currentTimeMillis();
                        if (result4[0] != null) {
                            System.out.println("IDA* Solution found using Manhattan distance:");
                            result4[0].printSolution();
                        } else {
                            System.out.println("No IDA* solution found using Manhattan distance.");
                        }
                        long totalTime = endTime - startTime;
                        if (totalTime > 10000) {
                            System.out.println("\nTotal running time (ms) more than  " + totalTime);
                        } else {
                            System.out.println("\nTotal running time (ms): " + totalTime);
                        }
                    }

                    if (useHamming) {
                        long startTime = System.currentTimeMillis();
                        int[][] finalInitial1 = initial;
                        int[][] finalGoal1 = goal;
                        Thread solverThread = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                result5[0] = IDAStar.idaStar(finalInitial1, finalGoal1, pattern, true, false, false,Dijkstra, patternDatabase);

                            }
                        });
                        solverThread.start();
                        try {
                            solverThread.join(60000); // 等待10秒钟
                            solverThread.interrupt(); // 中断求解线程
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long endTime = System.currentTimeMillis();
                        if (result5[0] != null) {
                            System.out.println("IDA* Solution found using Hamming distance:");
                            result5[0].printSolution();
                        } else {
                            System.out.println("No IDA* solution found using Hamming distance.");
                        }

                        long totalTime = endTime - startTime;
                        if (totalTime > 1000) {
                            System.out.println("\nTotal running time (ms) more than  " + totalTime);
                        } else {
                            System.out.println("\nTotal running time (ms): " + totalTime);
                        }
                    }

                    if (useEmptySpace) {
                        long startTime = System.currentTimeMillis();
                        int[][] finalInitial2 = initial;
                        int[][] finalGoal2 = goal;
                        Thread solverThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                result6[0] = IDAStar.idaStar(finalInitial2, finalGoal2, pattern, true, false, false, Dijkstra,patternDatabase);
                            }
                        });
                        solverThread.start();
                        try {
                            solverThread.join(60000); // 等待10秒钟
                            solverThread.interrupt(); // 中断求解线程
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long endTime = System.currentTimeMillis();
                        if (result6[0] != null) {
                            System.out.println("IDA* Solution found using EmptySpace distance:");
                            result6[0].printSolution();
                        } else {
                            System.out.println("No IDA* solution found using EmptySpace distance.");
                        }

                        long totalTime = endTime - startTime;
                        if (totalTime > 1000) {
                            System.out.println("\nTotal running time (ms) more than  " + totalTime);
                        } else {
                            System.out.println("\nTotal running time (ms): " + totalTime);
                        }
                    }
                }
            }
        }
    }

    /**
     * 随机获得样例
     * @return
     */
    public static int[][] generateRandomState(int size) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size*size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int[][] state = new int[size][size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state[i][j] = numbers.get(index++);
            }
        }

        return state;
    }

    /**
     * 根据问题类型和当前阶段，获取所有启发函数的类型
     * @param type
     * @param step
     * @return
     */
    private static ArrayList<HeuristicType> getHeuristicTypes(ProblemType type, int step) {
        //求解当前问题在当前阶段可用的启发函数类型列表
        ArrayList<HeuristicType> heuristics = new ArrayList<>();
        //根据不同的问题类型，进行不同的测试
        if (type == ProblemType.PATHFINDING) {
            heuristics.add(PF_GRID);
            heuristics.add(PF_EUCLID);
        }
        else {
            //NPuzzle问题的第一阶段，使用不在位将牌和曼哈顿距离
            if (step == 1) {
                heuristics.add(MISPLACED);
                heuristics.add(MANHATTAN);
            }
            //NPuzzle问题的第三阶段，使用Disjoint Pattern
            else if (step == 3){
                heuristics.add(DISJOINT_PATTERN);
            }
        }
        return heuristics;
    }

    /**
     * 使用给定的searcher，求解问题集合中的所有问题，同时使用解检测器对求得的解进行检测
     * @param problems     问题集合
     * @param searcher     searcher
     * @param heuristicType 使用哪种启发函数？
     */
    private static void solveProblems(ArrayList<Problem> problems, AbstractSearcher searcher, HeuristicType heuristicType) {
        for (Problem problem : problems) {
            // 使用AStar引擎求解问题
            StopwatchCPU timer1 = new StopwatchCPU();
            Deque<Node> path = searcher.search(problem);
            double time1 = timer1.elapsedTime();

            if (path == null) {
                System.out.println("No Solution" + "，执行了" + time1 + "s，"+
                        "共生成了" + searcher.nodesGenerated() + "个结点，" +
                        "扩展了" + searcher.nodesExpanded() + "个结点");
                continue;
            }

            // 解路径的可视化
            problem.showSolution(path);

            System.out.println("启发函数：" + heuristicType + "，解路径长度：" + path.size() + "，执行了" + time1 + "s，" +
                    "共生成了" + searcher.nodesGenerated() + "个结点，" +
                    "扩展了" + searcher.nodesExpanded() + "个结点");
        }
    }

    /**
     * 从文件读入问题实例的字符串，放入字符串数组里
     * @param scanner
     * @return
     */
    public static ArrayList<String> getProblemLines(Scanner scanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()){
            lines.add(scanner.nextLine());
        }
        return lines;
    }

}