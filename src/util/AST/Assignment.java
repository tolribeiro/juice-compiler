package util.AST;

public class Assignment extends Command{
	Expression expression;
	String id;
	
	public Assignment(Expression expression, String id) {
		super();
		this.expression = expression;
		this.id = id;
	}
	
	@Override
	public String toString(int level) {
		return id + " = " + expression.toString(0)+";";
	}
}
