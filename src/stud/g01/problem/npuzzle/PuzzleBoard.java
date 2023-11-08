package stud.g01.problem.npuzzle;
import core.problem.Action;
import core.problem.State;

public class PuzzleBoard extends State {


    public int size;
    public int[] puzzle;

    public PuzzleBoard(int[] a, int size){
        puzzle = new int[size*size];
        for(int i=0;i<size*size;i++){
            this.puzzle[i]=a[i];
        }
    }

    @Override
    public void draw() {

    }

    @Override
    public State next(Action action) {
        return null;
    }

    @Override
    public Iterable<? extends Action> actions() {
        return null;
    }
}
