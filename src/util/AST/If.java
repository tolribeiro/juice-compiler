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
	
	@Override
	public String toString(int level) {
		StringBuffer ifCommand = new StringBuffer();
		
		ifCommand.append("if");
		ifCommand.append("(");
		ifCommand.append(expression.toString(0));
		ifCommand.append(")");
		ifCommand.append("{\n");
		ifCommand.append("\t");
		
		for	(int i = 0; i < then.size(); ++i) {
			ifCommand.append("\t");
			ifCommand.append(then.get(i).toString(level+1));
		}
		
		ifCommand.append("\n");
		ifCommand.append("\t}");
		
		return ifCommand.toString();
	}
	
}
