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
 * ��ѧ���������㷨���м���������
 * arg0: ������������      resources/pathfinding.txt
 * arg1: ��������         PATHFINDING
 * arg2: ��Ŀ���ĸ��׶�    1
 * arg3: ��С���Feeder   stud.runner.WalkerFeeder
 */
public final class SearchTester {
    public static ArrayList<Key> keys = new ArrayList<>();
    //ͬѧ�ǿ��Ը����Լ�����Ҫ�������޸ġ�
    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, FileNotFoundException {

        //����args[3]�ṩ����������ѧ����EngineFeeder����
        EngineFeeder feeder = (EngineFeeder)
                Class.forName(args[3])
                        .getDeclaredConstructor().newInstance();

    ////���ļ��������������������ı��� args[0]�����������ļ������·��
        Scanner scanner = new Scanner(new File(args[0]));
        ArrayList<String> problemLines = getProblemLines(scanner);

        //feeder�����������ı���ȡѰ·���������ʵ��
        ArrayList<Problem> problems = feeder.getProblems(problemLines);
    ////����ʵ�����뵽ArrayList��

        //��ǰ��������� args[1]    Ѱ·���⣬�������̣�Ұ�˴���ʿ���ӵ�
        ProblemType type = ProblemType.valueOf(args[1]);
        //����ڼ��׶� args[2]
        int step = Integer.parseInt(args[2]);

        //�����������ͺ͵�ǰ�׶Σ���ȡ������������������
        //Ѱ·����ֱ�ʹ��Grid�����Euclid������Ϊ��������
        ArrayList<HeuristicType> heuristics = getHeuristicTypes(type, step);

        for (HeuristicType heuristicType : heuristics) {
            //solveProblems�������ݲ�ͬ�����������ɲ�ͬ��searcher
            //��Feeder��ȡ��ʹ�õ��������棨AStar��IDAStar�ȣ���
            //solveProblems(problems, feeder.getAStar(heuristicType), heuristicType);
            //System.out.println();
        }
        //int[][] initial = {{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};
        //int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        int numTests = 1; // ������еĲ��Դ���
        int size = 4;//�������size ע�⵱ʹ��ģʽ���ݿ�ʱsize����Ϊ4
        boolean pattern = false;//�Ƿ�ʹ��Patternģʽ���ݿ�
        boolean Dijkstra = false;//�Ƿ�ʹ��Dijkstra Pattern ��ע��Ҫ��patternͬʱ��
        boolean robe = false;//�Ƿ�ʹ���������
        boolean useAstar = true;//�Ƿ�ʹ��A*�㷨
        boolean useIDAstar = true;//�Ƿ�ʹ��IDA*�㷨

        boolean useManhattan = true;//�Ƿ�ʹ�������پ�����������
        boolean useHamming = false;//�Ƿ�ʹ�ú���������������
        boolean useEmptySpace = false;//�Ƿ�ʹ�ÿ�λ������������


        System.out.println("����ģʽ���ݿ�ʱ�䣺");
        // ��ʼʱ��
        long stime = System.currentTimeMillis();
        PatternDatabase patternDatabase = new PatternDatabase(keys);//����15����ģʽ���ݿ�
        // ����ʱ��
        long etime = System.currentTimeMillis();

        // ����ִ��ʱ��
        System.out.printf("%d ����.", (etime - stime));
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
                // ���������ʹ�� initial �� goal ���к�������
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
            PuzzleBoard initialState = new PuzzleBoard(array1, size);//��ų�ʼ״̬���鵽��ΪinitialState��PuzzleBoard��
            PuzzleBoard goal1 = new PuzzleBoard(array2, size);//Ŀ��״̬
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
                        // ʹ��Dijkstra������Ϊ��������
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
                        // ʹ�������پ�����Ϊ��������
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
                        // ʹ�ú���������Ϊ��������
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
                        // ʹ�ÿ�λ������Ϊ��������
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
                            solverThread.join(60000); // �ȴ�10����
                            solverThread.interrupt(); // �ж�����߳�
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
                            solverThread.join(600000); // �ȴ�10����
                            solverThread.interrupt(); // �ж�����߳�
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
                            solverThread.join(60000); // �ȴ�10����
                            solverThread.interrupt(); // �ж�����߳�
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
                            solverThread.join(60000); // �ȴ�10����
                            solverThread.interrupt(); // �ж�����߳�
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
     * ����������
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
     * �����������ͺ͵�ǰ�׶Σ���ȡ������������������
     * @param type
     * @param step
     * @return
     */
    private static ArrayList<HeuristicType> getHeuristicTypes(ProblemType type, int step) {
        //��⵱ǰ�����ڵ�ǰ�׶ο��õ��������������б�
        ArrayList<HeuristicType> heuristics = new ArrayList<>();
        //���ݲ�ͬ���������ͣ����в�ͬ�Ĳ���
        if (type == ProblemType.PATHFINDING) {
            heuristics.add(PF_GRID);
            heuristics.add(PF_EUCLID);
        }
        else {
            //NPuzzle����ĵ�һ�׶Σ�ʹ�ò���λ���ƺ������پ���
            if (step == 1) {
                heuristics.add(MISPLACED);
                heuristics.add(MANHATTAN);
            }
            //NPuzzle����ĵ����׶Σ�ʹ��Disjoint Pattern
            else if (step == 3){
                heuristics.add(DISJOINT_PATTERN);
            }
        }
        return heuristics;
    }

    /**
     * ʹ�ø�����searcher��������⼯���е��������⣬ͬʱʹ�ý���������õĽ���м��
     * @param problems     ���⼯��
     * @param searcher     searcher
     * @param heuristicType ʹ����������������
     */
    private static void solveProblems(ArrayList<Problem> problems, AbstractSearcher searcher, HeuristicType heuristicType) {
        for (Problem problem : problems) {
            // ʹ��AStar�����������
            StopwatchCPU timer1 = new StopwatchCPU();
            Deque<Node> path = searcher.search(problem);
            double time1 = timer1.elapsedTime();

            if (path == null) {
                System.out.println("No Solution" + "��ִ����" + time1 + "s��"+
                        "��������" + searcher.nodesGenerated() + "����㣬" +
                        "��չ��" + searcher.nodesExpanded() + "�����");
                continue;
            }

            // ��·���Ŀ��ӻ�
            problem.showSolution(path);

            System.out.println("����������" + heuristicType + "����·�����ȣ�" + path.size() + "��ִ����" + time1 + "s��" +
                    "��������" + searcher.nodesGenerated() + "����㣬" +
                    "��չ��" + searcher.nodesExpanded() + "�����");
        }
    }

    /**
     * ���ļ���������ʵ�����ַ����������ַ���������
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