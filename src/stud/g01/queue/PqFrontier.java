package stud.g01.queue;

import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.PriorityQueue;
import java.util.Comparator;
import core.solver.queue.EvaluationType;

public class PqFrontier implements Frontier {
    private final PriorityQueue<Node> priorityQueue;

    public PqFrontier(Comparator<Node> evaluator) {
        // �ڹ��캯���г�ʼ�� PriorityQueue�����ṩһ���Ƚ�����ȷ���ڵ�����ȼ�
        priorityQueue = new PriorityQueue<>(Node.evaluator(EvaluationType.FULL));
    }

    @Override
    public boolean contains(Node node) {
        return priorityQueue.contains(node);
    }

    @Override
    public boolean offer(Node node) {
        // ���ڵ���ӵ����ȼ�������
        return priorityQueue.offer(node);
    }

    @Override
    public Node poll() {
        // �Ӷ�����ȡ�������ؾ���������ȼ��Ľڵ�
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
