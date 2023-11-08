package stud.g01.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

public class NpuzzleAction extends Action {
    private int targetPosition;

    public NpuzzleAction(int targetPosition) {
        this.targetPosition = targetPosition;
    }

    @Override
    public void draw() {
        System.out.println(this);
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    @Override
    public void execute() {
        // ʵ��ִ�ж������߼�
    }

    @Override
    public int stepCost() {
        // ����ִ�ж����Ĵ���
        // ���磺������ж����Ĵ��۶���ͬ�����Է���һ���̶�ֵ
        return 1;
    }
}
