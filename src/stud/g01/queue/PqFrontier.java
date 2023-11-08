package stud.g01.queue;

import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.PriorityQueue;
import java.util.Comparator;
import core.solver.queue.EvaluationType;

public class PqFrontier implements Frontier {
    private final PriorityQueue<Node> priorityQueue;

    public PqFrontier(Comparator<Node> evaluator) {
        // 在构造函数中初始化 PriorityQueue，并提供一个比较器来确定节点的优先级
        priorityQueue = new PriorityQueue<>(Node.evaluator(EvaluationType.FULL));
    }

    @Override
    public boolean contains(Node node) {
        return priorityQueue.contains(node);
    }

    @Override
    public boolean offer(Node node) {
        // 将节点添加到优先级队列中
        return priorityQueue.offer(node);
    }

    @Override
    public Node poll() {
        // 从队列中取出并返回具有最高优先级的节点
        return priorityQueue.poll();
    }

    @Override
    public void clear() {
        priorityQueue.clear();
    }

    @Override
    public int size() {
        return priorityQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }
}
