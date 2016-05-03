package util.AST;

import java.util.ArrayList;

public class If extends Command {
	Expression expression;
	ArrayList<Command> then;
	ArrayList<Command> otherwise;
	
	public If(Expression expression, ArrayList<Command> then,
			ArrayList<Command> otherwise) {
		super();
		this.expression = expression;
		this.then = then;
		this.otherwise = otherwise;
	}
	
	
}
