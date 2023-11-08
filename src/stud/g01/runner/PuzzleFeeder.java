package stud.g01.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.g01.patterndatabase.PatternDatabase;
import stud.g01.problem.npuzzle.NPuzzleProblem;
import stud.g01.problem.npuzzle.PuzzleBoard;
import stud.g01.queue.PqFrontier;
import stud.queue.ListFrontier;

import java.util.ArrayList;
//Fix Me   //Fix Me
public class PuzzleFeeder extends EngineFeeder {
    /**
     * ��ȡ������
     * @param problemLines  �ַ������飬��ŵ��ǣ����������������ı��ļ�������
     * @return
     */
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {
        /* ����������� */
        ArrayList<Problem> problems = new ArrayList<>();
        for (String line : problemLines) {
            NPuzzleProblem problem = getNPuzzleProblem(line);//��ȡ����
            problems.add(problem);
        }//����ѭ��ͨ����̬����problemLines�����ļ�����������
        //System.out.println("��������" + problems.size());
        return problems;
    }

    /**
     * ��ȡ����String����Ϣ��ŵ�problem����
     * @param problemLine
     * @return
     */
    private NPuzzleProblem getNPuzzleProblem(String problemLine) {

        String[] elements = problemLine.split(" ");
        int size = Integer.parseInt(elements[0]);//���ַ�����һ�����֣����������size
        int[] array1 = new int[size * size];//��ʼ״̬
        int[] array2 = new int[size * size];//Ŀ��״̬
        for (int i = 0; i < 2 * size * size; i++) {
            if(i < size * size) array1[i] = Integer.parseInt(elements[i + 1]);
            else array2[i - size * size] = Integer.parseInt(elements[i + 1]);
        }
        //System.out.println(size);
        PuzzleBoard initialState = new PuzzleBoard(array1,size);//��ų�ʼ״̬���鵽��ΪinitialState��PuzzleBoard��
        PuzzleBoard goal = new PuzzleBoard(array2,size);//Ŀ��״̬

        //NPuzzleProblem n1 = new NPuzzleProblem(initialState, goal, size);
        /*
        n1.solvable();
        n1.hammingDistance();
        n1.manhattanDistance();
        n1.zeroDistance();
        for (int i = 0; i < size * size; i++) {
            System.out.print(initialState.puzzle[i]+"/ ");
        }
        System.out.println(" ");
        for (int i = 0; i < size * size; i++) {
            System.out.print(goal.puzzle[i]+"* ");
        }
        System.out.println("\n");
        */
        //����Ѱ·�����ʵ��
        return new NPuzzleProblem(initialState, goal, size);//ʹ�õõ�����newһ��NPuzzleProblem
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
        //return new PqFrontier(Node.evaluator(type)); // ʹ�����Լ�ʵ�ֵ� PqFrontier
        return new PqFrontier(Node.evaluator(type));
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
        return null;
    }
}
