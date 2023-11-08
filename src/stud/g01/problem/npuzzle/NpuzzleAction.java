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
        // 实现执行动作的逻辑
    }

    @Override
    public int stepCost() {
        // 返回执行动作的代价
        // 例如：如果所有动作的代价都相同，可以返回一个固定值
        return 1;
    }
}
