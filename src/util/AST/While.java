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
	
	@Override
	public String toString(int level) {
		StringBuffer whileD = new StringBuffer();
		
		
		whileD.append("while");
		whileD.append("(");
		whileD.append(expression.toString(0));
		whileD.append(")");
		whileD.append("{\n");
		whileD.append("\t");
		if (then != null) {
			for (int i = 0; i < then.size(); i++) {
				whileD.append("\t");
				whileD.append(then.get(i).toString(level+1));
				whileD.append("\n");
			}
		}
		
		whileD.append("\n\t}");
		
		return whileD.toString();
	
	}
}
