package core.problem;

public abstract class Action {
	public abstract void draw();
	public abstract int stepCost();

	public abstract void execute();
}
