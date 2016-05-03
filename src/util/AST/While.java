package util.AST;

import java.util.ArrayList;

public class While extends Command{
	Expression expression;
	ArrayList<Command> then;
	
	public While(Expression expression, ArrayList<Command> then) {
		super();
		this.expression = expression;
		this.then = then;
	}
}
